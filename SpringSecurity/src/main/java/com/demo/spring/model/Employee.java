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
	@Column(name="police_station_id")
	private Long policeStationId;
	public Long getPoliceStationId() {
		return policeStationId;
	}
	public void setPoliceStationId(Long policeStationId) {
		this.policeStationId = policeStationId;
	}
	

	
	
}
