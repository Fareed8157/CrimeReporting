package com.demo.spring.service;

import java.util.List;

import com.demo.spring.model.CrimeTypes;

public interface CrimeService {

	void save(CrimeTypes crime);

	List<CrimeTypes> findAll();

}
