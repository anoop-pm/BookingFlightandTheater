package com.example.bookingsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class SearchFlightTickets {


	@NotNull
	@Min(value=1,message="Please fill Booking ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int bookingid;

	@NotNull
	@Min(value=1,message="Please fill User ID")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int userid;

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}


}
