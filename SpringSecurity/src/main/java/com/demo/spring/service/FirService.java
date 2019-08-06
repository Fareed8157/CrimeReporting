package com.demo.spring.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.model.Fir;

public interface FirService {

	void saveFir(Fir fir, MultipartHttpServletRequest file);
	
}
