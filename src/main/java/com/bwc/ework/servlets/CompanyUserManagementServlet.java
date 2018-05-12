package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class UserManagmentServlet
 */
public class CompanyUserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyUserManagementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = "select * from mstr_user where delflg=? and authflg !=?";
		Object[] params = new Object[2];
		params[0] = "0";
		params[1] = "0";
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		List<String[]> userinfolist = new ArrayList<String[]>();
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[6];
			each[0] = row.get("userid").toString();
			each[1] = row.get("username").toString();
			each[2] = row.get("password").toString();
			each[3] = row.get("sex").toString();
			each[4] = row.get("authflg").toString();
			each[5] = "F".equals(each[3]) ? "assets/images/rachel.png" : "assets/images/christian.jpg";
			
			userinfolist.add(each);
		}
		
		request.setAttribute("userinfo", userinfolist);
		request.getRequestDispatcher("usermanagement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
