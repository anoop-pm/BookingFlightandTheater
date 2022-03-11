package com.example.bookingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "flight_details")
public class FlightDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message="Please Fill FlightName")
	@Column(name = "flight_name", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String flightname;

	@NotNull(message="Please Fill Number of Business Seat")
	@Min(value=1,message="Please Fill Number of Business Seat")
	@Column(name = "business_class_seats", length = 45)
	@ApiModelProperty(notes = "The application-specific Number Of Business class Seats")
	private int businessclassseats;

	@NotNull(message="Please Fill Number of Economic Seat")
	@Min(value=1,message="Please Fill Number of Economic Seat")
	@Column(name = "economy_class_seats", length = 45)
	@ApiModelProperty(notes = "The application-specific Number of Economy class Seats")
	private int economyclassseats;


	@Column(name = "seats", length = 6000)
	@ApiModelProperty(notes = "The application-specific Arranged Seats")
	private String seats;

	@NotNull(message="Please Fill FlightName")
	@Column(name = "dispaturetimestimes", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String dispaturetimes; //change to dispatureTime

	@NotNull(message="Please Fill Duration")
	@Column(name = "duration", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String duration; //change to duration

	@NotEmpty
	@NotNull(message="Please Fill Day")
	@Column(name = "dates", length = 2000)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String dates;

	@NotEmpty
	@Column(name = "source", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String source;

	@NotEmpty
	@Column(name = "destination", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String destination;

	@NotNull(message="please fill Price")
	@Column(name = "price", length = 4000)
	@ApiModelProperty(notes = "The application-specific Flight ID")
	private String price;

	@Column(name = "flight_id", length = 45)
	@ApiModelProperty(notes = "The application-specific Flight ID")
	private int flightid;

	public FlightDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightname() {
		return flightname;
	}

	public void setFlightname(String flightname) {
		this.flightname = flightname;
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

	public String getDispaturetimes() {
		return dispaturetimes;
	}

	public void setDispaturetimes(String dispaturetimes) {
		this.dispaturetimes = dispaturetimes;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getFlightid() {
		return flightid;
	}

	public void setFlightid(int flightid) {
		this.flightid = flightid;
	}


}
