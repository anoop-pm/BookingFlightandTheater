package com.example.bookingsystem.model;

import com.example.bookingsystem.dto.BookDetails;

public class BookingObject {

	private int status;
	private String message;
	private BookDetails bookdetails;
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
	public BookDetails getBookdetails() {
		return bookdetails;
	}
	public void setBookdetails(BookDetails bookdetails) {
		this.bookdetails = bookdetails;
	}


}
