package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userinfo = (User) session.getAttribute("userinfo");
		String wdate = request.getParameter("wdate");
		String wdate2 = request.getParameter("wdate2");
		String wcomment = request.getParameter("wcomment");
		if(wcomment != null){
			wcomment = new String(wcomment.getBytes("iso-8859-1"), "utf-8");
		}
		
		String subkbn = request.getParameter("subKbn");

		// 系统当前时间取得
		SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());

		// 初期化
		if (subkbn == null) {
			wdate = now;
			wdate2 = wdate;
		} else {

			// 删除
			if ("1".equals(request.getParameter("deleteFlg"))) {
				delete(request, response);
			}

			// 初期化的场合
			if ("true".equals(request.getParameter("subKbn")) && !"1".equals(request.getParameter("selectChg")) && !"1".equals(request.getParameter("deleteFlg"))) {
				String sql = "select * from cdata_leave where userid=? and leavedate=?";
				Object[] params = new Object[2];
				params[0] = userinfo.getUserId();
				params[1] = request.getParameter("wdate");
				List<Object> list = JdbcUtil.getInstance().excuteQuery(sql, params);

				com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(request.getParameter("wdate").toString());
				String userid = userinfo.getUserId();
				String year = date1.getYear();
				String month = date1.getMonth();
				String date = request.getParameter("wdate");

				// 数据存在更新操作
				if (list.size() > 0) {
					String updateSql = "update cdata_leave set year=?,month=?,content=?"
							+ " where userid=? and leavedate=?";
					Object[] updateparams = new Object[5];
					updateparams[0] = year;
					updateparams[1] = month;
					updateparams[2] = wcomment == null || wcomment.length() == 0 ? "未填写理由" : wcomment;
					updateparams[3] = userid;
					updateparams[4] = date;
					JdbcUtil.getInstance().executeUpdate(updateSql, updateparams);
				} else {
					String insertSql = "insert into cdata_leave value(?,?,?,?,?)";
					Object[] insertparams = new Object[5];
					insertparams[0] = userid;
					insertparams[1] = date;
					insertparams[2] = year;
					insertparams[3] = month;
					insertparams[4] = wcomment == null || wcomment.length() == 0 ? "未填写理由" : wcomment;
					JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
				}

				wdate2 = wdate;
			}
		}

		// 日期设定
		request.setAttribute("sysDate", wdate);
		// 日期设定
		request.setAttribute("sysDate2", wdate2);
		
		String sql = "select * from cdata_leave where userid=? and leavedate=?";
		Object[] params = new Object[2];
		params[0] = userinfo.getUserId();
		params[1] = wdate;
		List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
		if (list1.size() > 0) {
			Map<String, Object> set = (Map<String, Object>) list1.get(0);
			request.setAttribute("wcomment", set.get("content").toString());
		} else {
			request.setAttribute("wcomment", "");
		}
		request.setAttribute("monthdata", getMonthData(userinfo.getUserId(), wdate2));
		RequestDispatcher re = request.getRequestDispatcher("leave.jsp");
		re.forward(request, response);
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
}
