package com.demo.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@Column(name="date_of_crime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateOfCrime;
	private String description;
	
	@JsonIgnore
	@Column(name="fir_submitted_date")
	private Date firSubmittedDate;
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name="crime_id")
	private CrimeTypes crimeType;
	
	@ManyToOne
	@JoinColumn(name="criminal_id")
	private Criminal criminal;
	
	@ManyToOne
	@JoinColumn(name="police_station_id")
	private PoliceStation policeStation;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@JsonIgnore
	@Transient 
	private List<MultipartFile> files=new ArrayList<MultipartFile>();
}
