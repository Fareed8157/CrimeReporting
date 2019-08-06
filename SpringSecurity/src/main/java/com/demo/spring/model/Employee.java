package com.demo.spring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
public class Employee extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784105343659158615L;
	@JoinColumn(name="police_station_id")
	@ManyToOne
	private PoliceStation policeStation;
	public PoliceStation getPoliceStation() {
		return policeStation;
	}
	public void setPoliceStation(PoliceStation policeStation) {
		this.policeStation = policeStation;
	}
	
	
	
	
}
