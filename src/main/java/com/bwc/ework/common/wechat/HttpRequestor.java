package com.bwc.ework.common.wechat;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpRequestor {
	public static JSONObject httpGetProc(String url){
		HttpGet get = new HttpGet(url);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse resp = null;
		try {
			 resp = client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HttpEntity httpEntity=resp.getEntity();
		JSONObject jsonObject=null;
        if(httpEntity!=null){
            String result = null; 
			try {
				result = EntityUtils.toString(httpEntity,"UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            jsonObject=new JSONObject(result);
        }
        get.releaseConnection();
        
		return jsonObject;
	}
	
	public static String httpPostProc(String url, JSONObject jsonParam) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		// 表单方式
		// List<BasicNameValuePair> pairList = new
		// ArrayList<BasicNameValuePair>();
		// pairList.add(new BasicNameValuePair("name", "admin"));
		// pairList.add(new BasicNameValuePair("pass", "123456"));
		// httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}
}
