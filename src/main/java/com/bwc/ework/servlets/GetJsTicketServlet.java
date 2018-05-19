package com.bwc.ework.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.WechatConsts;
import com.bwc.ework.common.wechat.JsTicketGeter;

/**
 * Servlet implementation class GetJsTicketServlet
 */
public class GetJsTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJsTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url =request.getParameter("url");
		JSONObject jsonObject = new JSONObject();
        try {  
            //获取Ticket  
            String jsapi_ticket = JsTicketGeter.getStrTicket();  
            
          //获取签名signature
            String noncestr = UUID.randomUUID().toString();
            String timestamp = Long.toString(System.currentTimeMillis() / 1000);

            String str = "jsapi_ticket=" + jsapi_ticket +
                    "&noncestr=" + noncestr +
                    "&timestamp=" + timestamp +
                    "&url=" + url;
            //sha1加密
            String signature = SHA1(str);
            
            jsonObject.put("appId", WechatConsts.APPID);  
            jsonObject.put("timestamp", timestamp);  
            jsonObject.put("nonceStr", noncestr);  
            jsonObject.put("signature", signature);  
        } catch (Exception e) {
        }  
        
        response.setCharacterEncoding("utf-8");  
        response.getWriter().write(jsonObject.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
