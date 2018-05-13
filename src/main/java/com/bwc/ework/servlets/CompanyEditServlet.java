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
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.StringUtil;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class CompanyEditServlet
 */
public class CompanyEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyEditServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String companyid = request.getParameter("companyid");
		if (companyid == null) {
			companyid = "";
		}
		String companynm = request.getParameter("companynm");
		if (companynm == null) {
			companynm = "";
		}
		String btnname = "申请开通";
		String companyexp = request.getParameter("companyexp");
		if (companyexp == null) {
			companyexp = "";
		}
		String reason = request.getParameter("reason");
		if (reason == null) {
			reason = "";
		}
		String mode = request.getParameter("mode");
		
		if("1".equals(mode)){
			if(companyid == null || companyid.length()==0){
				Object[] params = new Object[1];
				params[0] = userinfo.getUserId();
				String selsql = "SELECT * FROM cdata_companyapply where userid=?";
				List<Object> result = JdbcUtil.getInstance().excuteQuery(selsql, params);
				
				for (Object data : result) {
					Map<String, Object> row = (Map<String, Object>) data;
					
					companyid = row.get("companyid").toString();
					companynm = row.get("companynm").toString();
					companyexp = row.get("companyexp").toString();
					reason = row.get("reason").toString();
					
					break;
				}
			}
			if(companyid!=null){
				btnname="已申请";
				request.setAttribute("errmsg", "申请失败，每人最多只允许申请创建一个公司<br>"  + "已申请下列公司等待审核中"+ "<br>" + "编号：" + companyid+ "<br>" + "名称：" + companynm+ "<br>"+"（最快5分钟内审核，最长24小时内审核）");
			}else if(companyid.length()>0 ){
				btnname="已申请";
				request.setAttribute("errmsg", "申请失败，每人最多只允许申请创建一个公司<br>"  + "已成功申请下列公司"+ "<br>" + "编号：" + companyid+ "<br>" + "名称：" + companynm+ "<br>"+"（返回个人页面查看）");
			}
		}
		String maxnum ="0";
		if("1".equals(mode) && (companyid==null || companyid.length()==0)){
			String selsql = "SELECT max(companyid) as maxnum FROM cdata_companyapply";
			List<Object> result = JdbcUtil.getInstance().excuteQuery(selsql, null);
			
			for (Object data : result) {
				Map<String, Object> row = (Map<String, Object>) data;
				
				if(row.get("maxnum") != null){
					maxnum = row.get("maxnum").toString();
				}
				
				break;
			}
			
			Integer intmax = new Integer(maxnum);
			intmax++;
			companyid = StringUtil.padLeft(intmax.toString(), 10, '0');
			
			String insertSql = "insert into cdata_companyapply value(?,?,?,?,?,?)";
			Object[] insertparams = new Object[6];
			insertparams[0] = companyid;
			insertparams[1] = userinfo.getUserId();
			insertparams[2] = companynm;
			insertparams[3] = companyexp;
			insertparams[4] = reason;
			insertparams[5] = "0";
			
			JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
			
			btnname="已申请";
			
			request.setAttribute("errmsg", "申请成功，等待审核"+ "<br>"+"（最快5分钟内审核，最长24小时内审核）");
			
			String text = "公司名称：" + companynm + "<br>";
			text = text + "申请人：" + userinfo.getUserName() + "<br>";
			text = text + "＃请尽快进入系统进行审批，相关模块路径［个人］－［公司审批］" + "<br>";
			
			final String utext = text;
			
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							SendMailFactory.getInstance().getMailSender().sendMessage(getadmminusermailadd(),"新公司入驻申请提醒", utext, null);
						} catch (MessagingException e) {
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		if(mode==null){
			mode ="1";
		}
		
		request.setAttribute("btnname", btnname);
		request.setAttribute("mode", mode);
		
		request.setAttribute("companyid", companyid);
		request.setAttribute("companynm", companynm);
		request.setAttribute("companyexp", companyexp);
		request.setAttribute("reason", reason);
		
		request.getRequestDispatcher("companyedit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<String> getadmminusermailadd() {
		// 管理员邮箱取得
		String sql = "select mail from mstr_user where (authflg='0' or authflg=?) and delflg='0' and mail is not null";
		Object[] params = new Object[1];
		params[0] = "1";
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

}
