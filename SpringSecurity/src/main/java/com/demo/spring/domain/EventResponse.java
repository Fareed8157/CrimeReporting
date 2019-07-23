package com.demo.spring.domain;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3394577917281896787L;

	public EventResponse(String message) {
		
	}
	private String message;
	private List<String> images;
	private List<String> fileName;
	private List<String> extensions;
	private List<ImagesByEvent> listOfEvents;
}
