package com.demo.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.Fir;

@Repository
public interface FirRepository extends CrudRepository<Fir, Long>{

}
