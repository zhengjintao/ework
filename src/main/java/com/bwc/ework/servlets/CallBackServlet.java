package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
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
			String openid = jsonObject.getString("openid");
			
			String sql = "select * from mstr_user where openid=?";
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
				String accessToken = jsonObject.getString("access_token");
				String infoUrl = URLProducer.GetUserInfoUrl(accessToken, openid);
				JSONObject userInfo = HttpRequestor.httpGetProc(infoUrl);
				
				if(!userInfo.has("openid")){
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				}
				
				String euserid = openid;
				String username = userInfo.getString("nickname");
				String sex = "1".equals(userInfo.getString("sex")) ? "M" : "F";
				String password = "111111";
				String sql2 = "insert into mstr_user values(?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params2 = new Object[11];
				
				params2[0] = openid;
				params2[1] = username;
				params2[2] = "111111";
				params2[3] = "0";
				params2[4] = "09:30:00.0000";
				params2[5] = "18:30:00.0000";
				params2[6] = sex;
				params2[7] = "2";
				params2[8] = null;
				params2[9] = "";
				params2[10] = openid;
				JdbcUtil.getInstance().executeUpdate(sql2, params2);
				
				String text = "账号：" + openid + "<br>";
				text = text + "姓名：" + username + "<br>";
				text = text + "＃若为无关人员，可在人员管理模块禁用改用户" + "<br>";
				
				final String utext = text;
				
				Thread t = new Thread(new Runnable() {
					public void run() {
						try {
							try {
								SendMailFactory.getInstance().getMailSender().sendMessage(getadmminusermailadd(),"新用户注册提醒", utext);
							} catch (MessagingException e) {
							}
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				t.start();
				
				response.sendRedirect("login.do?rembpwd=1&userid="+ euserid + "&password=" + password);
				return;
			}

			//errmsg = errmsg + msg;
		} catch (Exception e) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
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
	
	private List<String> getadmminusermailadd() {
		// 管理员邮箱取得
		String sql = "select mail from mstr_user where authflg=? and delflg='0' and mail is not null";
		Object[] params = new Object[1];
		params[0] = "1";
		List<Object> userlist = JdbcUtil.getInstance().excuteQuery(sql, params);
		List<String> list = new ArrayList<String>();
		if (userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) userlist.get(i);
				if (set.get("mail") != null) {
					list.add(set.get("mail").toString());
				}
			}
		}
		
		list.clear();
		list.add("xiaonei0912@qq.com");

		return list;
	}
}
