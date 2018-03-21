package com.bwc.ework.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.bwc.ework.common.wechat.Consts;
import com.bwc.ework.common.wechat.tmpmsg.TemplateMessageUtil;

public class TimerManager {
	//时间间隔  
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;  
    public TimerManager() { 
    	SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd");
		String now = formattime.format(new Date());
    	
		//String msg = TemplateMessageUtil.sendTemplateMessage("ofXqDwsbmAUZrHh85BuJkBwSpfaA", Consts.templetid, "郑锦涛", now + " 8:30");
    	scheduleNoticeToUser();
    	scheduleNoticeToAdmin();
    }  
    
    private void scheduleNoticeToUser(){
    	Calendar calendar = Calendar.getInstance();   
        
        /*** 定制每日20:30执行方法 ***/  
        calendar.set(Calendar.HOUR_OF_DAY, 20);  
        calendar.set(Calendar.MINUTE, 30);  
        calendar.set(Calendar.SECOND, 0);  
           
        Date date=calendar.getTime(); //第一次执行定时任务的时间  
        System.out.println(date);  
        System.out.println("before 方法比较："+date.before(new Date()));  
        //如果第一次执行定时任务的时间 小于 当前的时间  
        //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准  
        if (date.before(new Date())) {  
            date = this.addDay(date, 1);  
            System.out.println(date);  
        }  
           
        Timer timer = new Timer();  
           
        LyzTimerTaskNoticeUser task = new LyzTimerTaskNoticeUser();  
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。  
        timer.schedule(task,date,PERIOD_DAY); 
    }
    
    private void scheduleNoticeToAdmin(){
    	Calendar calendar = Calendar.getInstance();   
        
        /*** 定制每日21:00执行方法 ***/  
 
        calendar.set(Calendar.HOUR_OF_DAY, 21);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
           
        Date date=calendar.getTime(); //第一次执行定时任务的时间  
        System.out.println(date);  
        System.out.println("before 方法比较："+date.before(new Date()));  
        //如果第一次执行定时任务的时间 小于 当前的时间  
        //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准  
        if (date.before(new Date())) {  
            date = this.addDay(date, 1);  
            System.out.println(date);  
        }  
           
        Timer timer = new Timer();  
           
        LyzTimerTask task = new LyzTimerTask();  
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。  
        timer.schedule(task,date,PERIOD_DAY); 
    }
  
        // 增加或减少天数  
        public Date addDay(Date date, int num) {  
         Calendar startDT = Calendar.getInstance();  
         startDT.setTime(date);  
         startDT.add(Calendar.DAY_OF_MONTH, num);  
         return startDT.getTime();  
        } 
}
