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
import com.bwc.ework.common.wechat.URLProducer;
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
		// 来自微信公众号
		if(request.getParameter("from") != null ){
			String callbackUrl="http://www.freertokyo.com"+ request.getContextPath()+"/callback.do";
	        response.sendRedirect(URLProducer.GetAuthUrl(callbackUrl));
	        return;
		}
		
		String user = request.getParameter("userid");
		String pwd = HashEncoder.getResult(request.getParameter("password"));
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
		
		String sql = "select u.userid as uuserid, u.*, c.*, mc.* from mstr_user u left join mstr_user_comp c on u.userid=c.userid and c.delflg='0' and c.defaultflg='1' left join mstr_company mc on c.companyid = mc.companyid where u.userid=? and u.delflg='0'";
		Object[] params = new Object[1];
		params[0] = user;
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(userinfo == null || userinfo.size() != 1){
			request.setAttribute("errmsg", "用户名不正确！");
			request.setAttribute("userid", user);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		Map<String, Object> info = (Map<String, Object>)userinfo.get(0);
		String enpwd = HashEncoder.getResult((String)info.get("password"));
		
		if(!enpwd.equals(pwd)){
			request.setAttribute("errmsg", "密码不正确！<br>(注意：密码区分大小写)");
			request.setAttribute("userid", user);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		User userdata = new User();
		userdata.setUserId((String)info.get("uuserid"));
		userdata.setUserName((String)info.get("username"));
		userdata.setDelflg((String)info.get("delflg"));
		userdata.setBeginTime((Time)info.get("begintime"));
		userdata.setEndTime((Time)info.get("endtime"));
		userdata.setSex((String)info.get("sex"));
		userdata.setUserPwd(enpwd);
		userdata.setAuthflg((String)info.get("authflg"));
		userdata.setMaincompanyid((String)info.get("companyid"));
		userdata.setMaincompanyname((String)info.get("companynm"));
		userdata.setRest((String)info.get("rest"));
	    
		HttpSession session = request.getSession();
		session.setAttribute("userinfo", userdata);
		
		if("on".equals(rembpwd)){
			Cookie ucookies = new Cookie("ucookies", user);
			ucookies.setMaxAge(604800);
			Cookie pcookies = new Cookie("pcookies", enpwd);
			pcookies.setMaxAge(604800);
			response.addCookie(ucookies);
			response.addCookie(pcookies);
		}
		
		String rurl = userdata.getMaincompanyid() == null ? "personal.do" : "list.do";
		response.sendRedirect(rurl);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
