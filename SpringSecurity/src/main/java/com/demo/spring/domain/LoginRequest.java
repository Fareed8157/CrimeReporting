package com.demo.spring.domain;

import lombok.Data;

@Data
public class LoginRequest {

	private String emailOrPhoneNumber;
	private String password;
	
}
