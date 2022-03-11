package com.example.bookingsystem.model;

import java.util.List;

import com.example.bookingsystem.entity.TheaterBooking;

public class TheaterBookingObject {


	private int status;
	private String message;
	private List<TheaterBooking> theatreList;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TheaterBooking> getTheatreList() {
		return theatreList;
	}
	public void setTheatreList(List<TheaterBooking> theatreList) {
		this.theatreList = theatreList;
	}


}
