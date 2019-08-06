package com.demo.spring.controller;

import java.io.IOException;
import java.security.Principal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HttpSessionMutexListener;

import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.Response;
import com.demo.spring.model.CrimeTypes;
import com.demo.spring.model.Fir;
import com.demo.spring.model.PoliceStation;
import com.demo.spring.service.CrimeService;
import com.demo.spring.service.FirService;
import com.demo.spring.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FirController {

	ObjectMapper obm=new ObjectMapper();
	
	Logger logger=LoggerFactory.getLogger(FirController.class);
	private @Autowired CrimeService crimeService;
	private @Autowired FirService firService;
	private @Autowired UserService userService;
	
	@PostMapping(value="/fir",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<Response> saveSlider(MultipartHttpServletRequest file ,@RequestParam(required=false,value="fir") String fir,
			@RequestParam(required=false,value="crimeType") String crimeType,
			@RequestParam(required=false,value="policeStation") String policeStation) throws JsonParseException, JsonMappingException, IOException{
		//Slider slider1=new ObjectMapper().readValue(slider,Slider.class);
		//sliderService.save(slider1,file);
		Fir fir1=new ObjectMapper().readValue(fir, Fir.class);
		PoliceStation p=new ObjectMapper().readValue(policeStation, PoliceStation.class);
		CrimeTypes c=new ObjectMapper().readValue(crimeType, CrimeTypes.class);
		//fir.setUser(userService.getUserByEmailOrPhoneNumber(principal.getName()));
		//logger.info("Username: "+principal.getName());
		logger.info("inside saveFir fir: "+fir);
		logger.info("inside saveFir policeStation: "+p);
		logger.info("inside saveFir CrimeType: "+c);
		System.out.println("this is fir"+fir1);
		if(file!=null)
			System.out.println("File name"+file.getFileNames());
		//System.out.println("Slider"+slider1);
		System.out.println("Inside save fir");
		fir1.setTitle(c.getName());
		firService.saveFir(fir1,file);
		return new ResponseEntity<Response>(new Response("fir saved successfully"),HttpStatus.OK);
	}
}
