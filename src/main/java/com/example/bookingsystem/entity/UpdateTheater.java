package com.example.bookingsystem.entity;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class UpdateTheater {

	@NotNull
	@Min(value=1,message="Please Fill Theater ID")
	@Column(name = "theater_id", length = 45)
	@ApiModelProperty(notes = "The application-specific Flight ID")
	private int theaterid;

	@NotNull
	@Column(name = "fromdate", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String fromdate;

	@NotNull
	@Column(name = "todate", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String todate;

	@NotNull
	@Column(name = "moviename", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String moviename;

	@NotNull(message="Please Fill Price Economy and Business")
	private Price price;

	public int getTheaterid() {
		return theaterid;
	}

	public void setTheaterid(int theaterid) {
		this.theaterid = theaterid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getMoviename() {
		return moviename;
	}

	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}



}
