package com.demo.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.UnauthorizedException;
import com.demo.spring.domain.LoginRequest;
import com.demo.spring.domain.UserDTO;
import com.demo.spring.model.User;
import com.demo.spring.security.JwtTokenUtil;
import com.demo.spring.security.JwtUser;
import com.demo.spring.service.UserService;

@RestController
public class AuthenticationController {

	@Value("${jwt.header}")
	private String tokenHeader;
	@Autowired UserService userService;
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<UserDTO> login(@RequestBody LoginRequest user, HttpServletRequest req,HttpServletResponse res){
		System.out.println("Userdata");
		System.out.println(user.getEmailOrPhoneNumber());
		System.out.println(userService.getUserByEmailOrPhoneNumber(user.getEmailOrPhoneNumber()));
		try {
			Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailOrPhoneNumber(), user.getPassword()));
			final JwtUser userDetails=(JwtUser)authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token=jwtTokenUtil.generateToken(userDetails);
			res.setHeader("Token", token);
			System.out.println("after logiin"+userDetails.getUser());
			return new ResponseEntity<UserDTO>(new UserDTO(userDetails.getUser(), token),HttpStatus.OK);
		} catch (Exception e) {
			throw new UnauthorizedException(e.getMessage());
		}
	}
}
