package com.bwc.ework.common.wechat.tmpmsg;

import org.json.JSONObject;

public class TemplateMessageImpl implements ITemplateMessage {

	public JSONObject buildMessage() {
		/*String msg = "--Begin set accesstoken--<br>";
		//AccessToken accessToken = WechatUtil.getAccessToken();
		String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ accesstoken;
		 msg = msg+ "--url" + sendUrl +"<br>";
		//msg = msg + "<br>" + "--success to get accesstoken--" + accessToken.getToken() + "<br>";
		// post请求数据
		String url = "http://www.freertokyo.com/ework";
		// data
		JSONObject dataJson = new JSONObject();
		// first
		JSONObject fstJson = new JSONObject();
		fstJson.put("value", "remind");
		// keyword1
		JSONObject k1Json = new JSONObject();
		k1Json.put("value", username);
		// keyword2
		JSONObject k2Json = new JSONObject();
		k2Json.put("value", time);
		// keyword3
		JSONObject k3Json = new JSONObject();
		k3Json.put("value", status);
		// remark
		JSONObject rmkJson = new JSONObject();
		rmkJson.put("value", "it's the time to leave work.");
		
		dataJson.put("first", fstJson);
		dataJson.put("keyword1", k1Json);
		dataJson.put("keyword2", k2Json);
		dataJson.put("keyword3", k3Json);
		dataJson.put("remark", rmkJson);

//		{{first.DATA}}
//		姓名：{{keyword1.DATA}}
//		时间：{{keyword2.DATA}}
//		状态：{{keyword3.DATA}}
//		{{remark.DATA}}
		
		
		jsonmsg.put("touser", touser);
		jsonmsg.put("template_id", template_id);
		jsonmsg.put("data", dataJson);*/
		JSONObject jsonmsg = new JSONObject();
		return jsonmsg;
	}

}
