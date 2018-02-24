package com.bwc.ework.form;

import java.sql.Time;

public class User {
	private String userId;
	private String userPwd;
	private String userName;
	private String delflg;
	private Time beginTime;
	private Time endTime;
	private String sex;
	private String authflg;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Time getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAuthflg() {
		return authflg;
	}
	public void setAuthflg(String authflg) {
		this.authflg = authflg;
	}
	
}
