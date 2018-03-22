package com.bwc.ework.common.wechat.meta;

public class JsTicket {
	public static String Ticket = "ticket";
	public static String ExpiresIn = "expires_in";
	
	private String ticket;
	private long expirationTime;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public long getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	
}
