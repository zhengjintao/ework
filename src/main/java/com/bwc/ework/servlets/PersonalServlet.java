package com.bwc.ework.servlets;

import java.io.IOException;
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
