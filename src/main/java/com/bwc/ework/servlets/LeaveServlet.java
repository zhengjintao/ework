package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class leaveServlte
 */
public class LeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<String[]> getMonthData(String userId, String companyid, String dateStr) {
		List<String[]> monthData = new ArrayList<String[]>();
		if (dateStr == null) {
			return monthData;
		}
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();

		String sql = "select * from cdata_leave where userid=? and companyid = ? and year = ? and month = ?";
		Object[] params = new Object[4];
		params[0] = userId;
		params[1] = companyid;
		params[2] = year;
		params[3] = month;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);

		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("leavedate").toString();
			each[1] = (String) row.get("content");
			monthData.add(each);
		}

		return monthData;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User userinfo = (User) session.getAttribute("userinfo");

		String sql = "delete from cdata_leave where userid=? and companyid = ? and leavedate=?";
		Object[] params = new Object[3];
		params[0] = userinfo.getUserId();
		params[1] =  Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		params[2] = request.getParameter("wdate");
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
		senddeleteMail(userinfo.getUserId(),  Utils.getStoreCompanyid(userinfo.getMaincompanyid()), userinfo.getUserName(), request.getParameter("wdate"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 请假的开始日期
		String wdate = request.getParameter("wdate");
		// 用户信息
		User userinfo = (User) session.getAttribute("userinfo");
		// 请假时间（复数可）
		List<String> wdateList = new ArrayList();
		if (wdate != null) {
			wdateList = Arrays.asList(wdate.split(","));
		}

		// 画面查询时间
		String wdate2 = request.getParameter("wdate2");
		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());
		if (wdateList.size() == 0) {
			wdateList.add(now);
		}

		if (wdate2 == null || wdate2.length() == 0) {
			wdate = now;
			wdate2 = DateTimeUtil.GetMonth(now);
		}

		// 请假理由转码
		String wcomment = request.getParameter("wcomment");
		if (wcomment == null) {
			wcomment = "";
		}
		String subkbn = request.getParameter("subKbn");

		// IN条件作成
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < wdateList.size(); i++) {
			buffer.append("?, ");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.deleteCharAt(buffer.length() - 1);

		Object[] params = new Object[wdateList.size() + 2];
		params[0] = userinfo.getUserId();
		params[1] =  Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		for (int i = 0; i < wdateList.size(); i++) {
			params[i + 2] = wdateList.get(i);
		}

		// 初期化
		if (subkbn == null) {
			wdate2 = DateTimeUtil.GetMonth(now);
		} else {

			// 删除
			if ("1".equals(request.getParameter("deleteFlg"))) {
				delete(request, response);
			}

			// 初期化的场合
			if ("true".equals(request.getParameter("subKbn")) && !"1".equals(request.getParameter("leaveinfo"))
					&& !"1".equals(request.getParameter("deleteFlg"))) {
				String sql = "select * from cdata_worktime where userid=? and companyid =? and date in (" + buffer.toString() + ")";

				List<Object> listw = JdbcUtil.getInstance().excuteQuery(sql, params);
				if (listw.size() > 0) {
					StringBuilder dayinfo = new StringBuilder();
					for (Object data : listw) {
						Map<String, Object> row = (Map<String, Object>) data;
						dayinfo.append(String.valueOf(row.get("date")) + "<br>");
					}
					request.setAttribute("errmsg", "下面日期已签到，无法请假。<br>" + dayinfo);
				} else {
					// 删除
					String deleteSql = "delete from cdata_leave where userid=? and companyid=? and leavedate in (" + buffer.toString()
							+ ")";
					Object[] delparams = new Object[3];
					delparams[0] = userinfo.getUserId();
					delparams[1] =  Utils.getStoreCompanyid(userinfo.getMaincompanyid());
					delparams[2] = wdate;
					JdbcUtil.getInstance().executeUpdate(deleteSql, params);

					// 数据存在更新操作
					String insertSql = "insert into cdata_leave value(?,?,?,?,?,?)";
					Object[] insertparams = new Object[6];
					String userid = userinfo.getUserId();
					for (int i = 0; i < wdateList.size(); i++) {
						com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(wdateList.get(i));
						insertparams[0] = userid;
						insertparams[1] =  Utils.getStoreCompanyid(userinfo.getMaincompanyid());
						insertparams[2] = wdateList.get(i);
						insertparams[3] = date1.getYear();
						insertparams[4] = date1.getMonth();
						insertparams[5] = wcomment == null || wcomment.length() == 0 ? "未填写理由" : wcomment;
						JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
					}

					//sendMail(userid,  Utils.getStoreCompanyid(userinfo.getMaincompanyid()), userinfo.getUserName(), wdate, wcomment);
				}

				wdate2 = DateTimeUtil.GetMonth(wdateList.get(0));
			}
		}

		// 日期设定
		request.setAttribute("sysDate", wdateList.get(0));
		// 日期设定
		request.setAttribute("sysDate2", wdate2);
		request.setAttribute("wcomment", wcomment);

		List<String[]> monthinfo = getMonthData(userinfo.getUserId(),  Utils.getStoreCompanyid(userinfo.getMaincompanyid()), wdate2);
		StringBuilder info = new StringBuilder();
		if (monthinfo.size() == 0) {
			info.append("<tr>");
			info.append("<td>");
			info.append("当月没有请假");
			info.append("</td>");
			info.append("</tr>");
		}
		for (String[] each : monthinfo) {
			info.append("<tr>");
			info.append("<td style='width:40%'>");
			info.append(each[0]);
			info.append("</td>");
			info.append("<td style='width:60%'> ");
			info.append(each[1]);
			info.append("</td>");
			info.append("<td style='width:6%'>");
			info.append("<i class='close icon' onclick='ondelete(" + each[0].replace("-", "") + ");'></i>");
			info.append("</td>");
			info.append("</tr>");
		}
		if ("1".equals(request.getParameter("leaveinfo")) || "1".equals(request.getParameter("deleteFlg"))) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", info.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());

			return;
		}

		request.setAttribute("info", info.toString());
		RequestDispatcher re = request.getRequestDispatcher("leave.jsp");
		re.forward(request, response);
	}

	// 向管理员发送请假邮件
	private void senddeleteMail(String userid, String companyid, String username, String wdate)
			throws UnsupportedEncodingException {

		List<String> list = getadmminusermailadd();

		if (list.size() > 0) {
			String mailname = "取消请假通知_" + username;
			String text = "尊敬的管理员您好：" + "<br>" + "&emsp;&emsp;本人(" + username + ")取消"
					+ wdate + "的请假。" + "<br>" + "请留意";
			text = text + leaveinfoText(userid, companyid, username);
			final List<String> ulist = list;
			final String umailname = mailname;
			final String utext = text;
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							SendMailFactory.getInstance().getMailSender().sendMessage(ulist, umailname, utext, null);
						} catch (MessagingException e) {
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();

		}
	}

	// 向管理员发送请假邮件
	private void sendMail(String userid, String companyid, String username, String wdate, String wcomment)
			throws UnsupportedEncodingException {
		// 管理员邮箱取得
		List<String> list = getadmminusermailadd();

		if (list.size() > 0) {
			String mailname = "请假通知_" + username;
			String text = "尊敬的管理员您好：" + "<br>" + "&emsp;&emsp;本人(" + username + ")由于" + wcomment + "<br>" + "因此,计划于"
					+ wdate + "请假。" + "<br>" + "望批准";
			text = text + leaveinfoText(userid, companyid, username);
			final List<String> ulist = list;
			final String umailname = mailname;
			final String utext = text;
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							SendMailFactory.getInstance().getMailSender().sendMessage(ulist, umailname, utext, null);
						} catch (MessagingException e) {
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();

		}
	}

	private List<String> getadmminusermailadd() {
		// 管理员邮箱取得
		String sql = "select mail from mstr_user where authflg=? and delflg='0' and mail is not null";
		Object[] params = new Object[1];
		params[0] = "1";
		List<Object> userlist = JdbcUtil.getInstance().excuteQuery(sql, params);
		List<String> list = new ArrayList<String>();
		if (userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) userlist.get(i);
				if (set.get("mail") != null) {
					list.add(set.get("mail").toString());
				}
			}
		}

		return list;
	}

	private String leaveinfoText(String userid, String companyid, String username) {
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());
		List<String[]> info = leaveinfoAfterToday(userid, companyid, now);
		StringBuilder sb = new StringBuilder();
		if (info.size() > 0) {
			sb.append("<br>");
			sb.append("-----------------------备考------------------------<br>");
			sb.append("[" + username + "]最终请假安排一览<br>");
			for (String[] each : info) {
				sb.append(each[0]).append("&emsp;&emsp;").append(each[1]).append("<br>");
			}
		}else{
			sb.append("<br>");
			sb.append("-----------------------备考------------------------<br>");
			sb.append("[" + username + "]最终没有请假<br>");
		}
		return sb.toString();
	}

	private List<String[]> leaveinfoAfterToday(String userid, String companyid, String date) {
		List<String[]> info = new ArrayList<String[]>();
		String sql2 = "SELECT * FROM cdata_leave where userid=? and companyid=? and leavedate > ? order by leavedate;";
		Object[] params2 = new Object[3];
		params2[0] = userid;
		params2[1] = companyid;
		params2[2] = date;
		List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);

		for (int i = 0; i < infolist.size(); i++) {
			Map<String, Object> set = (Map<String, Object>) infolist.get(i);
			String[] each = new String[2];
			each[0] = String.valueOf(set.get("leavedate"));
			each[1] = String.valueOf(set.get("content"));
			info.add(each);
		}

		return info;
	}
}
