package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.Consts;
import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
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
			String rest = request.getParameter("wrest");
			
			String sql = "update mstr_user set begintime=? , endtime=?, rest=? where userid=?";
			Object[] params = new Object[4];
			params[0] = begintime;
			params[1] = endtime;
			params[2] = rest;
			params[3] = userinfo.getUserId();
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			userinfo.setBeginTime(DateTimeUtil.stringToTime(begintime));
			userinfo.setEndTime(DateTimeUtil.stringToTime(endtime));
			userinfo.setRest(rest);
			session.setAttribute("userinfo", userinfo);
		}
		
		request.setAttribute("userid", userinfo.getUserId());
		request.setAttribute("username", userinfo.getUserName());
		String maincompanyid = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		if(maincompanyid == Consts.DefaultCompanyId){
			String sql = "select * from mstr_user_comp usr left join mstr_company com on usr.companyid= com.companyid where usr.userid=? and usr.defaultflg ='1' and usr.delflg='0'";
			Object[] params = new Object[1];
			params[0] = userinfo.getUserId();
			List<Object> companyinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			for (Object data : companyinfo) {
				Map<String, Object> row = (Map<String, Object>) data;
				maincompanyid = row.get("companyid").toString();
				userinfo.setMaincompanyid(maincompanyid);
				userinfo.setMaincompanyname(row.get("companynm").toString());
				session.setAttribute("userinfo", userinfo);
				break;
			}
		}
		String companyname = maincompanyid == Consts.DefaultCompanyId ? "未加入公司，点击加入或创建" : userinfo.getMaincompanyname();
		String companyurl = maincompanyid == Consts.DefaultCompanyId ? "companysetting.do" : "companydetail.do?companyid=" + maincompanyid;
		request.setAttribute("companyname", companyname);
		request.setAttribute("companyurl", companyurl);
		
		request.setAttribute("begintime", userinfo.getBeginTime().toString());
		request.setAttribute("endtime", userinfo.getEndTime().toString());
		request.setAttribute("wrest", userinfo.getRest().toString());
		
		String personalImg = "assets/images/christian.jpg";
		String nickname = "";
		if("F".equals(userinfo.getSex())){
			personalImg = "assets/images/rachel.png";
			nickname = "";
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
		String sql = "select count(*) leaveday from cdata_leave where companyid=? and userid=? and year=? and month=?";
		Object[] params = new Object[4];
		params[0] = maincompanyid;
		params[1] = userinfo.getUserId();
		params[2] = year;
		params[3] = month;
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
                           "and wk.companyid =? "+
                           "and user.userid =? " +
                     "group by user.userid";
		Object[] params2 = new Object[4];
		params2[0] = year;
		params2[1] = month;
		params2[2] = maincompanyid;
		params2[3] = userinfo.getUserId();
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
		
		String superdiplay = "0".equals(userinfo.getAuthflg()) ? "" : "display: none";
		request.setAttribute("superdiplay", superdiplay);
		
		String userdiplay = "0".equals(userinfo.getAuthflg()) || "1".equals(userinfo.getAuthflg()) ? "display: none" : "";
		request.setAttribute("userdiplay", userdiplay);
		
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
