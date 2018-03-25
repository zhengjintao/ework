package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class UserEditServlet
 */
public class MoneySubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoneySubmitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");

		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());
		
		//初期化
		if(!"1".equals(request.getParameter("expinfo")) 
		  && !"1".equals(request.getParameter("saveFlg"))
		  && !"1".equals(request.getParameter("delflg"))){
			request.setAttribute("userid", userid);
			request.setAttribute("nowmonth", DateTimeUtil.GetMonth(now));
			request.setAttribute("nowdate", now);
			request.setAttribute("sysDate2", DateTimeUtil.GetMonth(now));
			request.setAttribute("info", getExpinfoByMonth(userid,DateTimeUtil.GetMonth(now)));
			request.getRequestDispatcher("moneysubmit.jsp").forward(request, response);
		}else if(!"1".equals(request.getParameter("expinfo")) 
				&& "1".equals(request.getParameter("saveFlg"))
				&& !"1".equals(request.getParameter("delflg"))){
			com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(request.getParameter("subdate"));
			String year = date1.getYear();
			String month = date1.getMonth();
			String subKbn = new String(request.getParameter("subKbn").getBytes("iso-8859-1"), "utf-8");
			String station = request.getParameter("station") != null ? 
					new String(request.getParameter("station").getBytes("iso-8859-1"), "utf-8") :"";
			String notes = request.getParameter("notes") != null ? 
					new String(request.getParameter("notes").getBytes("iso-8859-1"), "utf-8") :"";
			// 数据新规
			String insertsql = "insert into cdate_expenses values(?,?,?,?,?,?,?,?,?,?)";
			Object[] insertparams = new Object[10];
			insertparams[0] = userid;
			insertparams[1] = getDetailNo(userid);
			insertparams[2] = subKbn;
			insertparams[3] = request.getParameter("subdate");
			insertparams[4] = year;
			insertparams[5] = month;
			insertparams[6] = station;
			insertparams[7] = request.getParameter("money");
			insertparams[8] = notes;
			insertparams[9] = "0";
			JdbcUtil.getInstance().executeUpdate(insertsql, insertparams);
			
			request.setAttribute("userid", userid);
			request.setAttribute("nowmonth", DateTimeUtil.GetMonth(now));
			request.setAttribute("nowdate", now);
			request.setAttribute("sysDate2", DateTimeUtil.GetMonth(now));
			request.setAttribute("info", getExpinfoByMonth(userid,DateTimeUtil.GetMonth(now)));
			request.getRequestDispatcher("moneysubmit.jsp").forward(request, response);
		}
		else if("1".equals(request.getParameter("expinfo")) && !"1".equals(request.getParameter("delflg"))){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", getExpinfoByMonth(userid,request.getParameter("wdate2")));
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
		}
		else if("1".equals(request.getParameter("delflg"))){
			delete(request.getParameter("dtlno"));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", getExpinfoByMonth(userid,request.getParameter("wdate2")));
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
		}
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getExpinfoByMonth(String userid,String monthDate){
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(monthDate);
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql2 = "SELECT * FROM cdate_expenses where userid=? and year=? and month=? and delflg = ? order by expdetailno;";
		Object[] params2 = new Object[4];
		params2[0] = userid;
		params2[1] = year;
		params2[2] = month;
		params2[3] = "0";
		List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
		List<String[]> monthinfo = new ArrayList<String[]>();
		for (int i = 0; i < infolist.size(); i++) {
			Map<String, Object> set = (Map<String, Object>) infolist.get(i);
			String[] each = new String[6];
			each[0] = String.valueOf(set.get("expdate"));
			each[1] = String.valueOf(set.get("expkbn"));
			each[2] = String.valueOf(set.get("station"));
			each[3] = String.valueOf(set.get("money"));
			each[4] = String.valueOf(set.get("notes"));
			each[5] = String.valueOf(set.get("expdetailno"));
			monthinfo.add(each);
		}
		
		StringBuilder info = new StringBuilder();
		if (monthinfo.size() == 0) {
			info.append("<tr>");
			info.append("<td>");
			info.append("当月没有报销");
			info.append("</td>");
			info.append("</tr>");
		}
		for (String[] each : monthinfo) {
			info.append("<tr>");
			info.append("<td  style='width:20%'>");
			info.append(each[0]);
			info.append("</td>");
			info.append("<td >");
			info.append(each[1]);
			info.append("<td>");
			info.append(each[2]);
			info.append("</td>");
			info.append("</td>");
			info.append("<td> ");
			info.append(each[3]);
			info.append("</td>");
			info.append("<td>");
			info.append(each[4]);
			info.append("</td>");
			info.append("<td>");
			info.append("<i class='close icon' onclick='ondelete(" + each[5] + ");'></i>");
			info.append("</td>");
			info.append("</tr>");
		}
		
		return info.toString();
	}
	
	//经费报销用户对应最大明细番号取得
	private int getDetailNo(String userid){
		int detailNo=0;
		
		String sql2 = "SELECT max(expdetailno) as expdetailno FROM cdate_expenses where userid=?;";
		Object[] params2 = new Object[1];
		params2[0] = userid;
		List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
		Map<String, Object> set = (Map<String, Object>) infolist.get(0);
		String maxno=  String.valueOf(set.get("expdetailno"));
		detailNo = maxno == "null" ? 0: Integer.valueOf(maxno)+1;
		return detailNo;
	}
	
	private void delete(String dtlno){
		String sql2 = "delete from cdate_expenses where expdetailno=?;";
		Object[] params2 = new Object[1];
		params2[0] = dtlno;
		JdbcUtil.getInstance().executeUpdate(sql2, params2);
	}
}
