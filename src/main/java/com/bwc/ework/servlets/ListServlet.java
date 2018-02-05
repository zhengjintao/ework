package com.bwc.ework.servlets;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class ListServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
	    
		// 
		if("true".equals(request.getParameter("subKbn"))){
			String sql = "select * from cdata_worktime where userid=? and date=?";
			Object[] params = new Object[2];
			params[0] = userinfo.getUserId();
			params[1] = request.getParameter("wdate");
			List<Object> list = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(request.getParameter("wdate").toString());
			String userid = userinfo.getUserId();
			String year = date1.getYear();
			String month = date1.getMonth();
			String day = date1.getDay();
			String date = request.getParameter("wdate");
			String begin = request.getParameter("wbegin");
			String end = request.getParameter("wend");
					
			// 数据存在更新操作
			if(list.size()>0){
				String updateSql = "update cdata_worktime set year=?,month=?,day=?,date=?,begintime=?,endtime=?"
						+ " where userid=? and date=?";
				Object[] updateparams = new Object[8];
				updateparams[0] = year;
				updateparams[1] = month;
				updateparams[2] = day;
				updateparams[3] = date;
				updateparams[4] = begin;
				updateparams[5] = end;
				updateparams[6] = userid;
				updateparams[7] = date;
				JdbcUtil.getInstance().executeUpdate(updateSql, updateparams);
			}else{
				String insertSql = "insert into cdata_worktime value(?,?,?,?,?,?,?)";
				Object[] insertparams = new Object[7];
				insertparams[0] = userid;
				insertparams[1] = year;
				insertparams[2] = month;
				insertparams[3] = day;
				insertparams[4] = date;
				insertparams[5] = begin;
				insertparams[6] = end;
				JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
			}
			
			// 日期设定
			request.setAttribute("sysDate", date);
			// 默认开始时间
			request.setAttribute("defaultBeginTime",begin);
			// 默认结束时间
			request.setAttribute("defaultEndTime", end);
		}else{
			// 系统当前时间取得
			SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
			
			// 日期设定
			request.setAttribute("sysDate", formattime.format(new Date()));
			
			String sql = "select * from cdata_worktime where userid=? and date=?";
			Object[] params = new Object[2];
			params[0] = userinfo.getUserId();
			params[1] = formattime.format(new Date());
			List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			if(list1.size()>0){
				Map<String, Object> set = (Map<String, Object>)list1.get(0);
				// 默认开始时间
				request.setAttribute("defaultBeginTime",set.get("begintime").toString());
				// 默认结束时间
				request.setAttribute("defaultEndTime", set.get("endtime").toString());
			}else{
				// 默认开始时间
				request.setAttribute("defaultBeginTime",userinfo.getBeginTime().toString());
				// 默认结束时间
				request.setAttribute("defaultEndTime", userinfo.getEndTime().toString());
			}
		}
		
		RequestDispatcher re = request.getRequestDispatcher("list.jsp");
		re.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
