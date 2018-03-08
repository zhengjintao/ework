package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class SendMailServlet
 */
public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String prockbn = request.getParameter("prockbn");
		String userid = request.getParameter("userid");

		if (userid == null || userid.length() == 0) {
			HttpSession session = request.getSession();
			User userinfo = (User) session.getAttribute("userinfo");
			userid = userinfo.getUserId();
		}

		// 查询月份取得
		String wdate = request.getParameter("wdate");
		String mail = request.getParameter("mail");
		String mailname = request.getParameter("mailname");
		if (mailname != null) {
			mailname = new String(mailname.getBytes("iso-8859-1"), "utf-8");
		} else {
			mailname = "当月出勤统计一览";
		}

		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = wdate == null ? formattime.format(new Date()) : wdate;

		String leaveinfo = getMonthData(userid, DateTimeUtil.GetMonth(dateStr));
		String workinfo = getworktime(userid, DateTimeUtil.GetMonth(dateStr));
		
		List<String> list = new ArrayList<String>();
		list.add(mail);
		// list.add("92@sina.cn");
		list.add("xiaonei0912@qq.com");
		String text = workinfo + leaveinfo;
		String message = "";
		if("1".equals(prockbn)){
			message = text;
		}else{
			try {
				message = "发送成功";
				SendMailFactory.getInstance().getMailSender().sendMessage(list, mailname, text);
			} catch (MessagingException e) {
				message = "发送失败";
			}
		}
		
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", message);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());

		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getworktime(String userid, String dateStr) {

		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql = "select * from cdata_worktime where userid= ? and year = ? and month= ?";
		Object[] params = new Object[3];
		params[0] = userid;
		params[1] = year;
		params[2] = month;
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);

		StringBuilder sb = new StringBuilder();
		sb.append("出勤时间：");
		sb.append("<br>");
		for (Object data : resultList) {

			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[4];
			each[0] = row.get("date").toString();
			each[1] = row.get("begintime").toString();
			each[2] = row.get("endtime").toString();
			each[3] = row.get("comment") == null ? "" : row.get("comment").toString().replace("\r\n", " ");
			sb.append(each[0]);
			sb.append("\t");
			sb.append(each[1]);
			sb.append("\t");
			sb.append(each[2]);
			sb.append("\t");
			sb.append(each[3]);

			sb.append("<br>");
		}

		return sb.toString();
	}

	private String getMonthData(String userId, String dateStr) {
		if (dateStr == null) {
			return "";
		}
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();

		String sql = "select * from cdata_leave where userid=? and year = ? and month = ?";
		Object[] params = new Object[3];
		params[0] = userId;
		params[1] = year;
		params[2] = month;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
		StringBuilder sb = new StringBuilder();
		sb.append("请假时间：");
		sb.append("<br>");
		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("leavedate").toString();
			each[1] = (String) row.get("content");

			sb.append(each[0]);
			sb.append("\t");
			sb.append(each[1]);

			sb.append("<br>");
		}

		return sb.toString();
	}
}
