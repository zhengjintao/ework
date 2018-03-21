package com.bwc.ework.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

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
			
			SimpleDateFormat formattime2 = new SimpleDateFormat("yyyy/MM/dd");
			final String now2 = formattime2.format(new Date()) + " 8:30";
			for (int i = 0; i < infolist.size(); i++) {
				Map<String, Object> set = (Map<String, Object>) infolist.get(i);
				final String openid = String.valueOf(set.get("openid"));
				
				if(openid == null || openid.length() < 10){
					continue;
				}
				final String username = String.valueOf(set.get("username"));
				Thread t = new Thread(new Runnable() {
					public void run() {
						TemplateMessageUtil.sendTemplateMessage(openid, Consts.templetid, username, now2);
					}
				});
				t.start();
			}

		} catch (Exception e) {
			System.out.println("-------------解析信息发生异常--------------");
		}
	}
}
