package com.bwc.ework.common.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class EmailTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		qqmail();
		//EmailSender es = new EmailSender();
		//es.sendEmail("xiaonei0912@qq.com", "test", "testbody");
	}
	
	private static void qqmail(){
        /*
         * 添加收件人有三种方法：
         * 1,单人添加(单人发送)调用setRecipient(str);发送String类型
         * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
         * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
         */
        List<String> list = new ArrayList<String>();
        list.add("382362074@qq.com");
        //list.add("92@sina.cn");
        list.add("xiaonei0912@gmail.com");
        try {
			SendMailFactory.getInstance().getMailSender().sendMessage(list, "test", "testconten", null);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	

}
