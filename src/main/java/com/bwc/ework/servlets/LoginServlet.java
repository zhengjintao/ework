package com.bwc.ework.servlets;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("userid");
		String pwd = request.getParameter("password");
		String rembpwd = request.getParameter("rembpwd");
		
		Cookie[] cookies = request.getCookies();
		
		if(user == null && pwd == null && cookies!=null){
			for(int i=0; i < cookies.length; i++){
				Cookie ck = cookies[i];
				System.out.println(ck.getName()+"--->"+ck.getValue());
				if("ucookies".equals(ck.getName())){
					user = ck.getValue();
				}
				
				if("pcookies".equals(ck.getName())){
					pwd = ck.getValue();
				}
			}
		}
		
		if(user == null || user.length() == 0 || pwd == null || pwd.length() == 0){
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		String sql = "select * from mstr_user where userid=? and password=? and delflg=?";
		Object[] params = new Object[3];
		params[0] = user;
		params[1] = pwd;
		params[2] = "0";
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(userinfo == null || userinfo.size() != 1){
			request.setAttribute("errmsg", "用户名和密码不正确！");
			request.setAttribute("userid", user);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		Map<String, Object> info = (Map<String, Object>)userinfo.get(0);
		User userdata = new User();
		userdata.setUserId((String)info.get("userid"));
		userdata.setUserName((String)info.get("username"));
		userdata.setDelflg((String)info.get("delflg"));
		userdata.setBeginTime((Time)info.get("begintime"));
		userdata.setEndTime((Time)info.get("endtime"));
		userdata.setSex((String)info.get("sex"));
		userdata.setUserPwd(HashEncoder.getResult((String)info.get("password")));
		userdata.setAuthflg((String)info.get("authflg"));
	    
		HttpSession session = request.getSession();
		session.setAttribute("userinfo", userdata);
		
		if("on".equals(rembpwd)){
			Cookie ucookies = new Cookie("ucookies", user);
			ucookies.setMaxAge(604800);
			Cookie pcookies = new Cookie("pcookies", pwd);
			pcookies.setMaxAge(604800);
			response.addCookie(ucookies);
			response.addCookie(pcookies);
		}
		


		response.sendRedirect("list.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
