package com.example.bookingsystem.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateFlight {


	@NotNull
	@Min(value=1,message="Please Fill Message")
	private int flight_id;
	@NotNull(message="Please Fill Price Economy and Business")
	private Price price;
	@NotNull(message="Please Enter Valid Time")
	private String time;
	@NotNull(message="Please Fill Day OF Week")
	private String dates;

	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public int getFlight_id() {
		return flight_id;
	}
	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}



}
