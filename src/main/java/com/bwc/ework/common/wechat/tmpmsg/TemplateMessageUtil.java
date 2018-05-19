package com.bwc.ework.common.wechat.tmpmsg;

import org.json.JSONObject;

import com.bwc.ework.common.wechat.AccessTokenGeter;
import com.bwc.ework.common.wechat.HttpRequestor;
import com.bwc.ework.common.wechat.URLProducer;

public class TemplateMessageUtil {
	// 发送模板消息
	public static String sendTemplateMessage(String touser, String template_id, String username, String time) {
		String msg = "--Begin set accesstoken--<br>";
		String token = AccessTokenGeter.getStrAccessToken();
		String sendUrl = URLProducer.GetTemplateSendUrl(token);
		 msg = msg+ "--url" + sendUrl +"<br>";
		// post请求数据
		String url = "http://www.freertokyo.com/ework/list.do";
		// data
		JSONObject dataJson = new JSONObject();
		// first
		JSONObject fstJson = new JSONObject();
		fstJson.put("value", "今日还未签到。加班中？（休息日请忽略）");
		// keyword1
		JSONObject k1Json = new JSONObject();
		k1Json.put("value", username);
		// keyword2
		JSONObject k2Json = new JSONObject();
		k2Json.put("value", time);
		// keyword3
		JSONObject k3Json = new JSONObject();
		k3Json.put("value", "未签到");
		k3Json.put("color", "#DC143C");
		// remark
		JSONObject rmkJson = new JSONObject();
		rmkJson.put("value", "点击可快速进行签到, GO>>");
		rmkJson.put("color", "#173177");
		
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
		
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("touser", touser);
		jsonmsg.put("template_id", template_id);
		jsonmsg.put("url", url);
		jsonmsg.put("data", dataJson);
		msg = msg + "<br>" + "--begin send temp message" + jsonmsg.toString() + "<br>";
		try {
			String rep = HttpRequestor.httpPostProc(sendUrl, jsonmsg);
			
			msg = msg + "<br>" + "--Success send temp message" + "<br>";
			msg = msg + "<br>" + "--respone data" + rep + "<br>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = msg + "<br>" + "--Failed send temp message" + e.getMessage() + "<br>";
		}
		
		return msg;
	}
}
