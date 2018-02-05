package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class AddServlet
 */
public class AddWorkDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWorkDayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("wdate");
		String begin = request.getParameter("wbegin");
		String end = request.getParameter("wend");
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		if(userId == null || userId.length() == 0){
			response.sendRedirect("login.jsp");
			return;
		}
		
		JdbcUtil  jdbc = new JdbcUtil();
		String sql = "select * from mstr_user";
		List<Object> list = jdbc.excuteQuery(sql,null);
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(date);
		response.getWriter().append("Ser  ved at: ").append(begin);
		response.getWriter().append("Served at: ").append(end);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		

	}

}
