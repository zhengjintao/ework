package com.bwc.ework.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GoogleMapUtil {
	public static void main(String[] args){
		String add = geocodeAddr("35.8324875", "139.683537");
	}
	public static String geocodeAddr(String latitude, String longitude) {
		String addr = "";

		// 也可以是http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s，不过解析出来的是英文地址
		// 密钥可以随便写一个key=abc
		// output=csv,也可以是xml或json，不过使用csv返回的数据最简洁方便解析
		String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?&key=AIzaSyAP42AskYza1DwaysKIXhoxKq3cvD8VS0Y&language=ja&latlng=%s,%s",latitude, longitude);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
		      myURL = new URL(url);
		} catch (MalformedURLException e) {
		      e.printStackTrace();
		      return null;
		}
		try {
		      httpsConn = (URLConnection) myURL.openConnection();
		      if (httpsConn != null) {
		            InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
		            BufferedReader br = new BufferedReader(insr);
		            String data = null;
		            if ((data = br.readLine()) != null) {
		                  System.out.println(data);
		                  String[] retList = data.split(",");
		                  if (retList.length > 2 && ("200".equals(retList[0]))) {
		                        addr = retList[2];
		                        addr = addr.replace("\"", "");
		                  } else {
		                        addr = "";
		                  }
		            }
		      insr.close();
		      }
		} catch (IOException e) {
		      e.printStackTrace();
		      return null;
		}
		      return addr;
		}
}
