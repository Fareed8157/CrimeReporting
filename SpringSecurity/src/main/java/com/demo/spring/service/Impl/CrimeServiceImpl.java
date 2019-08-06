package com.demo.spring.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.model.CrimeTypes;
import com.demo.spring.repository.CrimeRepository;
import com.demo.spring.service.CrimeService;

@Service
public class CrimeServiceImpl implements CrimeService{

	@Autowired CrimeRepository crimeRepository;
	@Override
	public void save(CrimeTypes crime) {
		crimeRepository.save(crime);
	}
	@Override
	public List<CrimeTypes> findAll() {
		// TODO Auto-generated method stub
		return (List<CrimeTypes>)crimeRepository.findAll();
	}

}
