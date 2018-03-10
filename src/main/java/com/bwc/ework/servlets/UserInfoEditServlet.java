package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class UserEditServlet
 */
public class UserInfoEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String sql = "select * from mstr_user where userid=? and delflg=?";
		Object[] params = new Object[2];
		params[0] = userid;
		params[1] = "0";
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		Map<String, Object> info = (Map<String, Object>)userinfo.get(0);

		String username = (String)info.get("username");
		String sex = (String)info.get("sex");
		String mail = info.get("mail") == null ? "" : (String)info.get("mail");
		String birthday = info.get("birthday") == null ? "" : String.valueOf(info.get("birthday"));
		String authflg = (String)info.get("authflg");
		
		//初期化
		if(!"1".equals(request.getParameter("saveFlg"))){
			request.setAttribute("userid", userid);
			request.setAttribute("username", username);
			request.setAttribute("hasuserid", userid != null);
			request.setAttribute("isfemale", "F".equals(sex));
			request.setAttribute("isadmin", "1".equals(authflg));
			request.setAttribute("mail", mail);
			request.setAttribute("birthday", birthday);
			request.getRequestDispatcher("userInfoEdit.jsp").forward(request, response);
		}else{
			// 更新数据
			String updatesql = "update mstr_user set mail=? where userid=?";
			Object[] updateparams = new Object[2];
			updateparams[0] = request.getParameter("email");
			updateparams[1] = request.getParameter("euserid");
			JdbcUtil.getInstance().executeUpdate(updatesql, updateparams);
			
			if(request.getParameter("ebirthday") != null){
				updateparams = new Object[2];
				updateparams[0] = request.getParameter("ebirthday");
				updateparams[1] = request.getParameter("euserid");
				updatesql = "update mstr_user set birthday= ? where userid=?";
				JdbcUtil.getInstance().executeUpdate(updatesql, updateparams);
			}
			
			request.getRequestDispatcher("personal.do").forward(request, response);
			return;
		}
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
