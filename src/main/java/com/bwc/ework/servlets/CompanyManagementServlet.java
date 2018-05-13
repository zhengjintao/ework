package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bwc.ework.common.JdbcUtil;

/**
 * Servlet implementation class CompanyManagementServlet
 */
public class CompanyManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyManagementServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("list".equals(mode)){
			this.list(request, response);
		}else if("apply".equals(mode)){
			this.apply(request, response);
		}else if("refuse".equals(mode)){
			this.refuse(request, response);
		}
		else{
			this.init(request, response);
		}
		
	}
	
	private void refuse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		String sql = "update cdata_companyapply set status='2' where companyid=?";
		Object[] params = new Object[1];
		params[0] = id;
		JdbcUtil.getInstance().executeUpdate(sql, params);
	}
	
	private void apply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		String sql = "select * from cdata_companyapply where companyid=?";
		Object[] params = new Object[1];
		params[0] = id;
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			
			sql = "update cdata_companyapply set status='1' where companyid=?";
			params = new Object[1];
			params[0] = id;
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			sql = "insert into mstr_company values(?,?,?,?,?,?,?,?)";
			params = new Object[8];
			params[0] = row.get("companyid");
			params[1] = row.get("companynm");
			params[2] = row.get("companynm");
			params[3] = row.get("companynm");
			params[4] = "1";
			params[5] = "";
			params[6] = "";
			params[7] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			sql = "insert into mstr_user_comp values(?,?,?,?)";
			params = new Object[4];
			params[0] = row.get("userid");
			params[1] = row.get("companyid");
			params[2] = "2";
			params[3] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			
			sql = "insert into cdata_companyuser values(?,?,?,?)";
			params = new Object[4];
			params[0] = row.get("companyid");
			params[1] = row.get("userid");
			params[2] = "0";
			params[3] = "0";
			JdbcUtil.getInstance().executeUpdate(sql, params);
			break;
		}
		
		list(request, response);
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("companymanagement.jsp").forward(request, response);
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String sql = "select * from cdata_companyapply com left join mstr_user usr on com.userid=usr.userid where com.status='0'";
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, null);

		JSONArray onsalearray = new JSONArray();
		JSONArray unsalearray = new JSONArray();
		List<JSONObject> list = new ArrayList<JSONObject>();
		int i = 0;
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", row.get("companyid"));
			jsonObject.put("username", row.get("username"));
			jsonObject.put("text", row.get("companynm"));
			jsonObject.put("img", "assets/images/companypic.jpg");
			jsonObject.put("reason", row.get("reason"));
			list.add(jsonObject);

			onsalearray.put(i, jsonObject);
			i++;
		}

		// 最終結果
		JSONObject result = new JSONObject();
		result.put("onsalegoods", onsalearray);
		result.put("unsalegoods", unsalearray);

		response.getWriter().write(result.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
