package com.demo.spring.domain;

import java.io.Serializable;

import com.demo.spring.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8853559963616694536L;
	private User user;
	private String token;

}
