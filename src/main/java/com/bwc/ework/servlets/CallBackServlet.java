package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.Consts;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;
import com.bwc.ework.common.wechat.meta.AccessToken;
import com.bwc.ework.common.wechat.tmpmsg.TemplateMessageUtil;

/**
 * Servlet implementation class CallBackServlet
 */
public class CallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CallBackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		if (code == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		String errmsg = null;
		try {
			String url = URLProducer.GetUserAuthUrl(code);
			JSONObject jsonObject = HttpRequestor.httpGetProc(url);
			
			if(!jsonObject.has("openid")){
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			errmsg = jsonObject.toString() + "<br>";

			String openid = jsonObject.getString("openid");
			
			String accessToken = jsonObject.getString("access_token");
			//errmsg = errmsg + "--GetUserInfoUrl begin...<br>";
			String infoUrl = URLProducer.GetUserInfoUrl(accessToken, openid);
			JSONObject userInfo = HttpRequestor.httpGetProc(infoUrl);

			errmsg = errmsg + userInfo.toString() + "<br>";
			
			List<String> list = new ArrayList<String>();
			list.add("xiaonei0912@qq.com");
			SendMailFactory.getInstance().getMailSender().sendMessage(list, "openid", errmsg);
			
			//errmsg = errmsg + "--GetUserInfoUrl end...<br><br>";
			//errmsg = " openid: " +openid + "<br>";
			//String openid= "ofXqDwsbmAUZrHh85BuJkBwSpfaA1";
			/*String sql = "select * from mstr_user where openid=?";
			Object[] params = new Object[1];
			params[0] = openid;
			List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);

			if(userinfo != null && userinfo.size() >0){
				Map<String, Object> info = (Map<String, Object>)userinfo.get(0);
				String ueserid = (String)info.get("userid");
				String password = (String)info.get("password");
				response.sendRedirect("login.do?rembpwd=1&userid="+ ueserid + "&password=" + password);
				return;
			}else{
				//errmsg = "openid :" + openid;
				String euserid = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); 
				String password = "111111";
				String sql2 = "insert into mstr_user values(?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params2 = new Object[11];
				
				params2[0] = euserid;
				params2[1] = "用户" + euserid;
				params2[2] = "111111";
				params2[3] = "0";
				params2[4] = "09:30:00.0000";
				params2[5] = "18:30:00.0000";
				params2[6] = "M";
				params2[7] = "2";
				params2[8] = null;
				params2[9] = "";
				params2[10] = openid;
				JdbcUtil.getInstance().executeUpdate(sql2, params2);
				response.sendRedirect("login.do?rembpwd=1&userid="+ euserid + "&password=" + password);
				return;
			}*/

			String msg = TemplateMessageUtil.sendTemplateMessage(openid, Consts.templetid, "测试用户", "2018-12-2");
			//errmsg = errmsg + msg;
		} catch (Exception e) {
			/*errmsg = errmsg + "--call back error is happened...<br>";
			errmsg = errmsg + e.getMessage();*/
		}

		request.setAttribute("errmsg", errmsg);
		request.getRequestDispatcher("error.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
