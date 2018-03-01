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
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.form.User;

/**
 * 全体员工出勤情况一览查询（按月份）Servlet
 */
public class StatisticsWorkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatisticsWorkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String[]> dataList = new ArrayList<String[]>();
		//查询月份取得
		String wdate = request.getParameter("wdate");
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		
		// 出勤化的时候，当前时间取得
		String year = wdate == null ? Integer.toString(new Date().getYear() + 1900) : DateTimeUtil.stringToDate(wdate).getYear();
		String month = wdate == null ? String.format("%02d", new Date().getMonth()+1) : DateTimeUtil.stringToDate(wdate).getMonth();
		
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String selectuser = "0".equals(userinfo.getAuthflg()) || "1".equals(userinfo.getAuthflg()) ? "" : "and user.userid ='" +userinfo.getUserId() +"' " ;
		String sql = "select user.username, " +
		                     "user.userid, " +
				             "sum(wk.worktime) as worktime " +
				     "from cdata_worktime  wk join mstr_user user " +
                           "on wk.userid = user.userid " +
                     "where wk.year= ? " + 
                           "and wk.month = ? " + 
                           "and user.delflg ='0' "+
                           selectuser +
                     "group by user.username,user.userid";
		Object[] params = new Object[2];
		params[0] = year;
		params[1] = month;
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);

		// 系统当前时间取得
		SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
		String dateStr= wdate == null ? formattime.format(new Date()): wdate;
		// 结果取得整理（画面显示用）
		for (Object result : resultList) {
			Map<String, Object> row = (Map<String, Object>) result;
			String[] data = new String[5];
			// 用户名
			data[0] = row.get("username").toString();
			// 出勤时间
			data[1] = row.get("worktime").toString();
			// 请假时间
			data[2] = getLeaveTime(row.get("userid").toString(),year,month);
			
			data[3] = row.get("userid").toString();
			data[4] = dateStr;
			dataList.add(data);
		}
		
		
		
		// 日期设定
		request.setAttribute("sysDate", DateTimeUtil.GetMonth(dateStr));
		
		request.setAttribute("dataList", dataList);
		request.getRequestDispatcher("statisticsWork.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private String getLeaveTime(String userid,String year,String month){
		DecimalFormat df2=new DecimalFormat("0.0");
		
		String sql = "select * from cdata_leave where userid= ? and year = ? and month= ?";
		Object[] params = new Object[3];
		params[0] = userid;
		params[1] = year;
		params[2] = month;
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);
		return df2.format(resultList.size() * 8);
	}

}
