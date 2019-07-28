package com.demo.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Fir implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2935557834507001991L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	@Column(name="police_station_id")
	private Long policeStationId;
	@Column(name="crime_id")
	private Long crimeId;
	private boolean enabled;
	@ManyToOne
	@JoinColumn(name="criminal_id")
	private Criminal criminal;
	
	@JsonIgnore
	@Transient 
	private List<MultipartFile> files=new ArrayList<MultipartFile>();
}
