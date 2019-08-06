package com.demo.spring.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.domain.CriminalResponse;
import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByCriminal;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.domain.Response;
import com.demo.spring.model.Criminal;
import com.demo.spring.model.Slider;
import com.demo.spring.service.CriminalService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CriminalController {

	Logger logger=LoggerFactory.getLogger(CriminalController.class);
	
	@Autowired CriminalService criminalService;
	@PostMapping(value="/criminal",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response> saveCriminal(String criminal, MultipartHttpServletRequest file) throws JsonParseException, JsonMappingException, IOException{
		logger.info("inside saveCriminal: "+criminal);
		//logger.info("inside orginal name: "+file.getOriginalFilename());
		
		if(file!=null)
			System.out.println("File name"+file.getFileNames());
		Criminal crimin=new ObjectMapper().readValue(criminal,Criminal.class);
		System.out.println("Criminal"+criminal);
		
		criminalService.save(crimin,file);
		return new ResponseEntity<Response>(new Response("Criminal Saved Successfully"),HttpStatus.OK);
	}
	
	@GetMapping(value="/criminal")
	public ResponseEntity<CriminalResponse> getImagesByEvent(){
		CriminalResponse res=criminalService.findCriminals();
		//System.out.println("Response of criminal"+res);
		return new ResponseEntity<CriminalResponse>(res,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/criminal/{criminalId}")
	public ResponseEntity<Response> deleteCriminalByEvent(@PathVariable Long criminalId){
		criminalService.deleteCriminalById(criminalId);
		return new ResponseEntity<Response>(new Response("Criminal Deleted"),HttpStatus.OK);
	}
	
	@GetMapping(value="/editCriminal/{criminalId}")
	public ResponseEntity<ImagesByCriminal> editEvent(@PathVariable Long criminalId){
		ImagesByCriminal er=criminalService.findByCriminalId(criminalId);
		return new ResponseEntity<ImagesByCriminal>(er,HttpStatus.OK);
	}
	
}
