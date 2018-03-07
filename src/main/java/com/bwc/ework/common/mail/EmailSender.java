package com.bwc.ework.common.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	private int port = 587;
	private String host = "smtp.google.com";
	private String from = "xiaonei0912@gmail.com";
	private boolean auth = true;
	private String username = "xiaonei0912@gmail.com";
	private String password = "Jintao123";
	private Protocol protocol = Protocol.TLS;
	private boolean debug = true;
	
	public void sendEmail(String to, String subject, String body){
		MimeMessage message = new MimeMessage(GetSession());
		try {
		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(to)};
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(subject);
		    message.setSentDate(new Date());
		    message.setText(body);
		    Transport.send(message);
		} catch (MessagingException ex) {
		    ex.printStackTrace();
		}
	}
	
	public void sendMultiEmail(String to, String subject, String body){
		MimeMessage message = new MimeMessage(GetSession());
		Multipart multipart = new MimeMultipart("alternative");
		MimeBodyPart textPart = new MimeBodyPart();
		String textContent = "Hi, Nice to meet you!";
		try {
			textPart.setText(textContent);
			MimeBodyPart htmlPart = new MimeBodyPart();
			String htmlContent = "<html><h1>Hi</h1><p>Nice to meet you!</p></html>";
			htmlPart.setContent(htmlContent, "text/html");
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(htmlPart);
			message.setContent(multipart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Session GetSession(){
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		switch (protocol) {
		    case SMTPS:
		        props.put("mail.smtp.ssl.enable", true);
		        break;
		    case TLS:
		        props.put("mail.smtp.starttls.enable", true);
		        break;
		}
		
		Authenticator authenticator = null;
		if (auth) {
		    props.put("mail.smtp.auth", true);
		    authenticator = new Authenticator() {
		        private PasswordAuthentication pa = new PasswordAuthentication(username, password);
		        @Override
		        public PasswordAuthentication getPasswordAuthentication() {
		            return pa;
		        }
		    };
		}
		
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(debug);
		
		return session;
	}
}
