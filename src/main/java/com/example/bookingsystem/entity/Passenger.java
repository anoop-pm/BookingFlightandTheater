package com.example.bookingsystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;



@ToString
@Entity
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ApiModelProperty(notes = "The application-specific User Name")
	private String name;

	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String age;

	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String uniqueId;


	public Passenger() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Passenger(String name, String age, String uniqueId) {
		super();
		this.name = name;
		this.age = age;
		this.uniqueId = uniqueId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}



}
