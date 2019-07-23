package com.demo.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.PoliceStation;



@Repository
public interface PoliceStationRepository extends  CrudRepository<PoliceStation, Long> {

}
