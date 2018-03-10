package com.bwc.ework.common.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class AuthUtil {
	public static final String APPID="wx893db98985206df6";
    public static final String APPSECRET="a7681bcfbd70c0beb497715bfa1fb35a";
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject=null;
        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
        HttpEntity httpEntity=httpResponse.getEntity();
        if(httpEntity!=null){
            String result= EntityUtils.toString(httpEntity,"UTF-8");
            jsonObject=new JSONObject(result);
            //System.out.println("jsonObject:  "+jsonObject);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
    
    public static String createUrl(String backUrl) throws UnsupportedEncodingException{
    	 String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AuthUtil.APPID+
                 "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                 "&response_type=code" +
                 "&scope=snsapi_userinfo" +
                 "&state=STATE#wechat_redirect";
    	 
    	 return url;
    }
}
