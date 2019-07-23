package com.demo.spring.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	static BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
	public static String getPasswordHash(String password) {
		
		return bcrypt.encode(password);
	}

	
}
