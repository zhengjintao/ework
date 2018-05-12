package com.bwc.ework.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bwc.ework.common.JdbcUtil;
import com.bwc.ework.form.User;

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
		JSONArray unsalearray = new JSONArray();
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

		// 最終結果
		JSONObject result = new JSONObject();
		result.put("onsalegoods", onsalearray);
		result.put("unsalegoods", unsalearray);

		response.getWriter().write(result.toString());
	}
	
	private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("userinfo");
		
		String sql = "select * from cdata_companyuser c left join mstr_user u on c.userid= u.userid where c.companyid=? and c.delflg=? and c.rolekbn !=?";
		Object[] params = new Object[3];
		params[0] = user.getMaincompanyid();
		params[1] = "0";
		params[2] = "1";
		List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql, params);
		
		List<String[]> userinfolist = new ArrayList<String[]>();
		for (Object data : userinfo) {
			Map<String, Object> row = (Map<String, Object>) data;
			String[] each = new String[6];
			each[0] = row.get("userid").toString();
			each[1] = row.get("username").toString();
			each[2] = row.get("password").toString();
			each[3] = row.get("sex").toString();
			each[4] = row.get("authflg").toString();
			each[5] = "F".equals(each[3]) ? "assets/images/rachel.png" : "assets/images/christian.jpg";
			
			userinfolist.add(each);
		}
		
		request.setAttribute("userinfo", userinfolist);
		request.getRequestDispatcher("employeemanage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
