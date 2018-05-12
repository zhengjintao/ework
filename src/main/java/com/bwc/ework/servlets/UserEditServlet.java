package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class UserEditServlet
 */
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String delflg = request.getParameter("delflg");
		if(delflg != null){
			String sql = "update mstr_user set delflg=? where userid=?";
			Object[] params = new Object[2];
			params[0] = "1";
			params[1] = request.getParameter("deluserid");
			JdbcUtil.getInstance().executeUpdate(sql, params);
			request.getRequestDispatcher("usermanagement.do").forward(request, response);
			return;
		}
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String sex = request.getParameter("sex");
		String authflg = request.getParameter("authflg");
		String edit = request.getParameter("edit");
		// 提交
		if(edit != null){
			// 新建
			if("false".equals(edit)){
				String sql1 = "select * from mstr_user where userid=?";
				Object[] params1 = new Object[1];
				params1[0] = request.getParameter("euserid");
				List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql1, params1);
				String eusername = request.getParameter("eusername");
				
				if(list1.size() > 0){
					request.setAttribute("errmsg", "该账号已存在");
					request.setAttribute("userid", request.getParameter("euserid"));
					request.setAttribute("username", eusername);
					request.setAttribute("hasuserid", false);
					request.setAttribute("isfemale", "F".equals(request.getParameter("esex")));
					request.setAttribute("isadmin", "1".equals(request.getParameter("eauthflg")));
					request.getRequestDispatcher("useredit.jsp").forward(request, response);
					return;
				}else{
					//更新
					String sql = "insert into mstr_user values(?,?,?,?,?,?,?,?,?,?,?)";
					Object[] params = new Object[11];
					
					params[0] = request.getParameter("euserid");
					params[1] = eusername;
					params[2] = "111111";
					params[3] = "0";
					params[4] = "09:30:00.0000";
					params[5] = "18:30:00.0000";
					params[6] = request.getParameter("esex");
					params[7] = request.getParameter("eauthflg");
					params[8] = "";
					params[9] = "";
					params[10] = "";
					JdbcUtil.getInstance().executeUpdate(sql, params);
				}
			}else{
				//更新
				String sql = "update mstr_user set username=? , sex=? , authflg=? where userid=?";
				Object[] params = new Object[4];
				
				String eusername = request.getParameter("eusername");
				params[0] = eusername;
				params[1] = request.getParameter("esex");
				params[2] = request.getParameter("eauthflg");
				params[3] = request.getParameter("euserid");
				JdbcUtil.getInstance().executeUpdate(sql, params);
				
				if("on".equals(request.getParameter("initpwd"))){
					//更新密码
					String sql2 = "update mstr_user set password=? where userid=?";
					Object[] params2 = new Object[2];
					params2[0] = "111111";
					params2[1] = request.getParameter("euserid");
					JdbcUtil.getInstance().executeUpdate(sql2, params2);
				}
			}
			request.getRequestDispatcher("usermanagement.do").forward(request, response);
			return;
			
		}else{
			request.setAttribute("userid", userid);
			request.setAttribute("username", username);
			request.setAttribute("hasuserid", userid != null);
			request.setAttribute("isfemale", "F".equals(sex));
			request.setAttribute("isadmin", "1".equals(authflg));
			request.getRequestDispatcher("useredit.jsp").forward(request, response);
			return;
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
