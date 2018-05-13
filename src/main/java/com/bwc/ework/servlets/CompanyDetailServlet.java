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
import com.bwc.ework.common.Utils;
import com.bwc.ework.common.mail.SendMailFactory;
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
		String sql = "SELECT * FROM ework.mstr_user_comp where userid=?";
		Object[] params = new Object[1];
		params[0] = userinfo.getUserId();
		List<Object> result = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		if(result.size() ==0){
			sql = "insert into cdata_userapply values(?,?,?)";
			params = new Object[3];
			params[0] = companyid;
			params[1] = userinfo.getUserId();
			params[2] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
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
		}
		
		init(request, response);
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

}
