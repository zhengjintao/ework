package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class UserWorkDetailServlet
 */
public class UserWorkDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserWorkDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		if (username != null) {
			username = new String(username.getBytes("iso-8859-1"), "utf-8");
		}
		//查询月份取得
		String wdate = request.getParameter("wdate");
	
		
		// 系统当前时间取得
		SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
		String dateStr= wdate == null ? formattime.format(new Date()): wdate;
		// 日期设定
		request.setAttribute("sysDate", DateTimeUtil.GetMonth(dateStr));
		request.setAttribute("userid", userid);
		request.setAttribute("username", username);
		
		request.setAttribute("monthdata", getMonthData(userid, DateTimeUtil.GetMonth(dateStr)));
		request.setAttribute("workdata", getworktime(userid, DateTimeUtil.GetMonth(dateStr)));
		request.getRequestDispatcher("userworkdetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<String[]> getworktime(String userid,String dateStr){
		List<String[]> monthData = new ArrayList<String[]>();
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql = "select * from cdata_worktime where userid= ? and year = ? and month= ?";
		Object[] params = new Object[3];
		params[0] = userid;
		params[1] = year;
		params[2] = month;
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);
		for (Object data : resultList) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[3];
			each[0] = row.get("date").toString();
			each[1] = row.get("begintime").toString();
			each[2] = row.get("endtime").toString();
			monthData.add(each);
		}

		return monthData;
	}
	
	private List<String[]> getMonthData(String userId, String dateStr) {
		List<String[]> monthData = new ArrayList<String[]>();
		if (dateStr == null) {
			return monthData;
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

		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("leavedate").toString();
			each[1] = (String) row.get("content");
			monthData.add(each);
		}

		return monthData;
	}
}
