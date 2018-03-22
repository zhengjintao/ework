package com.bwc.ework.common.wechat;

import org.json.JSONObject;

import com.bwc.ework.common.wechat.meta.JsTicket;

public class JsTicketGeter {
private static JsTicket atcache = null;
	
	private static JsTicket getTicket(){
		if(atcache == null || System.currentTimeMillis() > atcache.getExpirationTime()){
			JSONObject jsonObject= HttpRequestor.httpGetProc(URLProducer.GetJsTicketUrl(null));
	        atcache =new JsTicket();
	        if(jsonObject!= null && jsonObject.has(JsTicket.Ticket)){
	        	atcache.setTicket(jsonObject.getString(JsTicket.Ticket));
	        	
	        	long exp = System.currentTimeMillis() + Integer.parseInt(jsonObject.get(JsTicket.ExpiresIn).toString());
	        	atcache.setExpirationTime(exp);
	        }
		}
		
		return atcache;
	}
	
	public static String getStrTicket(){
		return getTicket().getTicket();
	}
}
