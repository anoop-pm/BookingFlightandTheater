package com.example.bookingsystem.model;

import java.util.List;

import com.example.bookingsystem.entity.TheatreDetails;

public class TheaterObject {

	private int status;
	private String message;
	private List<TheatreDetails> theatreList;
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
	public List<TheatreDetails> getTheatreList() {
		return theatreList;
	}
	public void setTheatreList(List<TheatreDetails> theatreList) {
		this.theatreList = theatreList;
	}



}
