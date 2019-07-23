package com.demo.spring.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_files")
@Data
@NoArgsConstructor
public class UserFiles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 88339418335369182L;
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
	@JoinColumn(name="slider_id")
	private Slider slider;
}
