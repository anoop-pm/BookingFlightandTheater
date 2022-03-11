package com.example.bookingsystem.dto;

import java.io.Serializable;

import com.example.bookingsystem.entity.CancelationOffer;

import lombok.ToString;



@ToString
public class CancelationChargeDto implements Serializable {

	private int flightid ;

	private CancelationOffer cancelOffer;

	public CancelationChargeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CancelationChargeDto(int flightid, CancelationOffer cancelOffer) {
		super();
		this.flightid = flightid;
		this.cancelOffer = cancelOffer;
	}

	public int getFlightid() {
		return flightid;
	}

	public void setFlightid(int flightid) {
		this.flightid = flightid;
	}

	public CancelationOffer getCancelOffer() {
		return cancelOffer;
	}

	public void setCancelOffer(CancelationOffer cancelOffer) {
		this.cancelOffer = cancelOffer;
	}


}
