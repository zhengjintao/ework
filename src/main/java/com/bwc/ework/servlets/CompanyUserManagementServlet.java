package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.form.User;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		
		if("auth".equals(mode)){
			auth(request, response);
		}else{
			init(request, response);
		}
	}
	
	private void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String companyid = request.getParameter("companyid");
		if(companyid ==null){
			companyid = userinfo.getMaincompanyid();
		}
		String rolekbn = request.getParameter("rolekbn");
		String userid = request.getParameter("userid");
		String sql = "update cdata_companyuser set rolekbn=? where companyid =? and userid=?";
		Object[] params = new Object[3];
		params[0] = "1".equals(rolekbn) ? "2" : "1";
		params[1] = companyid;
		params[2] = userid;
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
		init(request, response);
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String companyid = request.getParameter("companyid");
		if(companyid ==null){
			companyid = userinfo.getMaincompanyid();
		}

		String sql = "select * from cdata_companyuser com left join mstr_user usr on com.userid = usr.userid where com.companyid=? and com.delflg=? and com.rolekbn !=?";
		Object[] params = new Object[3];
		params[0] = companyid;
		params[1] = "0";
		params[2] = "0";
		List<Object> userinfo2 = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		List<String[]> userinfolist = new ArrayList<String[]>();
		for (Object data : userinfo2) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[6];
			each[0] = row.get("userid").toString();
			each[1] = row.get("username").toString();
			each[2] = "1".equals(row.get("rolekbn").toString()) ? "取消管理员" : "设置管理员";
			each[3] = row.get("sex").toString();
			each[4] = row.get("rolekbn").toString();
			each[5] = "F".equals(each[3]) ? "assets/images/rachel.png" : "assets/images/christian.jpg";
			
			userinfolist.add(each);
		}
		
		request.setAttribute("userinfo", userinfolist);
		request.getRequestDispatcher("companyusermanagement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
