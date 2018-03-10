package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.form.User;

/**
 * Servlet implementation class leaveServlte
 */
public class LeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<String[]> getMonthData(String userId, String dateStr) {
		List<String[]> monthData = new ArrayList<String[]>();
		if (dateStr == null) {
			return monthData;
		}
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(dateStr);
		String year = date1.getYear();
		String month = date1.getMonth();

		String sql = "select * from cdata_leave where userid=? and year = ? and month = ?";
		Object[] params = new Object[3];
		params[0] = userId;
		params[1] = year;
		params[2] = month;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);

		for (Object data : list1) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[2];
			each[0] = row.get("leavedate").toString();
			each[1] = (String) row.get("content");
			monthData.add(each);
		}

		return monthData;
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User userinfo = (User) session.getAttribute("userinfo");

		String sql = "delete from cdata_leave where userid=? and leavedate=?";
		Object[] params = new Object[2];
		params[0] = userinfo.getUserId();
		params[1] = request.getParameter("wdate");
		JdbcUtil.getInstance().executeUpdate(sql, params);
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 请假的开始日期
		String wdate= request.getParameter("wdate");
		// 用户信息
		User userinfo = (User) session.getAttribute("userinfo");
		// 请假时间（复数可）
		List<String> wdateList = new ArrayList();
		if(wdate!= null){
			wdateList =  Arrays.asList(wdate.split(","));
		}

		// 画面查询时间
		String wdate2 = request.getParameter("wdate2");
		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());
		if (wdateList.size() == 0) {
			wdateList.add(now);
		}
		
		if(wdate2 == null || wdate2.length() == 0){
			wdate = now;
			wdate2 = DateTimeUtil.GetMonth(now);
		}

		// 请假理由转码
		String wcomment = request.getParameter("wcomment");
		if (wcomment != null) {
			wcomment = new String(wcomment.getBytes("iso-8859-1"), "utf-8");
		}else{
			wcomment ="";
		}

		String subkbn = request.getParameter("subKbn");

		// IN条件作成
        StringBuffer buffer = new StringBuffer();  
        for (int i = 0; i < wdateList.size(); i++)  
        {  
            buffer.append("?, ");  
        }  
        buffer.deleteCharAt(buffer.length() - 1);  
        buffer.deleteCharAt(buffer.length() - 1); 
        
        
		Object[] params = new Object[wdateList.size()+1];
		params[0] = userinfo.getUserId();
        for (int i = 0; i < wdateList.size(); i++)  
        {  
        	params[i+1] = wdateList.get(i); 
        }  
        
		// 初期化
		if (subkbn == null) {
			wdate2 = DateTimeUtil.GetMonth(now);
		} else {

			// 删除
			if ("1".equals(request.getParameter("deleteFlg"))) {
				delete(request, response);
			}

			// 初期化的场合
			if ("true".equals(request.getParameter("subKbn")) && !"1".equals(request.getParameter("leaveinfo"))
					&& !"1".equals(request.getParameter("deleteFlg"))) {
				String sql = "select * from cdata_worktime where userid=? and date in ("+ buffer.toString() +")";

				List<Object> listw = JdbcUtil.getInstance().excuteQuery(sql, params);
				if (listw.size() > 0) {
					StringBuilder dayinfo = new StringBuilder();
					for (Object data : listw) {
						Map<String, Object> row = (Map<String, Object>) data;
						dayinfo.append(String.valueOf(row.get("date")) + "<br>");
					}
					request.setAttribute("errmsg", "下面日期已签到，无法请假。<br>" + dayinfo);
				} else {
					// 删除
					String deleteSql = "delete from cdata_leave where userid=? and leavedate in ("+ buffer.toString() +")";
					Object[] delparams = new Object[2];
					delparams[0] = userinfo.getUserId();
					delparams[1] = wdate;
					JdbcUtil.getInstance().executeUpdate(deleteSql, params);

					// 数据存在更新操作
					String insertSql = "insert into cdata_leave value(?,?,?,?,?)";
					Object[] insertparams = new Object[5];
					String userid = userinfo.getUserId();
					for(int i=0;i<wdateList.size();i++){
						com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(wdateList.get(i));			
						insertparams[0] = userid;
						insertparams[1] = wdateList.get(i);
						insertparams[2] = date1.getYear();
						insertparams[3] = date1.getMonth();
						insertparams[4] = wcomment == null || wcomment.length() == 0 ? "未填写理由" : wcomment;
						JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
					}
				}

				wdate2 = DateTimeUtil.GetMonth(wdateList.get(0));
			}
		}

		// 日期设定
		request.setAttribute("sysDate", wdateList.get(0));
		// 日期设定
		request.setAttribute("sysDate2", wdate2);
		request.setAttribute("wcomment", wcomment);

		String sql = "select * from cdata_leave where userid=? and leavedate in ("+ buffer.toString() +")";
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		List<String[]> monthinfo = getMonthData(userinfo.getUserId(), wdate2);
		StringBuilder info= new StringBuilder();
		if(monthinfo.size() == 0){
			info.append("<tr>");
			info.append("<td>");
			info.append("当月没有请假");
			info.append("</td>");
			info.append("</tr>");
		}
		for(String[] each : monthinfo){
			info.append("<tr>");
			info.append("<td style='width:40%'>");
			info.append(each[0]);
			info.append("</td>");
			info.append("<td style='width:60%'> ");
			info.append(each[1]);
			info.append("</td>");
			info.append("<td style='width:6%'>");
			info.append("<i class='window close outline icon' onclick='ondelete("+ each[0].replace("-", "") +");'></i>");
			info.append("</td>");
			info.append("</tr>");
		}
		if ("1".equals(request.getParameter("leaveinfo")) || "1".equals(request.getParameter("deleteFlg"))) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", info.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());

			return;
		}

		request.setAttribute("info", info.toString());
		RequestDispatcher re = request.getRequestDispatcher("leave.jsp");
		re.forward(request, response);
	}

}
