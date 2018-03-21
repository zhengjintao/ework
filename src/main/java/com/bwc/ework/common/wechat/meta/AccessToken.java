package com.bwc.ework.common.wechat.meta;

public class AccessToken {
	public static String AccessToken = "access_token";
	public static String ExpiresIn = "expires_in";
	
	private String token;
	private long expirationTime;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresin() {
		return expirationTime;
	}

	public void setExpiresin(long expiresin) {
		this.expirationTime = expiresin;
	}
}
