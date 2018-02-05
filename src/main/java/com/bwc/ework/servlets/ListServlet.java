package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
			com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(request.getParameter("wdate").toString());
			String userid = userinfo.getUserId();
			String year = date1.getYear();
			String month = date1.getMonth();
			String day = date1.getDay();
			String date = (String) request.getParameter("wdate");
			String begin = (String) request.getParameter("wbegin");
			String end = (String) request.getParameter("wend");
			
			String sql = "select * from cdata_worktime where userid=? and date=?";
			Object[] params = new Object[3];
			params[0] = userinfo.getUserId();
			params[1] = request.getParameter("wdate");
			List<Object> list = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			// 数据存在更新操作
			if(list.size()>0){
				String updateSql = "update cdata_worktime set year=?,month=?，day=?，date=?，begin=?，end=?"
						+ " where userid=? and date=?";
				Object[] updateparams = new Object[3];
				params[0] = userinfo.getUserId();
				params[1] = request.getParameter("wdate");
				JdbcUtil.getInstance().executeUpdate(updateSql, updateparams);
			}else{
				String updateSql = "update cdata_worktime set year=?,month=?，day=?，date=?，begin=?，end=?"
						+ " where userid=? and date=?";
				Object[] updateparams = new Object[3];
				params[0] = userinfo.getUserId();
				params[1] = request.getParameter("wdate");
				JdbcUtil.getInstance().executeUpdate(updateSql, updateparams);
			}
		}
		
		// 系统当前时间取得
		SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
		
		// 日期设定
		request.setAttribute("sysDate", formattime.format(new Date()));

		// 默认开始时间
		request.setAttribute("defaultBeginTime",userinfo.getBeginTime().toString());
		// 默认结束时间
		request.setAttribute("defaultEndTime", userinfo.getEndTime().toString());
		
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
