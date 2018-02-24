package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class EditPasswordServlet
 */
public class EditPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subKbn = request.getParameter("subKbn");
		if(subKbn != null && subKbn.length() > 0){
			String password = request.getParameter("password");
			String newpassword = request.getParameter("newpassword");
			String repassword = request.getParameter("repassword");
			
			Boolean updatepwd = true;
			String encode = HashEncoder.getResult(password);
			HttpSession session = request.getSession();
			User userinfo = (User)session.getAttribute("userinfo");
			if(!encode.equals(userinfo.getUserPwd())){
				updatepwd = false;
			}
			
			if(!repassword.equals(newpassword)){
				updatepwd = false;
			}
			
			if(updatepwd){
				String sql = "update mstr_user set password=? , endtime=? where userid=?";
				Object[] params = new Object[3];
				params[0] = newpassword;
				params[2] = userinfo.getUserId();
				JdbcUtil.getInstance().executeUpdate(sql, params);
				
				userinfo.setUserPwd(HashEncoder.getResult(newpassword));
				session.setAttribute("userinfo", userinfo);
				request.getRequestDispatcher("personal.do").forward(request, response);
			}else{
				// 修改模式
				request.setAttribute("subKbn", "edit");
				request.getRequestDispatcher("editpassword.jsp").forward(request, response);
			}
		}else{
			// 修改模式
			request.setAttribute("subKbn", "edit");
			request.getRequestDispatcher("editpassword.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
