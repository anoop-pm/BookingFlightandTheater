package com.example.bookingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "cancelation_rules")
public class CancelationRules {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "system_id", length = 200)
	@ApiModelProperty(notes = "The application-specific System Id")
	private int systemid;

	@NotEmpty
	@Column(name = "booking_system", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String bookingsystem;

	@NotEmpty
	@Column(name = "rules", length = 5000)
	@ApiModelProperty(notes = "The application-specific Booking System name")
	private String rules;

	@Column(name = "time", length = 50)
	@ApiModelProperty(notes = "The application-specific Booking System name")
	private String time;

	public CancelationRules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSystemid() {
		return systemid;
	}

	public void setSystemid(int systemid) {
		this.systemid = systemid;
	}

	public String getBookingsystem() {
		return bookingsystem;
	}

	public void setBookingsystem(String bookingsystem) {
		this.bookingsystem = bookingsystem;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
