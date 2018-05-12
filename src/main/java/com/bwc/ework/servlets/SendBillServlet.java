package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.excel.ReadWriteExcelFile;
import com.bwc.ework.common.mail.SendMailFactory;
import com.bwc.ework.form.BillDetail;
import com.bwc.ework.form.BillInfo;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class SendBillServlet
 */
public class SendBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendBillServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String mailname = request.getParameter("mailname");
		String userid = request.getParameter("userid");
		HttpSession session = request.getSession();
		User userif = (User) session.getAttribute("userinfo");
		BillDetail bd = new BillDetail();
		bd.setName(userif.getUserName());
		Date[] dates = this.queryDays(request.getParameter("wdate2"));
		bd.setBegindate(dates[0]);
		bd.setEnddate(dates[1]);
		
		List<String[]> info = this.getExpinfoByMonth(userid, userif.getMaincompanyid(), request.getParameter("wdate2"));
		List<BillInfo> bilist = new ArrayList<BillInfo>();
		for(String[] each : info){
			BillInfo bi = new BillInfo();
			bi.setDate(each[0]);
			bi.setFrom(each[2]);
			bi.setTo(each[3]);
			bi.setMoney(each[4]);
			bi.setComment(each[5]);
			bilist.add(bi);
			bd.setBilist(bilist);
		}
		
		// 根据模版生成交通费精算文件
		String outfilePath = null;
		try{
			outfilePath = ReadWriteExcelFile.readXLSFile(bd);
		}catch(Exception e){
			String message = "交通费精算文件生成失败";
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
	
	private List<String[]> getExpinfoByMonth(String userid, String companyid, String monthDate){
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(monthDate);
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql2 = "SELECT * FROM cdate_expenses where userid=? and companyid=? and year=? and month=? and expkbn='交通费' and delflg = ? order by expdetailno;";
		Object[] params2 = new Object[5];
		params2[0] = userid;
		params2[1] = companyid;
		params2[2] = year;
		params2[3] = month;
		params2[4] = "0";
		List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
		List<String[]> monthinfo = new ArrayList<String[]>();
		for (int i = 0; i < infolist.size(); i++) {
			Map<String, Object> set = (Map<String, Object>) infolist.get(i);
			String[] each = new String[7];
			each[0] = String.valueOf(set.get("expdate"));
			each[1] = String.valueOf(set.get("expkbn"));
			each[2] = String.valueOf(set.get("stationf"));
			each[3] = String.valueOf(set.get("stationt"));
			each[4] = String.valueOf(set.get("money"));
			each[5] = String.valueOf(set.get("notes"));
			each[6] = String.valueOf(set.get("expdetailno"));
			monthinfo.add(each);
		}
		
		return monthinfo;
	}
	
	public Date[] queryDays(String datestr){  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String beginStr = datestr+"-01";  
        Date[] ds = new Date[2];
        try {  
        	ds[0] = dateFormat.parse(beginStr);  
              
            Calendar calendar = Calendar.getInstance();  
            calendar.setTime(dateFormat.parse(beginStr));  
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
            ds[1] = calendar.getTime();  
              
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        
        return ds;
    }  
}
