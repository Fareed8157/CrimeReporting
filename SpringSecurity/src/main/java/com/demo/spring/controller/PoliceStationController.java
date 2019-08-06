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
import com.demo.spring.model.PoliceStation;
import com.demo.spring.service.PoliceStationService;

@RestController
public class PoliceStationController {

	Logger logger=LoggerFactory.getLogger(FirController.class);
	@Autowired PoliceStationService policeStationService;
	
	@PostMapping(path="/policeStation")
	@ResponseBody
	public ResponseEntity<Response> savePoliceStation(@RequestBody  PoliceStation policeStation){
		policeStationService.savePoliceStation(policeStation);
		logger.info("inside policeStation"+policeStation);
		return new ResponseEntity<Response>(new Response("Saved PoliceStation Successfully"),HttpStatus.OK);
	}
	@GetMapping(path="/policeStation")
	@ResponseBody
	public ResponseEntity<List<PoliceStation>> findAllPoliceStations(){
		List<PoliceStation> policeStations=policeStationService.findAll();
		return new ResponseEntity<List<PoliceStation>>(policeStations,HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
