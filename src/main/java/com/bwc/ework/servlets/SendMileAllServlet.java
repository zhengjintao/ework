package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import com.bwc.ework.common.Utils;
import com.bwc.ework.common.excel.CreateExcelForAllWork;
import com.bwc.ework.common.mail.SendMailFactory;

import com.bwc.ework.form.User;

/**
 * Servlet implementation class SendBillServlet
 */
public class SendMileAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMileAllServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String mailname = request.getParameter("mailname");
		HttpSession session = request.getSession();
		User userif = (User) session.getAttribute("userinfo");
		String companycd = Utils.getStoreCompanyid(userif.getMaincompanyid());
		
		// 根据模版生成出勤文件
		String outfilePath = null;
		try{
			outfilePath = CreateExcelForAllWork.readXLSFile(companycd,userif.getMaincompanyname(),request.getParameter("wdate"));
		}catch(Exception e){
			String message = "全员出勤统计文件生成失败";
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("message", message);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
			return;
		}
		
		
		List<String> list = new ArrayList<String>();
		list.add(mail);
		List<String> files = new ArrayList<String>();
		files.add(outfilePath);  // 文件路径，可多个
		final List<String> ulist = list;
		final String umailname = mailname + "(" + userif.getUserName() + request.getParameter("wdate2") + ")";
		final List<String> ufiles = files;
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					try {
						SendMailFactory.getInstance().getMailSender().sendMessage(ulist, umailname, umailname,ufiles);
					} catch (MessagingException e) {
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
		
		String message = "已做发送处理，请注意查收<br>（文件发送需耗时1分钟左右<br>有可能发送失败，3分钟还未收到时请重发）";
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", message);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
