package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class CompanySettingServlet
 */
public class CompanySettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanySettingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companynm = request.getParameter("companynm");
		if (companynm == null) {
			companynm = "";
		}
		request.setAttribute("companynm", companynm);
		
		StringBuilder info = new StringBuilder();
		
		if(companynm != null && companynm.length() > 0){
			List<String[]> scompniesinfo = getsearchcompniesinfo(companynm);
			
			for (String[] each : scompniesinfo) {
				info.append("<div class='ui inverted divider'></div>");
				info.append("<div class='ui middle aligned divided list'>");
				info.append("<div class='item'>");
				info.append("<img class='ui avatar image' src='assets/images/companypic.jpg'>");
				info.append("<div class='content'>");
				info.append("<a class='header' href='companydetail.do?companyid=" + each[0] +"'>" + each[1] +"</a>");
				info.append("</div>");
				info.append("</div>");
				info.append("</div>");
				info.append("<div class='ui inverted divider'></div>");
			}
			
			if(scompniesinfo.size() ==0){
				info.append("未搜索到相关公司，<a href='companyedit.do?companynm="+ companynm +"' style='color:red'>申请开通</a>");
			}
		}
		
		
		request.setAttribute("scompnies", info.toString());
		
		List<String[]> hotcompniesinfo = gethotcompniesinfo();
		info = new StringBuilder();
		for (String[] each : hotcompniesinfo) {
			info.append("<div class='item'>");
			info.append("<img class='ui avatar image' src='assets/images/companypic.jpg'>");
			info.append("<div class='content'>");
			info.append("<a class='header' href='companydetail.do?companyid=" + each[0] +"'>" + each[1] +"</a>");
			info.append("</div>");
			info.append("</div>");
		}
		
		request.setAttribute("hotcompnies", info.toString());
		request.getRequestDispatcher("companysetting.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<String[]> gethotcompniesinfo() {
		List<String[]> monthData = new ArrayList<String[]>();

		String sql = "select * from mstr_company where delflg =? limit 10";
		Object[] params = new Object[1];
		params[0] = 0;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);

		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("companyid").toString();
			each[1] = (String) row.get("companynm");
			monthData.add(each);
		}

		return monthData;
	}
    
    private List<String[]> getsearchcompniesinfo(String mapname) {
		List<String[]> monthData = new ArrayList<String[]>();

		String sql = "select * from mstr_company where delflg =? and companynm like ?";
		Object[] params = new Object[2];
		params[0] = 0;
		params[1] = "%" + mapname + "%";
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);

		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("companyid").toString();
			each[1] = (String) row.get("companynm");
			monthData.add(each);
		}

		return monthData;
	}

}
