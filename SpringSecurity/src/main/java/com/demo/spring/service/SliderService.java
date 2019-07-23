package com.demo.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.model.Slider;
import com.demo.spring.model.UserFiles;

public interface SliderService {

	Slider save(Slider slider,MultipartHttpServletRequest httpfile);
	EventResponse findFilesByStatus(boolean status);
	List<UserFiles> findFilesBySliderId(Long userId);
	void deleteImagesByEvent(Long eventId);
	ImagesByEvent findByEventId(Long eventId);
}
