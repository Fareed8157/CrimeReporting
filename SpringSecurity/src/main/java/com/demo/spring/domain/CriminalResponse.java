package com.demo.spring.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriminalResponse {

	public CriminalResponse(String message) {
		
	}
	private String message;
	private List<String> images;
	private List<String> fileName;
	private List<String> extensions;
	private List<ImagesByCriminal> listOfCriminals;
}
