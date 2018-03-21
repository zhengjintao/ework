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

import org.json.JSONObject;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.Consts;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;
import com.bwc.ework.common.wechat.tmpmsg.TemplateMessageUtil;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class EditNoticeServlet
 */
public class EditNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subKbn = request.getParameter("subKbn");
		// 发布
		if(subKbn != null && subKbn.length() > 0){
			String sqlcount = "select count(*) as count from cdata_notice";
			Long count = (Long)JdbcUtil.getInstance().executeQuerySingle(sqlcount, null);
			count++;
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "utf-8");
			String typekbn = "notice".equals(subKbn) ? "1" : "2";
			
			HttpSession session = request.getSession();
			User userinfo = (User)session.getAttribute("userinfo");
			
			SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sysDate = formattime.format(new Date());
			
			String sql = "insert into cdata_notice value(?, ?, ?, ?, ?, ?, ?)";
			
			Object[] params = new Object[7];
			params[0] = count.toString();
			params[1] = typekbn;
			params[2] = "";
			params[3] = content;
			params[4] = userinfo.getUserName();
			params[5] = sysDate;
			params[6] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			String sql2 = "SELECT * FROM mstr_user where delflg=?";
			Object[] params2 = new Object[1];
			params2[0] = "0";
			List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
			
			SimpleDateFormat formattime2 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			final String now2 = formattime2.format(new Date());
			for (int i = 0; i < infolist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) infolist.get(i);
				final String openid = String.valueOf(set.get("openid"));
				
				if(openid == null || openid.length() < 10){
					continue;
				}
				final String username = String.valueOf(set.get("username"));
				Thread t = new Thread(new Runnable() {
					public void run() {
						sendTemplateMessage(openid, "b-FwE9zb25Dno6zxqJ3pcDMt3hLMx2xGOV3W2Cfg_eY", username, now2);
					}
				});
				t.start();
			}
			
			request.getRequestDispatcher("home.do").forward(request, response);
		}else{
			// 编辑
			subKbn = "1".equals(request.getParameter("type")) ? "notice" : "event";
			String title = "1".equals(request.getParameter("type")) ? "通知" : "活动";
			request.setAttribute("subKbn", subKbn);
			request.setAttribute("title", title);
			
			String content = "1".equals(request.getParameter("type")) ? "最近没啥事，各自安好！" : "近期没活动，自己high，自己浪！";
			
			String sql = "select * from cdata_notice where type=? and delflg=? and createdate =(select max(createdate) from cdata_notice where type=? and delflg=? group by type ) limit 1";
			Object[] paramsnotice = new Object[4];
			paramsnotice[0] = "1".equals(request.getParameter("type")) ? "1" : "2";  // notice kbn
			paramsnotice[1] = "0";
			paramsnotice[2] = "1".equals(request.getParameter("type")) ? "1" : "2";  // notice kbn
			paramsnotice[3] = "0";
			List<Object> notices = JdbcUtil.getInstance().excuteQuery(sql, paramsnotice);
		   if(notices != null && notices.size() > 0){
			   String temp = (String)((Map<String, Object>)notices.get(0)).get("content");
			   content = temp.length() > 0 ? temp : content;
		   }
	
			request.setAttribute("content", content);
			
			request.getRequestDispatcher("editnotice.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static String sendTemplateMessage(String touser, String template_id, String username, String time) {
		String msg = "--Begin set accesstoken--<br>";
		String token = "7_XhXIHBiJhmrZ5l8e1h1_t9p7UUPNmHd8GCjGuy2lRc9zgQoE8YC4jkgi65gK3WMX3eClTZ8kPGq8o-q3fE8dDzw0qB6mYFmdu2SFmp_DS82npK4Chn15lW5vJgehoFLy-CN-GGZxx6uNhOIXOEIjAEACSW";
		token = AccessTokenGeter.getStrAccessToken();
		String sendUrl = URLProducer.GetTemplateSendUrl(token);
		 msg = msg+ "--url" + sendUrl +"<br>";
		// post请求数据
		String url = "http://www.freertokyo.com/ework/home.do";
		// data
		JSONObject dataJson = new JSONObject();
		// first
		JSONObject fstJson = new JSONObject();
		fstJson.put("value", "注意！有新的通知／活动");
		// keyword1
		JSONObject k1Json = new JSONObject();
		k1Json.put("value", "新活动");
		// keyword2
		JSONObject k2Json = new JSONObject();
		k2Json.put("value", time);
		// keyword3
		JSONObject k3Json = new JSONObject();
		k3Json.put("value", "待查看");
		k3Json.put("color", "#DC143C");
		// remark
		JSONObject rmkJson = new JSONObject();
		rmkJson.put("value", "点击可快速进行查看, GO>>");
		rmkJson.put("color", "#173177");
		
		dataJson.put("first", fstJson);
		dataJson.put("keyword1", k1Json);
		dataJson.put("keyword2", k2Json);
		dataJson.put("keyword3", k3Json);
		dataJson.put("remark", rmkJson);

//		{{first.DATA}}
//		姓名：{{keyword1.DATA}}
//		时间：{{keyword2.DATA}}
//		状态：{{keyword3.DATA}}
//		{{remark.DATA}}
		
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("touser", touser);
		jsonmsg.put("template_id", template_id);
		jsonmsg.put("url", url);
		jsonmsg.put("data", dataJson);
		msg = msg + "<br>" + "--begin send temp message" + jsonmsg.toString() + "<br>";
		try {
			String rep = HttpRequestor.httpPostProc(sendUrl, jsonmsg);
			
			msg = msg + "<br>" + "--Success send temp message" + "<br>";
			msg = msg + "<br>" + "--respone data" + rep + "<br>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = msg + "<br>" + "--Failed send temp message" + e.getMessage() + "<br>";
		}
		
		return msg;
	}

}
