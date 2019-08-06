package com.demo.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.domain.Response;
import com.demo.spring.model.Slider;
import com.demo.spring.repository.SliderRepository;
import com.demo.spring.service.SliderService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SliderController {

	ObjectMapper obm=new ObjectMapper();
	
	@Autowired private SliderService sliderService;
	@Autowired ServletContext context;
	private FileInputStream fileInputStream;
	
	@PostMapping(value="/slider",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<EventResponse> saveSlider(MultipartHttpServletRequest file ,@RequestParam(required=false,value="slider") String slider) throws JsonParseException, JsonMappingException, IOException{
		Slider slider1=new ObjectMapper().readValue(slider,Slider.class);
		sliderService.save(slider1,file);
		if(file!=null)
			System.out.println("File name");
		//System.out.println("Slider"+slider1);
		System.out.println("Inside save slider");
		return new ResponseEntity<EventResponse>(new EventResponse("Slider saved successfully"),HttpStatus.OK);
	}
	
	
	@PutMapping(value="/slider",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<EventResponse> updateSlider(MultipartHttpServletRequest file ,@RequestParam(required=false,value="slider") String slider) throws JsonParseException, JsonMappingException, IOException{
		Slider slider1=new ObjectMapper().readValue(slider,Slider.class);
		sliderService.save(slider1,file);
		//if(file!=null)
			System.out.println("File name");
		System.out.println("Slider"+slider1);
		System.out.println("Inside update slider");
		return new ResponseEntity<EventResponse>(new EventResponse(""),HttpStatus.OK);
	}
	
	
	@GetMapping(value="/gallary")
	public ResponseEntity<EventResponse> getImages(){
		EventResponse res=new EventResponse();
		List<String> images=new ArrayList<String>();
		List<String> imagesNames=new ArrayList<String>();
		List<String> extensions=new ArrayList<String>();
		String filePath=context.getRealPath("/images");
		File fileFolder=new File(filePath);
		if(fileFolder!=null) {
			for(final File file:fileFolder.listFiles()) {
				if(!file.isDirectory()) {
					String encodeBase64=null;
					try {
						String extension=FilenameUtils.getExtension(file.getName());
						String fileName=file.getName();
						fileInputStream = new FileInputStream(file);
						byte[] bytes=new byte[(int)file.length()];
						fileInputStream.read(bytes);
						encodeBase64=Base64.getEncoder().encodeToString(bytes);
						System.out.println("File names with extension"+fileName+"=="+extension);
						if(extension.equalsIgnoreCase("JPG") || extension.equalsIgnoreCase("png"))
							images.add("data:image/"+extension+";base64,"+encodeBase64);
						else {
							images.add("fileName:"+fileName+":extension:"+extension+"");
						}
						imagesNames.add(fileName);
						extensions.add(extension);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			res.setImages(images);
			res.setFileName(imagesNames);
			res.setExtensions(extensions);
		}
		return new ResponseEntity<EventResponse>(res,HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/slider/{eventId}")
	public ResponseEntity<Response> deleteImagesByEvent(@PathVariable Long eventId){
		sliderService.deleteImagesByEvent(eventId);
		return new ResponseEntity<Response>(new Response("File Deleted"),HttpStatus.OK);
	}
	
	@GetMapping(value="/getImagesByEvent")
	public ResponseEntity<EventResponse> getImagesByEvent(){
		EventResponse res=sliderService.findFilesByStatus(true);
		return new ResponseEntity<EventResponse>(res,HttpStatus.OK);
	}
	
	@GetMapping(value="/editEvent/{eventId}")
	public ResponseEntity<ImagesByEvent> editEvent(@PathVariable Long eventId){
		ImagesByEvent er=sliderService.findByEventId(eventId);
		return new ResponseEntity<ImagesByEvent>(er,HttpStatus.OK);
	}
}
