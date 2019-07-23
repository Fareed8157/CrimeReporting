package com.demo.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class DeadBodiesFiles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4043413791749650436L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="file_name")
	private String fileName;
	@Column(name="modified_file_name")
	private String modifiedFileName;
	@Column(name="file_extension")
	private String fileExtension;
	
	@ManyToOne
	@JoinColumn(name="deadBodies_id")
	private DeadBodies deadBodies;

}
