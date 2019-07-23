package com.demo.spring.model;

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
public class CriminalFiles {

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
	@JoinColumn(name="criminal_id")
	private Criminal criminals;
	
	
}
