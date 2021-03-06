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
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.bwc.ework.common.DateTimeUtil;
import com.bwc.ework.common.HashEncoder;
import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.common.Utils;
import com.bwc.ework.form.User;

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
		HttpSession session = request.getSession();
		// 用户信息
		User userinfo = (User)session.getAttribute("userinfo");
		String mail = userinfo.getMail();
		String companyCd =Utils.getStoreCompanyid(userinfo.getMaincompanyid());
		request.setAttribute("email", mail);

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
			request.setAttribute("info", getExpinfoByMonth(userid, companyCd, DateTimeUtil.GetMonth(now)));
			request.getRequestDispatcher("moneysubmit.jsp").forward(request, response);
		}else if(!"1".equals(request.getParameter("expinfo")) 
				&& "1".equals(request.getParameter("saveFlg"))
				&& !"1".equals(request.getParameter("delflg"))){
			com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(request.getParameter("subdate"));
			String year = date1.getYear();
			String month = date1.getMonth();
			String subKbn = new String(request.getParameter("subKbn"));
			String stationf = request.getParameter("stationf") != null ? 
					new String(request.getParameter("stationf")) :"";
			String stationt = request.getParameter("stationt") != null ? 
					new String(request.getParameter("stationt")) :"";		
			String notes = request.getParameter("notes") != null ? 
					new String(request.getParameter("notes")) :"";
			// 数据新规
			String insertsql = "insert into cdate_expenses values(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] insertparams = new Object[12];
			insertparams[0] = userid;
			insertparams[1] = companyCd;
			insertparams[2] = getDetailNo(userid);
			insertparams[3] = subKbn;
			insertparams[4] = request.getParameter("subdate");
			insertparams[5] = year;
			insertparams[6] = month;
			insertparams[7] = stationf;
			insertparams[8] = stationt;
			insertparams[9] = request.getParameter("money");
			insertparams[10] = notes;
			insertparams[11] = "0";
			JdbcUtil.getInstance().executeUpdate(insertsql, insertparams);
			
			request.setAttribute("userid", userid);
			request.setAttribute("nowmonth", DateTimeUtil.GetMonth(now));
			request.setAttribute("nowdate", now);
			request.setAttribute("sysDate2", DateTimeUtil.GetMonth(now));
			request.setAttribute("info", getExpinfoByMonth(userid, companyCd, DateTimeUtil.GetMonth(now)));
			request.getRequestDispatcher("moneysubmit.jsp").forward(request, response);
		}
		else if("1".equals(request.getParameter("expinfo")) && !"1".equals(request.getParameter("delflg"))){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", getExpinfoByMonth(userid, companyCd, request.getParameter("wdate2")));
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(jsonObject.toString());
		}
		else if("1".equals(request.getParameter("delflg"))){
			delete(request.getParameter("dtlno"));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info", getExpinfoByMonth(userid, companyCd, request.getParameter("wdate2")));
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
	
	private String getExpinfoByMonth(String userid,String companyid, String monthDate){
		com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(monthDate);
		String year = date1.getYear();
		String month = date1.getMonth();
		String sql2 = "SELECT * FROM cdate_expenses where userid=? and companyid=? and year=? and month=? and delflg = ? order by expdetailno;";
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
		
		StringBuilder info = new StringBuilder();
		if (monthinfo.size() == 0) {
			info.append("<tr>");
			info.append("<td>");
			info.append("当月没有报销");
			info.append("</td>");
			info.append("</tr>");
		}else{
			info.append("<tr bgcolor='#00B5AB'>");
			info.append("<th style='text-align:center; color:white; width:20%'>日期</th>");
			info.append("<th style='text-align:center; color:white; width:30%'>区分</th>");
			//info.append("<th style='text-align:center; color:white; width:50%'>区间</th>");
			info.append("<th style='text-align:center; color:white; width:30%'>金额</th>");
			//info.append("<th style='text-align:center; color:white; width:20%'>备注</th>");
			info.append("<th style='text-align:center; color:white; width:10%'></th>");
			info.append("</tr>");
		}
		for (String[] each : monthinfo) {
			info.append("<tr>");
			info.append("<td style='text-align:center'>");
			info.append(each[0].substring(5, 10));
			info.append("</td>");
			info.append("<td style='text-align:center'>");
			info.append(each[1]);
			info.append("</td>");
			/*info.append("<td>");
			info.append(each[2] + " - " + each[3]);
			info.append("</td>");*/
			info.append("<td style='text-align:right'>");
			info.append(each[4]);
			info.append("</td>");
			/*info.append("<td>");
			info.append(each[5]);
			info.append("</td>");*/
			info.append("<td>");
			info.append("<i class='close icon' onclick='ondelete(" + each[6] + ");'></i>");
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
