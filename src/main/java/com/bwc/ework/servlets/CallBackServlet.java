package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;

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
			errmsg = "－－开始获取openid－－<br>";
			String url = URLProducer.GetUserAuthUrl(code);
			JSONObject jsonObject = HttpRequestor.httpGetProc(url);
			
			if(!jsonObject.has("openid")){
				errmsg = errmsg + "－－获取openid失败－－<br>";
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
				if("0".equals((String)info.get("delflg"))){
					String ueserid = (String)info.get("userid");
					String password = (String)info.get("password");
					request.getRequestDispatcher("login.do?rembpwd=1&userid="+ ueserid + "&password=" + password).forward(request, response);
				}else{
					response.sendRedirect("login.do");
				}
				
				return;
			}else{
				String accessToken = jsonObject.getString("access_token");
				errmsg = errmsg + "－－开始获取userInfo－－<br>";
				String infoUrl = URLProducer.GetUserInfoUrl(accessToken, openid);
				JSONObject userInfo = HttpRequestor.httpGetProc(infoUrl);
				
				if(!userInfo.has("openid")){
					errmsg = errmsg + "－－获取userInfo失败－－<br>";
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				}
				
				String euserid = openid;
				String username = userInfo.getString("nickname");
				String sex = "1".equals(userInfo.get("sex").toString()) ? "M" : "F";
				String password = "111111";
				String sql2 = "insert into mstr_user values(?,?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params2 = new Object[12];
				
				params2[0] = openid;
				params2[1] = filterEmoji(username);
				params2[2] = "111111";
				params2[3] = "0";
				params2[4] = "09:30:00.0000";
				params2[5] = "18:30:00.0000";
				params2[6] = sex;
				params2[7] = "2";
				params2[8] = null;
				params2[9] = "";
				params2[10] = openid;
				params2[11] = "1";
				
				errmsg = errmsg + "－－新规做成用户－－<br>";
				JdbcUtil.getInstance().executeUpdate(sql2, params2);
				
				String text = "账号：" + openid + "<br>";
				text = text + "姓名：" + username+ username.length() + "<br>";
				text = text + "＃若为无关人员，可在人员管理模块禁用该用户" + "<br>";
				
				final String utext = text;
				
				errmsg = errmsg + "－－新规做成用户管理员消息提醒－－<br>";
				Thread t = new Thread(new Runnable() {
					public void run() {
						try {
							try {
								SendMailFactory.getInstance().getMailSender().sendMessage(getadmminusermailadd(),"新用户加入提醒", utext, null);
							} catch (MessagingException e) {
							}
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				});
				t.start();
				
				errmsg = errmsg + "－－新用户自动登录－－<br>";
				request.getRequestDispatcher("login.do?rembpwd=1&userid="+ euserid + "&password=" + password).forward(request, response);
				return;
			}

		} catch (Exception e) {
			request.setAttribute("errmsg", errmsg + "<br><br>" + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
	}
	
	private static boolean isNotEmojiCharacter(char codePoint) 
	{ 
	return (codePoint == 0x0) || 
	(codePoint == 0x9) || 
	(codePoint == 0xA) || 
	(codePoint == 0xD) || 
	((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || 
	((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || 
	((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
	} 
	/** 
	* 过滤emoji 或者 其他非文字类型的字符 
	* @param source 
	* @return 
	*/ 
	public static String filterEmoji(String source) 
	{ 
	int len = source.length(); 
	StringBuilder buf = new StringBuilder(len); 
	for (int i = 0; i < len; i++) 
	{ 
	char codePoint = source.charAt(i); 
	if (isNotEmojiCharacter(codePoint)) 
	{ 
	buf.append(codePoint); 
	} else{

	buf.append("*");

	}
	} 
	return buf.toString(); 
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

		return list;
	}
}
