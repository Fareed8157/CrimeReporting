package com.demo.spring.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.spring.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JwtUser implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 509506125824315496L;
	private final Long id;
	private final String username;
	private final String password;
	private final String phoneNumber;
	private final User user;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;
	
	@JsonIgnore
	public Long getId() {
		return id;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public User getUser() {
		return user;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	
	
	
	
}
