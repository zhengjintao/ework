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
 * Servlet implementation class EmployeeManageServlet
 */
public class EmployeeManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeManageServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			
		}
		else{
			this.init(request, response);
		}
		
	}
	
	private void apply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userid = request.getParameter("userid");
		String companyid = request.getParameter("companyid");
		
		String sql = "update cdata_userapply set status='1' where companyid=? and userid=?";
		Object[] params = new Object[2];
		params[0] = companyid;
		params[1] = userid;
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
		sql = "insert into mstr_user_comp values(?,?,?,?)";
		params = new Object[4];
		params[0] = userid;
		params[1] = companyid;
		params[2] = "1";
		params[3] = "0";
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
		sql = "insert into cdata_companyuser values(?,?,?,?)";
		params = new Object[4];
		params[0] = companyid;
		params[1] = userid;
		params[2] = "1";
		params[3] = "0";
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
	}
	
    private void refuse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String companyid = request.getParameter("companyid");
		String sql = "select * from cdata_userapply where companyid=? and status='0'";
		Object[] params = new Object[1];
		params[0] = companyid;
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);

		JSONArray onsalearray = new JSONArray();
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		int i = 0;
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", row.get("companyid"));
			jsonObject.put("userid", row.get("userid"));
			jsonObject.put("username", row.get("userid"));
			jsonObject.put("text", row.get("userid"));
			jsonObject.put("img", "assets/images/companypic.jpg");
			jsonObject.put("reason", row.get("userid"));
			list.add(jsonObject);

			onsalearray.put(i, jsonObject);
			i++;
		}
		
		JSONArray unsalearray = new JSONArray();
		sql = "select * from cdata_companyuser where companyid=? and rolekbn!='0' and delflg='0'";
		params = new Object[1];
		params[0] = companyid;
		userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);		
		i = 0;
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", row.get("userid"));
			jsonObject.put("userid", row.get("userid"));
			jsonObject.put("username", row.get("userid"));
			jsonObject.put("text", row.get("userid"));
			jsonObject.put("img", "assets/images/companypic.jpg");
			jsonObject.put("reason", row.get("userid"));

			unsalearray.put(i, jsonObject);
			i++;
		}
		

		// 最終結果
		JSONObject result = new JSONObject();
		result.put("onsalegoods", onsalearray);
		result.put("unsalegoods", unsalearray);

		response.getWriter().write(result.toString());
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String companyid = request.getParameter("companyid");
		
		JSONObject result = new JSONObject();
		result.put("companyid", companyid);
		
		request.setAttribute("initdata", result);
		request.getRequestDispatcher("employeemanage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
