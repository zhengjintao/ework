package com.bwc.ework.common.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class SendMailFactory {
	private SendMailFactory() {

	}

	public static SendMailFactory getInstance() {
		return new SendMailFactory();
	}

	public QQMailSender getMailSender() throws UnsupportedEncodingException {
		byte[] b = new byte[11];
		b[0] = 74;
		b[1] = 105;
		b[2] = 110;
		b[3] = 116;
		b[4] = 97;
		b[5] = 111;
		b[6] = 49;
		b[7] = 50;
		b[8] = 51;
		b[9] = 52;
		b[10] = 53;
		Map<String, String> map = new HashMap<String, String>();
		QQMailSender mail = new QQMailSender("xiaonei0912@qq.com", new String(b));
		map.put("mail.smtp.host", "smtp.qq.com");
		map.put("mail.smtp.auth", "true");
		map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		map.put("mail.smtp.port", "465");
		map.put("mail.smtp.socketFactory.port", "465");
		mail.setPros(map);
		mail.initMessage();
		return mail;
	}

}
