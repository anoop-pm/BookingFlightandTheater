package com.example.bookingsystem.dto;

import com.example.bookingsystem.entity.FlightBooking;

import lombok.ToString;

@ToString
public class FlightBookingDto {

	private FlightBooking bookingFlights;

	public FlightBookingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FlightBookingDto(FlightBooking bookingFlights) {
		super();
		this.bookingFlights = bookingFlights;
	}

	public FlightBooking getBookingFlights() {
		return bookingFlights;
	}

	public void setBookingFlights(FlightBooking bookingFlights) {
		this.bookingFlights = bookingFlights;
	}

}
