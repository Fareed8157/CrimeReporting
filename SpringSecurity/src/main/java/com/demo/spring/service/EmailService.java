package com.demo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private static final String username="fareedmahar6@gmail.com";
	private static final String password = "Raja8166";
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void SendMail(String email,String code) {
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(username);
		mail.setSubject("OTP Verification");
		mail.setText("Your code : "+code);
		javaMailSender.send(mail);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
