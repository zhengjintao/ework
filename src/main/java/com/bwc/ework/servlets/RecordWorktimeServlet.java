package com.bwc.ework.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Servlet implementation class RecordWorktimeServlet
 */
public class RecordWorktimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordWorktimeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramwdate =request.getParameter("wdate");
		String comment =request.getParameter("wcomment");
		String latitude =request.getParameter("latitude");
		String longitude =request.getParameter("latitude");
		String dtladdress =request.getParameter("dtladdress");
		
		if (comment != null) {
			comment = new String(comment.getBytes("iso-8859-1"), "utf-8");
		}else{
			comment = "";
		}
		
		if (dtladdress != null) {
			dtladdress = new String(dtladdress.getBytes("iso-8859-1"), "utf-8");
		}else{
			dtladdress = "";
		}
		
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		request.setAttribute("beginlbl", "出勤");
		request.setAttribute("endlbl", "退勤");
		if("1".equals(request.getParameter("deleteFlg"))){
			delete(request,response);
			return;
		}
		// 初期化的场合
		if("true".equals(request.getParameter("subKbn")) && !"1".equals(request.getParameter("selectChg"))){
			String sqll = "select * from cdata_leave where userid=? and leavedate=?";
			Object[] paramsl = new Object[2];
			paramsl[0] = userinfo.getUserId();
			paramsl[1] = request.getParameter("wdate");
			List<Object> listl = JdbcUtil.getInstance().excuteQuery(sqll, paramsl);
			
			String sql = "select * from cdata_worktime where userid=? and date=?";
			Object[] params = new Object[2];
			params[0] = userinfo.getUserId();
			params[1] = paramwdate;
			List<Object> list = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			com.bwc.ework.form.Date date1 = DateTimeUtil.stringToDate(paramwdate);
			String userid = userinfo.getUserId();
			String year = date1.getYear();
			String month = date1.getMonth();
			String day = date1.getDay();
			String date = paramwdate;
			String begin = request.getParameter("wbegin");
			String end = request.getParameter("wend");
			if(listl.size() > 0){
				request.setAttribute("errmsg", "当天已请假，无法签到！");
			}else{
				// 数据存在更新操作
				if(list.size()>0){
					String updateSql = "update cdata_worktime set year=?,month=?,day=?,date=?,begintime=?,endtime=?,worktime=?,comment=?"
							+ " where userid=? and date=?";
					Object[] updateparams = new Object[10];
					updateparams[0] = year;
					updateparams[1] = month;
					updateparams[2] = day;
					updateparams[3] = date;
					updateparams[4] = begin;
					updateparams[5] = end;
					updateparams[7] = comment;
					try {
						updateparams[6] = DateTimeUtil.getHours(begin,end);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					updateparams[8] = userid;
					updateparams[9] = date;
					JdbcUtil.getInstance().executeUpdate(updateSql, updateparams);
				}else{
					String insertSql = "insert into cdata_worktime value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					Object[] insertparams = new Object[15];
					insertparams[0] = userid;
					insertparams[1] = year;
					insertparams[2] = month;
					insertparams[3] = day;
					insertparams[4] = date;
					insertparams[5] = begin;
					insertparams[6] = end;
					insertparams[8] = comment;
					try {
						insertparams[7] = DateTimeUtil.getHours(begin,end);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					JdbcUtil.getInstance().executeUpdate(insertSql, insertparams);
				}
				
				request.setAttribute("beginlbl", "已签");
				request.setAttribute("endlbl", "已签");
			}
			
			// 日期设定
			request.setAttribute("sysDate", date);
			// 默认开始时间
			request.setAttribute("defaultBeginTime",begin);
			// 默认结束时间
			request.setAttribute("defaultEndTime", end);
			request.setAttribute("comment", comment);
			request.setAttribute("dtladdress", "");
			try {
				getWeekData(request);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			// 系统当前时间取得
			SimpleDateFormat formattime=new SimpleDateFormat("yyyy-MM-dd"); 
			
			// 日期设定
			request.setAttribute("sysDate", "1".equals(request.getParameter("selectChg")) ? paramwdate : formattime.format(new Date()));
			String dateTime = "1".equals(request.getParameter("selectChg")) ? paramwdate: formattime.format(new Date());
			
			String sql = "select * from cdata_worktime where userid=? and date=?";
			Object[] params = new Object[2];
			params[0] = userinfo.getUserId();
			params[1] = dateTime;
			List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
			
			if("1".equals(request.getParameter("selectChg"))){
				try {
					getWeekData(request);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JSONObject jsonObject = new JSONObject();
				if(list1.size()>0){
					Map<String, Object> set = (Map<String, Object>)list1.get(0);
					jsonObject.put("defaultBeginTime", set.get("begintime").toString()); 
					jsonObject.put("defaultEndTime", set.get("endtime").toString());
					jsonObject.put("comment", set.get("comment").toString());
					jsonObject.put("settedFlg", "1");
				}else{
					jsonObject.put("defaultBeginTime",userinfo.getBeginTime().toString());
					jsonObject.put("defaultEndTime", userinfo.getEndTime().toString());
					jsonObject.put("comment", "");
					jsonObject.put("settedFlg", "0");
				}
				jsonObject.put("week0", request.getAttribute("week0"));
				jsonObject.put("week1", request.getAttribute("week1"));
				jsonObject.put("week2", request.getAttribute("week2"));
				jsonObject.put("week3", request.getAttribute("week3"));
				jsonObject.put("week4", request.getAttribute("week4"));
				jsonObject.put("week5", request.getAttribute("week5"));
				jsonObject.put("week6", request.getAttribute("week6"));
		        response.setCharacterEncoding("utf-8");  
		        response.getWriter().write(jsonObject.toString());

		        return;
			}
			else{
				if(list1.size()>0){
					Map<String, Object> set = (Map<String, Object>)list1.get(0);
					// 默认开始时间
					request.setAttribute("defaultBeginTime",set.get("begintime").toString());
					// 默认结束时间
					request.setAttribute("defaultEndTime", set.get("endtime").toString());
					
					request.setAttribute("comment", set.get("comment").toString());
					request.setAttribute("dtladdress", "");
					
					request.setAttribute("beginlbl", "已签");
					request.setAttribute("endlbl", "已签");
					try {
						getWeekData(request);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					// 默认开始时间
					request.setAttribute("defaultBeginTime",userinfo.getBeginTime().toString());
					// 默认结束时间
					request.setAttribute("defaultEndTime", userinfo.getEndTime().toString());
					
					request.setAttribute("comment", "");
					request.setAttribute("dtladdress", "");
					request.setAttribute("beginlbl", "出勤");
					request.setAttribute("endlbl", "退勤");
					
					try {
						getWeekData(request);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
  
		RequestDispatcher re = request.getRequestDispatcher("recordworktime.jsp");
		re.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private void getWeekData(HttpServletRequest request) throws ParseException{
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		String dateStr = request.getParameter("wdate2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> weekDate = convertWeekDate(dateStr == null ? new Date() : sdf.parse(dateStr));
		
		String dispStr;
		for(int i= 0; i<7;i++){
			dispStr = weekDate.get(i);
			String sql = "select * from cdata_worktime where userid=? and date = ?";
			Object[] params = new Object[2];
			params[0] = userinfo.getUserId();
			params[1] = weekDate.get(i);
			List<Object> list1 = JdbcUtil.getInstance().excuteQuery(sql, params);
			if(list1.size() > 0){
				Map<String, Object> set = (Map<String, Object>)list1.get(0);
				dispStr = dispStr + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + set.get("begintime").toString()
						+ "&nbsp&nbsp" + "~" + "&nbsp&nbsp" + set.get("endtime").toString();
			}
			
			request.setAttribute("week" + i , dispStr);
		}
	}
	
	/**
	 * 根据选择日期取得本周的所有日期
	 * @param time
	 * @return
	 */
	private List<String> convertWeekDate(Date time) {  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
          cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        String beginDate = sdf.format(cal.getTime()); //周一时间   
        List<String> result = new ArrayList<String>();
        result.add(beginDate + "&nbsp&nbsp"+ getweekDay(cal));
        for(int i=1;i<7;i++){
        	cal.add(Calendar.DATE,1);
        	result.add(sdf.format(cal.getTime()) +"&nbsp&nbsp"+ getweekDay(cal));
        }
        
        return result;
   } 
	
	/**
	 * 根据日期取得星期几
	 * @param cal
	 * @return
	 */
	private String getweekDay(Calendar cal){
		String str = "";
		switch (cal.get(Calendar.DAY_OF_WEEK)){
		    case Calendar.MONDAY:
		    	str = "(一)";
		    	break;
		    case Calendar.TUESDAY:
		    	str = "(二)";
		    	break;
		    case Calendar.WEDNESDAY:
		    	str = "(三)";
		    	break;
		    case Calendar.THURSDAY:
		    	str = "(四)";
		    	break;
		    case Calendar.FRIDAY:
		    	str = "(五)";
		    	break;
		    case Calendar.SATURDAY:
		    	str = "(六)";
		    	break;
		    case Calendar.SUNDAY:
		    	str = "(日)";
		    	break;
		}
		
		return str;
	}
	
	private void delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		User userinfo = (User)session.getAttribute("userinfo");
		
		String sql = "delete from cdata_worktime where userid=? and date=?";
		Object[] params = new Object[2];
		params[0] = userinfo.getUserId();
		params[1] = request.getParameter("wdate");
		JdbcUtil.getInstance().executeUpdate(sql, params);
		
		JSONObject jsonObject = new JSONObject();
		try {
			getWeekData(request);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jsonObject.put("defaultBeginTime",userinfo.getBeginTime().toString());
		jsonObject.put("defaultEndTime", userinfo.getEndTime().toString());
		jsonObject.put("comment", "");
		jsonObject.put("settedFlg", "0");
		jsonObject.put("week0", request.getAttribute("week0"));
		jsonObject.put("week1", request.getAttribute("week1"));
		jsonObject.put("week2", request.getAttribute("week2"));
		jsonObject.put("week3", request.getAttribute("week3"));
		jsonObject.put("week4", request.getAttribute("week4"));
		jsonObject.put("week5", request.getAttribute("week5"));
		jsonObject.put("week6", request.getAttribute("week6"));
        response.setCharacterEncoding("utf-8");  
        response.getWriter().write(jsonObject.toString());

        return;
	}

}
