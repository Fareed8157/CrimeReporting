package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.Response;
import com.demo.spring.model.Employee;
import com.demo.spring.model.PoliceStation;
import com.demo.spring.model.User;
import com.demo.spring.service.PoliceStationService;
import com.demo.spring.service.UserService;

@RestController
public class PreLoginController {

	@Autowired private UserService userService;
	@Autowired private PoliceStationService policeStationService;
	
	@PostMapping(value="/registration")
	public ResponseEntity<Response> registration(@RequestBody Employee user){
		User dbUser;
		if(user.getPoliceStationId()!=null) {
			dbUser=userService.save(user);
		}
		
		dbUser=userService.save(user);
		System.out.println("DbUser of Employee"+user);
		if(dbUser!=null) {
			return new ResponseEntity<Response>(new Response("Saved Succesfully"),HttpStatus.OK);
		}
		return null;
	}
}
