package com.example.bookingsystem.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class SearchFlight {


	@NotEmpty(message="PLease Fill Destination")
	@ApiModelProperty(notes = "The application-specific Address")
	private String destination;

	@NotEmpty(message="PLease Fill source")
	@ApiModelProperty(notes = "The application-specific Address")
	private String source;

	@NotEmpty(message="PLease Fill Valid Date format=DD/MM/YEAR")
	@ApiModelProperty(notes = "The application-specific Address")
	private String dates;

	@NotEmpty(message="PLease Fill Number of Seats")
	@ApiModelProperty(notes = "The application-specific Address")
	private String seats;

	@NotNull
	@Min(value=1,message="PLease Fill User ID")
	@ApiModelProperty(notes = "The application-specific Address")
	private int userid;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}



}
