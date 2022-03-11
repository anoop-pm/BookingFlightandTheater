package com.example.bookingsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class FlightDelete {

	@NotNull
	@Min(value=1,message="Please Fill Flight ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int flightid;

	public FlightDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getFlightid() {
		return flightid;
	}

	public void setFlightid(int flightid) {
		this.flightid = flightid;
	}



}
