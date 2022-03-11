package com.example.bookingsystem.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class TheaterDelete {

	@NotNull
	@Min(value=1,message="Please Fill Theater ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int theaterid;

	public int getTheaterid() {
		return theaterid;
	}

	public void setTheaterid(int theaterid) {
		this.theaterid = theaterid;
	}




}
