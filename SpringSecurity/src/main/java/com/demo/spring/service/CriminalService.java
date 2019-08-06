package com.demo.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.domain.CriminalResponse;
import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByCriminal;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.model.Criminal;
import com.demo.spring.model.CriminalFiles;

public interface CriminalService {


	Criminal save(Criminal crimin, MultipartHttpServletRequest file);

	CriminalResponse findCriminals();
	public List<CriminalFiles> findFilesByCriminalId(Long criminalId);

	void deleteCriminalById(Long criminalId);
	ImagesByCriminal findByCriminalId(Long criminalId);
}
