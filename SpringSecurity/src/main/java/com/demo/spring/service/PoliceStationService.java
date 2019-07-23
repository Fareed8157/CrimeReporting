package com.demo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.model.PoliceStation;
import com.demo.spring.repository.PoliceStationRepository;


@Service
public class PoliceStationService {

	@Autowired PoliceStationRepository policeStationRepository;
	
	public void savePoliceStation(PoliceStation station) {
		policeStationRepository.save(station);
	}
}
