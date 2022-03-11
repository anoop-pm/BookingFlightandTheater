package com.example.bookingsystem.model;

import java.util.List;

import com.example.bookingsystem.entity.FlightDetails;

public class FlightObject {

	private int status;
	private String message;
	private List<FlightDetails> flightList;
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
	public List<FlightDetails> getFlightList() {
		return flightList;
	}
	public void setFlightList(List<FlightDetails> flightList) {
		this.flightList = flightList;
	}


}
