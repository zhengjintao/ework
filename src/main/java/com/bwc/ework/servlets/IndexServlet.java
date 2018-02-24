package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String notice = "最近没啥事，各自安好！";
		String event = "近期没活动，自己high，自己浪！";
		
		String sql = "select * from cdata_notice where type=? and delflg=? and createdate =(select max(createdate) from cdata_notice where type=? and delflg=? group by type ) limit 1";
		Object[] paramsnotice = new Object[4];
		paramsnotice[0] = "1";  // notice kbn
		paramsnotice[1] = "0";
		paramsnotice[2] = "1";  // notice kbn
		paramsnotice[3] = "0";
		List<Object> notices = JdbcUtil.getInstance().excuteQuery(sql, paramsnotice);
	   if(notices != null && notices.size() > 0){
		   String temp = (String)((Map<String, Object>)notices.get(0)).get("content");
		   notice = temp.length() > 0 ? temp : notice;
	   }
		
		Object[] paramsevent = new Object[4];
		paramsevent[0] = "2";  // event kbn
		paramsevent[1] = "0";
		paramsevent[2] = "2";  // event kbn
		paramsevent[3] = "0";
		List<Object> events = JdbcUtil.getInstance().excuteQuery(sql, paramsevent);
		if(events != null && events.size() > 0){
			String temp = (String)((Map<String, Object>)events.get(0)).get("content");
			event = temp.length() > 0 ? temp : event;
		   }
		request.setAttribute("notice", notice);
		request.setAttribute("event", event);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
