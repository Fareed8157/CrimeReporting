package com.demo.spring.domain;

import java.util.List;

import com.demo.spring.model.Criminal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesByCriminal {

	private Criminal criminal;
	private List<String> images;
	private List<String> fileName;
}
