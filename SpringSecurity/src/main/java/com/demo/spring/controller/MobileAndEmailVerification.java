package com.demo.spring.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.model.User;
import com.demo.spring.service.EmailService;
import com.demo.spring.service.SMSService;



@RestController
public class MobileAndEmailVerification {

	private Map<String,User> otp_data=new HashMap<>();
	
	@Autowired
	EmailService emailService;
	@Autowired
	SMSService smsService;
	
	@PostMapping(value="/users/emails/{emailid}/2fa") 
	public ResponseEntity<Object> send2faCodeinEmail(@PathVariable("emailid") String emailid) throws AddressException, MessagingException {
		User user=new User();
		String twoFaCode = String.valueOf(new Random().nextInt(9999) + 1000);
		user.setTwofaCode(twoFaCode);
		user.setTwofaExpireTime(System.currentTimeMillis()+120000);
		emailService.SendMail(emailid,twoFaCode);
		//daoService.update2FAProperties(id, twoFaCode);
		otp_data.put(emailid, user);
		return new ResponseEntity<>("OTP is sent Successfully",HttpStatus.OK);
	}
	
	@PostMapping(value="/users/mobilenumbers/{mobilenumber}/2fa")
	@ResponseBody
	public ResponseEntity<Object> send2faCodeinSMS( @PathVariable("mobilenumber") String mobile) {
		User user=new User();
		String twoFaCode = String.valueOf(new Random().nextInt(9999) + 1000);
		user.setTwofaCode(twoFaCode);
		user.setTwofaExpireTime(System.currentTimeMillis()+90000);
		smsService.send2FaCode(mobile, twoFaCode);
		otp_data.put(mobile, user);
		//daoService.update2FAProperties(id, twoFaCode);
		return new ResponseEntity<>("OTP is sent Successfully",HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/{mobilenumbers}/{2facode}", method=RequestMethod.PUT) 
	public ResponseEntity<Object> verify(@PathVariable("mobilenumbers") String no,@PathVariable("2facode") String code) {
	
		if(code==null || code.trim().length()<=0) {
			return new ResponseEntity<>("Please provide OTP",HttpStatus.BAD_REQUEST);
		}
		if(otp_data.containsKey(no)) {
			User user=otp_data.get(no);
			if(user!=null) {
				if(user.getTwofaExpireTime()>=System.currentTimeMillis()) {
					if(code.equals(user.getTwofaCode())) {
						otp_data.remove(no);
						return new ResponseEntity<>("OTP is Verified Successfully",HttpStatus.OK);
					}
					return new ResponseEntity<>("Invalid OTP",HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("OTP is Expired",HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(no+" not found",HttpStatus.NOT_FOUND);
//		boolean isValid = daoService.checkCode(id, code);
//		
//		if(isValid)
//			return new ResponseEntity<>(HttpStatus.OK);

	}
}
