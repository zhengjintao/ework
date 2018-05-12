package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
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
        // TODO Auto-generated constructor stub
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
		}
		
		init(request, response);
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String companyid = request.getParameter("companyid");
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
		
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
