package com.demo.spring.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.spring.model.User;

public class JwtuserFactory {

	public static UserDetails create(User user) {
		
		JwtUser jwtUser=new JwtUser(user.getId(), user.getPhoneNumber(), user.getPassword(),user.getEmail(), user
		, maptoGrantedAuthorities(new ArrayList<String>(Arrays.asList("ROLE_"+user.getRole()))), user.isEnabled());
		return jwtUser;
	
	}

	private static List<GrantedAuthority> maptoGrantedAuthorities(List<String> authorities) {
		
		return authorities.stream().map(Authority-> new SimpleGrantedAuthority(Authority)).collect(Collectors.toList());
	}

}
