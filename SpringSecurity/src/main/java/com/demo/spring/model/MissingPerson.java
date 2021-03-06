package com.demo.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class MissingPerson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -282106142408358980L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	
	@JsonIgnore
	@Transient 
	private List<MultipartFile> files=new ArrayList<MultipartFile>();
	
}
