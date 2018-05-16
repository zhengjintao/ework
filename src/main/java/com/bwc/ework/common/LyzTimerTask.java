package com.bwc.ework.common;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.mail.MessagingException;

import com.bwc.ework.common.mail.SendMailFactory;

public class LyzTimerTask extends TimerTask {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void run() {
		try {
			// 在这里写你要执行的内容
			System.out.println("执行当前时间" + formatter.format(Calendar.getInstance().getTime()));

			SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
			String now = formattime.format(new Date());

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				return;
			}
			
			// 邮件向各个公司的管理员发送当日未签到人员名单
			String sql ="SELECT * FROM mstr_company where delflg='0'";
			List<Object> companyinfo = JdbcUtil.getInstance().excuteQuery(sql, null);
			for (int i = 0; i < companyinfo.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) companyinfo.get(i);
				String sql2 = "SELECT * FROM cdata_companyuser com join mstr_user usr on com.userid=usr.userid and usr.delflg='0' and usr.mail is not null and usr.mail !='' where com.companyid=? and com.rolekbn in ('0', '1')";
				Object[] params2 = new Object[1];
				params2[0] = set.get("companyid");
				List<Object> admininfo = JdbcUtil.getInstance().excuteQuery(sql2, params2);
				List<String> adminmail = new ArrayList<String>();
				for (int j = 0; j < admininfo.size(); j++) {
					Map<String, Object> mail = (Map<String, Object>) admininfo.get(j);
					adminmail.add(mail.get("mail").toString());
				}
				
				if(adminmail.size() > 0){
					sql2 = "SELECT * FROM cdata_companyuser com left join mstr_user usr on com.userid=usr.userid and usr.delflg='0' where com.companyid=? and com.rolekbn='2'" +
							"and com.userid not in (select userid from cdata_worktime where date=?) and com.userid not in (select userid from cdata_leave where leavedate =?)";
					params2 = new Object[3];
					params2[0] = set.get("companyid");
					params2[1] = now;
					params2[2] = now;
					List<Object> userinfo = JdbcUtil.getInstance().excuteQuery(sql2, params2);
					
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < userinfo.size(); j++) {
						Map<String, Object> set2 = (Map<String, Object>) userinfo.get(j);
						sb.append(String.valueOf(set2.get("username"))).append("<br>");
					}
					
					if (sb.length() > 0) {
						final List<String> uadminmail = adminmail;
						final String mailname = "员工未签到提醒(休息日请忽略)";
						final String text = "["+set.get("companynm") + "]<br>下列人员今日未签到<br>" + "" + sb.toString();
						Thread t = new Thread(new Runnable() {
							public void run() {
								try {
									SendMailFactory.getInstance().getMailSender().sendMessage(uadminmail, mailname, text, null);
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (MessagingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						t.start();
						
					}
				}
			}
		} catch (Exception e) {
			System.out.println("-------------解析信息发生异常--------------");
		}
	}
}
