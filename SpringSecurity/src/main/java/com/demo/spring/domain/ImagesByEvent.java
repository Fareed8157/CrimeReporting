package com.demo.spring.domain;

import java.util.List;

import com.demo.spring.model.Slider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesByEvent {

	private Slider slider;
	private List<String> images;
	private List<String> fileName;
}
