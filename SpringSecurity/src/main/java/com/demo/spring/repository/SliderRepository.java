package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.spring.model.Slider;

public interface SliderRepository extends CrudRepository<Slider, Long>{
	@Query("select s from Slider as s where s.enabled=?1")
	List<Slider> findSliderByStatus(boolean status);
}
