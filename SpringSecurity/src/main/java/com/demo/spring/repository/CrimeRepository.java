package com.demo.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.CrimeTypes;

@Repository
public interface CrimeRepository extends CrudRepository<CrimeTypes, Long>{

}
