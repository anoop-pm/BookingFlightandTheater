package com.example.bookingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "theatre_details")
public class TheatreDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "theatre_name", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String theatrename;

	@NotNull
	@Min(value=1,message="Please Enter Business Class Seat")
	@Column(name = "business_class_seats", length = 45)
	@ApiModelProperty(notes = "The application-specific Number Of Business class Seats")
	private int businessclassseats;

	@NotNull
	@Min(value=1,message="Please Enter Economic Class Seat")
	@Column(name = "economy_class_seats", length = 45)
	@ApiModelProperty(notes = "The application-specific Number of Economy class Seats")
	private int economyclassseats;

	@Column(name = "seats", length = 6000)
	@ApiModelProperty(notes = "The application-specific Arranged Seats")
	private String seats;

	@NotNull
	@Column(name = "times", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String times;

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

	@NotNull
	@Column(name = "location", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String location;

	@NotNull
	@Column(name = "price", length = 4000)
	@ApiModelProperty(notes = "The application-specific Flight ID")
	private String price;

	@Column(name = "theater_id", length = 45)
	@ApiModelProperty(notes = "The application-specific Flight ID")
	private int theaterid;

	public TheatreDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTheatrename() {
		return theatrename;
	}

	public void setTheatrename(String theatrename) {
		this.theatrename = theatrename;
	}

	public int getBusinessclassseats() {
		return businessclassseats;
	}

	public void setBusinessclassseats(int businessclassseats) {
		this.businessclassseats = businessclassseats;
	}

	public int getEconomyclassseats() {
		return economyclassseats;
	}

	public void setEconomyclassseats(int economyclassseats) {
		this.economyclassseats = economyclassseats;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getTheaterid() {
		return theaterid;
	}

	public void setTheaterid(int theaterid) {
		this.theaterid = theaterid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
