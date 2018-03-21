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
import com.bwc.ework.common.wechat.Consts;
import com.bwc.ework.common.wechat.tmpmsg.TemplateMessageUtil;

public class LyzTimerTaskNoticeUser extends TimerTask {

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

			String sql2 = "SELECT * FROM mstr_user where userid not in (select userid from cdata_worktime where date=?)"
					+ " and delflg='0' and authflg not in('0','1') "
					+ "and userid not in (select userid from cdata_leave where leavedate =?)";
			Object[] params2 = new Object[2];
			params2[0] = now;
			params2[1] = now;
			List<Object> infolist = JdbcUtil.getInstance().excuteQuery(sql2, params2);
			for (int i = 0; i < infolist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) infolist.get(i);
				String userid = String.valueOf(set.get("userid"));
				String openid = String.valueOf(set.get("openid"));
				//String token = "7_XhXIHBiJhmrZ5l8e1h1_t9p7UUPNmHd8GCjGuy2lRc9zgQoE8YC4jkgi65gK3WMX3eClTZ8kPGq8o-q3fE8dDzw0qB6mYFmdu2SFmp_DS82npK4Chn15lW5vJgehoFLy-CN-GGZxx6uNhOIXOEIjAEACSW";
				String msg = TemplateMessageUtil.sendTemplateMessage("ofXqDwsbmAUZrHh85BuJkBwSpfaA", Consts.templetid, "郑锦涛", now + " 8:30");
			}

		} catch (Exception e) {
			System.out.println("-------------解析信息发生异常--------------");
		}
	}

	private List<String> getadmminusermailadd() {
		// 管理员邮箱取得
		String sql = "select mail from mstr_user where authflg=? and delflg='0' and mail is not null";
		Object[] params = new Object[1];
		params[0] = "1";
		List<Object> userlist = JdbcUtil.getInstance().excuteQuery(sql, params);
		List<String> list = new ArrayList<String>();
		if (userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) userlist.get(i);
				if (set.get("mail") != null) {
					list.add(set.get("mail").toString());
				}
			}
		}

		return list;
	}

}
