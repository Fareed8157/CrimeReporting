package com.demo.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.domain.Response;
import com.demo.spring.model.CrimeTypes;
import com.demo.spring.model.Fir;
import com.demo.spring.model.PoliceStation;
import com.demo.spring.service.CrimeService;
import com.demo.spring.service.FirService;

@RestController
public class CrimeTypeController {

	Logger logger=LoggerFactory.getLogger(FirController.class);
	private @Autowired CrimeService crimeService;
	private @Autowired FirService firService;
	
	@PostMapping(path="/crime")
	@ResponseBody
	public ResponseEntity<Response> saveCrime(@RequestBody  CrimeTypes crime){
		crimeService.save(crime);
		logger.info("inside crimeType"+crime);
		return new ResponseEntity<Response>(new Response("Saved Successfully"),HttpStatus.OK);
	}
	
	@GetMapping(path="/crime")
	public ResponseEntity<List<CrimeTypes>> findAllPoliceStations(){
		List<CrimeTypes> crimeTypes=crimeService.findAll();
		return new ResponseEntity<List<CrimeTypes>>(crimeTypes,HttpStatus.OK);
	}
	
	
}
