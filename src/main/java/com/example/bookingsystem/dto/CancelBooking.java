package com.example.bookingsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CancelBooking {

	@NotNull
	@Min(value = 1, message = "Booking id needed")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int bookingid;

	@Min(value = 1, message = "User id needed")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int userid;

	@NotEmpty(message = "please enter seats")
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String seats;


	public CancelBooking() {
		super();
		// TODO Auto-generated constructor stub
	}
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

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}



}
