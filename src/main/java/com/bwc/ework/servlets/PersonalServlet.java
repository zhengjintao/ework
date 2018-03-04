package com.bwc.ework.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Servlet implementation class personalServlet
 */
public class PersonalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonalServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		
		if("setting".equals(request.getParameter("subKbn"))){
			String begintime = request.getParameter("wbegin");
			String endtime = request.getParameter("wend");
			
			String sql = "update mstr_user set begintime=? , endtime=? where userid=?";
			Object[] params = new Object[3];
			params[0] = begintime;
			params[1] = endtime;
			params[2] = userinfo.getUserId();
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			userinfo.setBeginTime(DateTimeUtil.stringToTime(begintime));
			userinfo.setEndTime(DateTimeUtil.stringToTime(endtime));
			session.setAttribute("userinfo", userinfo);
		}
		
		request.setAttribute("userid", userinfo.getUserId());
		request.setAttribute("username", userinfo.getUserName());
		request.setAttribute("begintime", userinfo.getBeginTime().toString());
		request.setAttribute("endtime", userinfo.getEndTime().toString());
		
		String personalImg = "assets/images/christian.jpg";
		String nickname = "帅锅";
		if("F".equals(userinfo.getSex())){
			personalImg = "assets/images/rachel.png";
			nickname = "镁铝";
		}
		request.setAttribute("nickname", nickname);
		request.setAttribute("sex", personalImg);
		
		String leavedays = "0";
		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new java.util.Date());
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(DateTimeUtil.GetMonth(now));
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql = "select count(*) leaveday from cdata_leave where userid=? and year=? and month=?";
		Object[] params = new Object[3];
		params[0] = userinfo.getUserId();
		params[1] = year;
		params[2] = month;
		List<Object> leaveinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(leaveinfo == null || leaveinfo.size() > 0){
			for(Object infoobj : leaveinfo){
				Map<String, Object> info = (Map<String, Object>)infoobj;
				
				leavedays= info.get("leaveday").toString();
			}
		}
		request.setAttribute("leavedays", String.valueOf(leavedays));
		
		String sql2 = "select user.username, " +
		                     "user.userid, " +
				             "sum(wk.worktime) as worktime," +
				             "count(wk.worktime) as days " +
				     "from cdata_worktime  wk join mstr_user user " +
                           "on wk.userid = user.userid " +
                     "where wk.year= ? " + 
                           "and wk.month = ? " + 
                           "and user.delflg ='0' "+
                           "and user.userid =? " +
                     "group by user.userid";
		Object[] params2 = new Object[3];
		params2[0] = year;
		params2[1] = month;
		params2[2] = userinfo.getUserId();
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql2, params2);

		String hours = "0";
		String dayscount = "0";
		
		if(resultList.size() > 0){
			Map<String, Object> row = (Map<String, Object>) resultList.get(0);
			
			// 出勤时间
			hours = row.get("worktime").toString();
			dayscount = row.get("days").toString();
			
		}
		
		request.setAttribute("days", dayscount);
		request.setAttribute("hours", hours);
		
		String diplay = "0".equals(userinfo.getAuthflg()) || "1".equals(userinfo.getAuthflg()) ? "" : "display: none";
		request.setAttribute("display", diplay);
		
		request.setAttribute("userid", userinfo.getUserId());
		request.getRequestDispatcher("personal.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
