package com.example.bookingsystem.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CancelationOffer {



	@NotNull
	@Min(value = 1, message = "Please Fill systemid")
	private int systemid;

	@NotNull(message = "Please Fill systemName")
	private String booking_system;

	@NotNull
	@Min(value = 1, message = "Please Fill within4hr")
	private int within4hr;

	@NotNull
	@Min(value = 1, message = "Please Fill after4hr")
	private int after4hr;

	@NotNull
	@Min(value = 1, message = "Please Fill cancelationcharge")
	private int cancelationcharge;

	@NotNull
	@Min(value = 1, message = "Please Fill offers")
	private int offers;

	public CancelationOffer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSystemid() {
		return systemid;
	}

	public void setSystemid(int systemid) {
		this.systemid = systemid;
	}

	public String getBooking_system() {
		return booking_system;
	}

	public void setBooking_system(String booking_system) {
		this.booking_system = booking_system;
	}

	public int getWithin4hr() {
		return within4hr;
	}

	public void setWithin4hr(int within4hr) {
		this.within4hr = within4hr;
	}

	public int getAfter4hr() {
		return after4hr;
	}

	public void setAfter4hr(int after4hr) {
		this.after4hr = after4hr;
	}

	public int getCancelationcharge() {
		return cancelationcharge;
	}

	public void setCancelationcharge(int cancelationcharge) {
		this.cancelationcharge = cancelationcharge;
	}

	public int getOffers() {
		return offers;
	}

	public void setOffers(int offers) {
		this.offers = offers;
	}

}