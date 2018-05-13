package com.bwc.ework.servlets;

import java.io.IOException;
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
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String notice = Utils.isDefaultCompany(userinfo.getMaincompanyid()) ? "温馨提示：新用户请先加入或开通公司，有任何问题都可在公众号留言" :"没有新通知";
		String event = "近期没活动";
		String sql = "select * from cdata_notice where companyid=? and type=? and delflg=? and createdate =(select max(createdate) from cdata_notice where companyid=? and type=? and delflg=? group by type ) limit 1";
		Object[] paramsnotice = new Object[6];
		paramsnotice[0] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		paramsnotice[1] = "1";  // notice kbn
		paramsnotice[2] = "0";
		paramsnotice[3] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		paramsnotice[4] = "1";  // notice kbn
		paramsnotice[5] = "0";
		List<Object> notices = JdbcUtil.getInstance().excuteQuery(sql, paramsnotice);
	   if(notices != null && notices.size() > 0){
		   String temp = (String)((Map<String, Object>)notices.get(0)).get("content");
		   notice = temp.length() > 0 ? temp : notice;
	   }
		
		Object[] paramsevent = new Object[6];
		paramsevent[0] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		paramsevent[1] = "2";  // event kbn
		paramsevent[2] = "0";
		paramsevent[3] = Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		paramsevent[4] = "2";  // event kbn
		paramsevent[5] = "0";
		List<Object> events = JdbcUtil.getInstance().excuteQuery(sql, paramsevent);
		if(events != null && events.size() > 0){
			String temp = (String)((Map<String, Object>)events.get(0)).get("content");
			event = temp.length() > 0 ? temp : event;
		   }
		request.setAttribute("notice", notice);
		request.setAttribute("event", event);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
