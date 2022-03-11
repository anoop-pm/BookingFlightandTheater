package com.example.bookingsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class SearchMovie {

	@NotNull
	@Min(value=1,message="Please fill User ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int userid;

	@NotNull
	private String movie;

	@NotNull
	private String location;

	@NotNull
	private String date;

	@NotNull
	@Min(value=1,message="Please fill Number Of Seats ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int numberofseats;


	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumberofseats() {
		return numberofseats;
	}

	public void setNumberofseats(int numberofseats) {
		this.numberofseats = numberofseats;
	}




}
