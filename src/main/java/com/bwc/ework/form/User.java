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
	private String mail;
	private Date birthday;
	private String maincompanyid;
	private String maincompanyname;
	private String rest;
	
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMaincompanyid() {
		return maincompanyid;
	}
	public void setMaincompanyid(String maincompanyid) {
		this.maincompanyid = maincompanyid;
	}
	public String getMaincompanyname() {
		return maincompanyname;
	}
	public void setMaincompanyname(String maincompanyname) {
		this.maincompanyname = maincompanyname;
	}
	public String getRest() {
		return rest;
	}
	public void setRest(String rest) {
		this.rest = rest;
	}
	
}
