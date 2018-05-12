package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
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
		
		// 出勤化的时候，当前时间取得
		String year = wdate == null ? Integer.toString(new Date().getYear() + 1900) : DateTimeUtil.stringToDate(wdate).getYear();
		String month = wdate == null ? String.format("%02d", new Date().getMonth()+1) : DateTimeUtil.stringToDate(wdate).getMonth();
		
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String sql2 = "select * from cdata_companyuser where companyid = ? and userid=? and delflg ='0' and rolekbn in ('0','1')";

		Object[] params2 = new Object[2];
		params2[0] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		params2[1] = userinfo.getUserId();
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql2, params2);
		String selectuser = list1.size() > 0 ? "" : "and user.userid ='" +userinfo.getUserId() +"' " ;
		String sql = "select user.username, " +
		                     "user.userid, " +
				             "sum(wk.worktime) as worktime," +
				             "count(wk.worktime) as workday " +
				     "from cdata_worktime  wk join mstr_user user " +
                           "on wk.userid = user.userid " +
                     "where wk.year= ? " + 
                           "and wk.month = ? " + 
                           "and user.delflg ='0' "+
                           "and companyid = ?" +
                           selectuser +
                     "group by user.username,user.userid";
		Object[] params = new Object[3];
		params[0] = year;
		params[1] = month;
		params[2] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);

		Map<String, String[]> leaveinfo = getLeaveTime(year,month,userinfo,selectuser);
		
		// 系统当前时间取得
		SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
		String dateStr= wdate == null ? formattime.format(new Date()): wdate;
		List<String> done = new ArrayList<String>();
		// 结果取得整理（画面显示用）
		for (Object result : resultList) {
			Map<String, Object> row = (Map<String, Object>) result;
			String[] data = new String[7];
			// 用户名
			data[0] = row.get("username").toString();
			// 出勤时间
			data[1] = row.get("worktime").toString();
			
			String userid = row.get("userid").toString();
			String[] linfo = leaveinfo.containsKey(userid) ? leaveinfo.get(userid) : null;
			// 请假时间
			data[2] = linfo == null ? "0.0" : linfo[3];
			
			data[3] = userid;
			data[4] = dateStr;
			data[5] = row.get("workday").toString();
			data[6] = linfo == null ? "0" : linfo[2];
			dataList.add(data);
			done.add(userid);
		}
		
		for (String userid : leaveinfo.keySet()) {
			if(!done.contains(userid)){
				String[] linfo = leaveinfo.get(userid);
				String[] data = new String[7];
				// 用户名
				data[0] = linfo[1];
				// 出勤时间
				data[1] = "0.0";
				// 请假时间
				data[2] = linfo[3];
				
				data[3] = userid;
				data[4] = dateStr;
				data[5] = "0";
				data[6] = linfo[2];
				dataList.add(data);
			}
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
	
	
	private Map<String, String[]> getLeaveTime(String year,String month, User userinfo,String selectuser){
		DecimalFormat df2=new DecimalFormat("0.0");
		String sql = "select  user.userid, user.username, count(*) as lcount from cdata_leave cl "
				+ "join mstr_user user on user.userid = cl.userid "
				+ "where cl.year = ? and cl.month= ? and user.delflg = '0' and companyid = ?"
				+ selectuser
				+ "group by user.userid, user.username";
		Object[] params = new Object[3];
		params[0] = year;
		params[1] = month;
		params[2] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		List<Object> resultList = JdbcUtil.getInstance().excuteQuery(sql, params);
		Map<String, String[]> linfo = new HashMap<String, String[]>();
		for (Object result : resultList) {
			Map<String, Object> row = (Map<String, Object>) result;
			String[] data = new String[4];
			data[0] = row.get("userid").toString();
			data[1] = row.get("username").toString();
			data[2] = row.get("lcount").toString();
			data[3] = df2.format(((Long)row.get("lcount")) * 8);
			linfo.put(data[0], data);
		}
		return linfo;
	}

}
