package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class RedirectAutoLoginServlet
 */
public class RedirectAutoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectAutoLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companyid=request.getParameter("companyid");
		String openid =request.getParameter("openid");
		String tourl=request.getParameter("tourl");
		
		if(companyid==null || companyid.length() ==0
				|| openid==null || openid.length() ==0
				|| tourl==null || tourl.length() ==0){
			response.sendRedirect("login.do");
			return;
		}
		
		String sql = "select * from mstr_user usr join cdata_companyuser com on usr.userid=com.userid where com.companyid=? and usr.openid=? and usr.delflg='0'";
		Object[] params = new Object[2];
		params[0] = companyid;
		params[1] = openid;
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(userinfo != null && userinfo.size() ==1){
			Map<String, Object> info = (Map<String, Object>)userinfo.get(0);
			if("0".equals((String)info.get("delflg"))){
				String ueserid = (String)info.get("userid");
				String password = (String)info.get("password");
				request.getRequestDispatcher("login.do?rembpwd=1&userid="+ ueserid + "&password=" + password+"&tourl="+ tourl).forward(request, response);
			}else{
				response.sendRedirect("login.do");
			}
		}
		
		response.sendRedirect("login.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
