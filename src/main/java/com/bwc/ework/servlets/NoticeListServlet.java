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

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class NoticeListServlet
 */
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userif = (User) session.getAttribute("userinfo");
		String type = request.getParameter("type");
		String sql = "select * from cdata_notice where companyid=? and delflg=? and type =? order by createdate desc";
		Object[] params = new Object[3];
		params[0] = Utils.getStoreCompanyid(userif.getMaincompanyid());
		params[1] = "0";
		params[2] = type;
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		List<String[]> userinfolist = new ArrayList<String[]>();
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[6];
			each[0] = row.get("type").toString();
			each[1] = formateContent(row.get("content").toString());
			each[2] = row.get("content").toString();
			each[3] = row.get("type").toString();
			each[4] = row.get("type").toString();
			each[5] = row.get("createdate").toString();
			
			userinfolist.add(each);
		}
		
		request.setAttribute("name", "1".equals(type) ? "通知" : "活动");
		request.setAttribute("userinfo", userinfolist);
		request.getRequestDispatcher("noticelist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String formateContent(String content){
		if(content != null && content.length() > 10){
			content = content.trim().replace("　", "").substring(0, 9);
		}
		
		return content + "...";
	}

}
