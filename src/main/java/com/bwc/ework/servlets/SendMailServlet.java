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

import org.json.JSONObject;

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
		String username = request.getParameter("username");

		if (userid == null || userid.length() == 0) {
			HttpSession session = request.getSession();
			User userinfo = (User) session.getAttribute("userinfo");
			userid = userinfo.getUserId();
			username = userinfo.getUserName();
		}
		
		if (username != null) {
			username = new String(username.getBytes("iso-8859-1"), "utf-8");
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
		
		String yearandmonth = DateTimeUtil.GetMonth(dateStr);
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();
		
		String text = "姓名：" + username + "&emsp;月份："+ year + "年" + month+"月" + "<br>" + "<br>";
		String leaveinfo = getMonthData(userid, yearandmonth);
		String workinfo = getworktime(userid, yearandmonth);
		
		List<String> list = new ArrayList<String>();
		list.add(mail);
		// list.add("92@sina.cn");
		list.add("xiaonei0912@qq.com");  // 管理邮箱抄送
		text = text + workinfo + "<br>" +leaveinfo;
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
		sb.append("[出勤记录]");
		sb.append("<br>");
		
		StringBuilder sb2 = new StringBuilder();
		for (Object data : resultList) {

			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[4];
			each[0] = row.get("date").toString();
			each[1] = row.get("begintime").toString();
			each[2] = row.get("endtime").toString();
			each[3] = row.get("comment") == null ? "" : row.get("comment").toString().replace("\r\n", " ");
			sb2.append(each[0]);
			sb2.append("&emsp;");
			sb2.append(each[1]);
			sb2.append("&emsp;");
			sb2.append(each[2]);
			sb2.append("&emsp;");
			sb2.append(each[3]);

			sb2.append("<br>");
		}
		
		if(sb2.length() == 0){
			sb.append("当月没有出勤");
		}else{
			sb.append(sb2.toString());
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
		sb.append("[请假记录]");
		sb.append("<br>");
		
		StringBuilder sb2 = new StringBuilder();
		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("leavedate").toString();
			each[1] = (String) row.get("content");

			sb2.append(each[0]);
			sb2.append("&emsp;");
			sb2.append(each[1]);

			sb2.append("<br>");
		}
		
		if(sb2.length() == 0){
			sb.append("当月没有请假");
		}else{
			sb.append(sb2.toString());
		}

		return sb.toString();
	}
}
