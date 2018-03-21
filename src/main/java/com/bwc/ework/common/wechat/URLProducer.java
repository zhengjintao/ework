package com.bwc.ework.common.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLProducer {
	/**
	 * 获取基础支持中的access_token
	 * 注意：用户授权操作中的 ［网页授权access_token］不一样
	 * @return
	 */
	public static String GetAcessTokenUrl(){
		String baseurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		String url = baseurl + "&appid=" + Consts.APPID + "&secret="+ Consts.APPSECRET;
		
		return url;
	}
	
	/**
	 * 用户授权引导URL
	 * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
	 * 
	 * 参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	 * @param callbackUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String GetAuthUrl(String callbackUrl) throws UnsupportedEncodingException{
		// 注意：由于授权操作安全等级较高，所以在发起授权请求时，微信会对授权链接做正则强匹配校验，
		// 如果链接的参数顺序不对，授权页面将无法正常访问
		
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Consts.APPID+
                "&redirect_uri=" + URLEncoder.encode(callbackUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
		return url;
	}
	
	/**
	 * 通过code换取网页授权access_token的接口URL
	 * 注意：此access_token与基础支持的access_token不同
	 * 
	 * 参考文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
	 * @param code
	 * @return
	 */
	public static String GetUserAuthUrl(String code){
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Consts.APPID+
                "&secret=" +Consts.APPSECRET+
                "&code=" +code+
                "&grant_type=authorization_code";
		return url;
	}
	
	/**
	 * 获取用户信息的接口URL
	 * @param accessToken  注意：是［网页授权access_token］，而非［基础支持中的access_token］
	 * @param openid
	 * @return
	 */
	public static String GetUserInfoUrl(String accessToken, String openid){
		String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +accessToken+
                "&openid=" +openid+
                "&lang=zh_CN";
		
		return infoUrl;
	}
	
	/**
	 * 模版消息发送的接口URL
	 * @param accessToken  注意：是［基础支持中的access_token］，而非［网页授权access_token］
	 * @return
	 */
	public static String GetTemplateSendUrl(String accessToken){
		if(accessToken == null || accessToken.length() ==0){
			accessToken = AccessTokenGeter.getStrAccessToken();
		}
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
		
		return url;
	}
}
