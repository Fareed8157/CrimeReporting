package com.demo.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user")
@Data
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 946505242996496668L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="district")
	private String district;
	@Column(name="address")
	private String address;
	@Column(name="father_name")
	private String fatherName;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="nic")
	private String nic;
	@Column(name="enabled")
	private boolean enabled;
	@Column(name="role")
	private String role;
	@Column(name="phoneNumber")
	private String phoneNumber;
	@Column(name="created_date")
	private Date createdDate;
	@Column(name="2fa_code")
	private String twofaCode;
	@Column(name="2fa_expire_time")
	private Long twofaExpireTime;
	
	
	
}
