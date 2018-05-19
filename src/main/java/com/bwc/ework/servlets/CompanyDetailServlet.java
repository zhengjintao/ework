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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.WechatConsts;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class CompanyDetailServlet
 */
public class CompanyDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyDetailServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("add".equals(mode)){
			this.add(request, response);
		}
		else{
			this.init(request, response);
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String companyid = request.getParameter("companyid");
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String sql = "SELECT * FROM mstr_user_comp where userid=?";
		Object[] params = new Object[1];
		params[0] = userinfo.getUserId();
		List<Object> result = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(result.size() ==0){
			params = new Object[1];
			params[0] = userinfo.getUserId();
			String selsql = "SELECT * FROM cdata_userapply usr left join mstr_company com on usr.companyid=com.companyid where usr.userid=? and usr.status='0'";
			List<Object> result2 = JdbcUtil.getInstance().excuteQuery(selsql, params);
			boolean haserr=false;
			for (Object data : result2) {
				Map<String, Object> row = (Map<String, Object>) data;
				String ucompanyid=row.get("companyid").toString();
				String companynm = row.get("companynm").toString();
				request.setAttribute("errmsg", "申请失败，每人最多只允许申请加入一个公司<br>"  + "已申请加入下列公司等待审核中"+ "<br>" + "编号：" + ucompanyid+ "<br>" + "名称：" + companynm);
				haserr=true;
				break;
			}
			
			if(!haserr){
				sql = "insert into cdata_userapply values(?,?,?)";
				params = new Object[3];
				params[0] = companyid;
				params[1] = userinfo.getUserId();
				params[2] = "0";
				JdbcUtil.getInstance().executeUpdate(sql, params);
				
				// 邮件通知公司管理员进行审批
				String text = "申请人：" + userinfo.getUserName() + "<br>";
				text = text + "＃请尽快进入系统进行审批，相关模块路径［个人］－［公司］- [申请审批]" + "<br>";
				
				final String utext = text;
				final String ucompanyid = companyid;
				
				Thread t = new Thread(new Runnable() {
					public void run() {
						try {
							try {
								SendMailFactory.getInstance().getMailSender().sendMessage(getadmminusermailadd(ucompanyid),"新人员申请加入公司提醒", utext, null);
							} catch (MessagingException e) {
							}
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				});
				t.start();
				
				// 公众号消息通知公司管理员审批
				List<String> infolist = getadmminuseropenid(ucompanyid);
				String companyname = ucompanyid;
				sql = "SELECT * FROM mstr_company where companyid=?";
				params = new Object[1];
				params[0] = ucompanyid;
				List<Object> result3 = JdbcUtil.getInstance().excuteQuery(sql, params);
				for (Object data : result3) {
					Map<String, Object> row = (Map<String, Object>) data;
					companyname = row.get("companynm").toString();
					break;
				}
				
				final String companynm = companyname;
				for (int i = 0; i < infolist.size(); i++) {
					final String openid = infolist.get(i);
					
					if(openid == null || openid.length() < 10){
						continue;
					}
					final String username = userinfo.getUserName();
					Thread t2 = new Thread(new Runnable() {
						public void run() {
							sendTemplateMessage(openid, WechatConsts.templetid02, companynm, username);
						}
					});
					t2.start();
				}
			}
		}
		
		init(request, response);
	}
	
	public static String sendTemplateMessage(String touser, String template_id, String companynm, String username) {
		String msg = "--Begin set accesstoken--<br>";
		String token = AccessTokenGeter.getStrAccessToken();
		String sendUrl = URLProducer.GetTemplateSendUrl(token);
		 msg = msg+ "--url" + sendUrl +"<br>";
		// post请求数据
		String url = "http://www.freertokyo.com/ework/personal.do";
		// data
		JSONObject dataJson = new JSONObject();
		// first
		JSONObject fstJson = new JSONObject();
		fstJson.put("value", "用户申请加入贵公司，请尽快审批。");
		// keyword1
		JSONObject k1Json = new JSONObject();
		k1Json.put("value", companynm);
		// keyword2
		JSONObject k2Json = new JSONObject();
		k2Json.put("value", username);
		// remark
		JSONObject rmkJson = new JSONObject();
		rmkJson.put("value", "点击快速进行审批, GO>>");
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
			e.printStackTrace();
			msg = msg + "<br>" + "--Failed send temp message" + e.getMessage() + "<br>";
		}
		
		return msg;
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String companyid = request.getParameter("companyid");
		if(companyid ==null){
			companyid = userinfo.getMaincompanyid();
		}
		String sql = "select * from mstr_company where companyid = ? and delflg =?";
		Object[] params = new Object[2];
		params[0] = companyid;
		params[1] = 0;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
		request.setAttribute("imgurl", "assets/images/companypic.jpg");
		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			
			request.setAttribute("companyid", row.get("companyid").toString());
			request.setAttribute("companynm", row.get("companynm").toString());
			request.setAttribute("companyexp", row.get("companyexp").toString());
			
			break;
		}
		
		String btnname = "申请加入";
		/*if(companyid.equals(userinfo.getMaincompanyid())){
			btnname = "永久退出";
		}*/
		request.setAttribute("btnname", btnname);
		sql = "select * from cdata_companyuser where companyid = ? and userid=? and delflg ='0' and rolekbn in ('0','1')";
		params = new Object[2];
		params[0] = companyid;
		params[1] = userinfo.getUserId();
		list1 = JdbcUtil.getInstance().excuteQuery(sql, params);

		String diplay = list1.size() > 0 ? "" : "display: none";
		request.setAttribute("display", diplay);
		
		String diplaybtn = Utils.isDefaultCompany(userinfo.getMaincompanyid())? "" : "display: none";
		request.setAttribute("displaybtn", diplaybtn);
		
		request.getRequestDispatcher("companydetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private List<String> getadmminusermailadd(String companyid) {
		// 管理员邮箱取得
		String sql = "select usr.mail as mail from cdata_companyuser com left join mstr_user usr on com.userid=usr.userid where com.companyid=? and com.rolekbn in ('0', '1') and com.delflg='0' and usr.mail is not null";
		Object[] params = new Object[1];
		params[0] = companyid;
		List<Object> userlist = JdbcUtil.getInstance().excuteQuery(sql, params);
		List<String> list = new ArrayList<String>();
		if (userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) userlist.get(i);
				if (set.get("mail") != null && set.get("mail").toString().length() > 0) {
					list.add(set.get("mail").toString());
				}
			}
		}

		return list;
	}
	
	private List<String> getadmminuseropenid(String companyid) {
		// 管理员邮箱取得
		String sql = "select usr.openid as openid from cdata_companyuser com left join mstr_user usr on com.userid=usr.userid where com.companyid=? and com.rolekbn in ('0', '1') and com.delflg='0' and usr.mail is not null";
		Object[] params = new Object[1];
		params[0] = companyid;
		List<Object> userlist = JdbcUtil.getInstance().excuteQuery(sql, params);
		List<String> list = new ArrayList<String>();
		if (userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) userlist.get(i);
				if (set.get("openid") != null && set.get("openid").toString().length() > 0) {
					list.add(set.get("openid").toString());
				}
			}
		}

		return list;
	}

}
