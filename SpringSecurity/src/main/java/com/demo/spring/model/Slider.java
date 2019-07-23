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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="slider")
@Data
public class Slider implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407383581967931873L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	@JsonIgnore
	@Column(name="enabled")
	private boolean enabled;
	@JsonIgnore
	@Transient 
	private List<MultipartFile> files=new ArrayList<MultipartFile>();
	@Transient 
	private List<String> removeImages=new ArrayList<String>();
	
}
