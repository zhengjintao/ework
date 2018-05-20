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
import com.bwc.ework.common.Utils;
import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.WechatConsts;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;
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
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		
		// 发布
		if(subKbn != null && subKbn.length() > 0){
			String sqlcount = "select count(*) as count from cdata_notice";
			Long count = (Long)JdbcUtil.getInstance().executeQuerySingle(sqlcount, null);
			count++;
			String content = new String(request.getParameter("content"));
			String typekbn = "notice".equals(subKbn) ? "1" : "2";
			
			SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sysDate = formattime.format(new Date());
			
			String sql = "insert into cdata_notice value(?,?, ?, ?, ?, ?, ?, ?)";
			
			Object[] params = new Object[8];
			params[0] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
			params[1] = count.toString();
			params[2] = typekbn;
			params[3] = "";
			params[4] = content;
			params[5] = userinfo.getUserName();
			params[6] = sysDate;
			params[7] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			String companyid = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
			String sql2 = "SELECT * FROM cdata_companyuser com left join mstr_user com.userid=usr.userid where companyid=? and delflg=?";
			Object[] params2 = new Object[2];
			params2[0] = companyid;
			params2[1] = "0";
			List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
			
			SimpleDateFormat formattime2 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			final String now2 = formattime2.format(new Date());
			for (int i = 0; i < infolist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) infolist.get(i);
				final String openid = String.valueOf(set.get("openid"));
				
				if(openid == null || openid.length() < 10){
					continue;
				}
				final String ucompanyid = companyid;
				Thread t = new Thread(new Runnable() {
					public void run() {
						String url = Utils.createRedirectUrl(ucompanyid, openid, "home.do");
						sendTemplateMessage(openid, WechatConsts.templetid03, url, now2);
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
			
			String content = "1".equals(request.getParameter("type")) ? "公司运营正常" : "近期没活动";
			
			String sql = "select * from cdata_notice where companyid=? and type=? and delflg=? and createdate =(select max(createdate) from cdata_notice where companyid=? and type=? and delflg=? group by type ) limit 1";
			Object[] paramsnotice = new Object[6];
			paramsnotice[0] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
			paramsnotice[1] = "1".equals(request.getParameter("type")) ? "1" : "2";  // notice kbn
			paramsnotice[2] = "0";
			paramsnotice[3] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
			paramsnotice[4] = "1".equals(request.getParameter("type")) ? "1" : "2";  // notice kbn
			paramsnotice[5] = "0";
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
		doGet(request, response);
	}
	
	public static String sendTemplateMessage(String touser, String template_id, String url, String time) {
		String msg = "--Begin set accesstoken--<br>";
		String token = AccessTokenGeter.getStrAccessToken();
		String sendUrl = URLProducer.GetTemplateSendUrl(token);
		 msg = msg+ "--url" + sendUrl +"<br>";
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
		k2Json.put("value", "待查看");
		k2Json.put("color", "#DC143C");
		// remark
		JSONObject rmkJson = new JSONObject();
		rmkJson.put("value", "点击可快速进行查看, GO>>");
		rmkJson.put("color", "#173177");
		
		dataJson.put("first", fstJson);
		dataJson.put("keyword1", k1Json);
		dataJson.put("keyword2", k2Json);
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
