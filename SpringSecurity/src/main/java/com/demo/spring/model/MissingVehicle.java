package com.demo.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MissingVehicle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8759229460685829317L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String description;
	@Column(name="police_Theaf")
	private boolean policeTheaf;

}
