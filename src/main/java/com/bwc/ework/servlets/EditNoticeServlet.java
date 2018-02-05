package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bwc.ework.common.JdbcUtil;
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
		if(subKbn != null && subKbn.length() > 0){
			String sqlcount = "select count(*) as count from cdata_notice";
			Long count = (Long)JdbcUtil.getInstance().executeQuerySingle(sqlcount, null);
			count++;
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "utf-8");
			String typekbn = "notice".equals(subKbn) ? "1" : "2";
			
			HttpSession session = request.getSession();
			User userinfo = (User)session.getAttribute("userinfo");
			
			SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
			request.getRequestDispatcher("index.do").forward(request, response);
		}else{
			String type = "1".equals(request.getParameter("type")) ? "notice" : "event";
			String title = "1".equals(request.getParameter("type")) ? "通知" : "活动";
			request.setAttribute("type", type);
			request.setAttribute("title", title);
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

}
