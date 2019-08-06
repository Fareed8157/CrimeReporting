package com.demo.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.spring.model.Criminal;

public interface CriminalRepository extends CrudRepository<Criminal, Long> {

}
