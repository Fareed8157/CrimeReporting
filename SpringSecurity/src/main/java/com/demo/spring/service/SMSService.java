package com.demo.spring.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {

	private final static String ACCOUNT_SID = "AC9485018b76b222d754d54e3db3d94daf";
	private final static String AUTH_ID = "2c1bbad180b5617d788d5ff27f6218fc";
	
	static {
		Twilio.init(ACCOUNT_SID, AUTH_ID);
	}
	
	public boolean send2FaCode(String mobilenumber, String twoFaCode) {
		
		Message.creator(new PhoneNumber(mobilenumber), new PhoneNumber("+17865446154"),
				"Your Two Factor Authentication code is: "+ twoFaCode).create();
		
		return true;
		
		
	}
	
}
