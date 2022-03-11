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
@Table(name = "theater_booking")
public class TheaterBooking {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message="Please Fill theaterid")
	@Column(name = "theaterid", length = 45)
	@Min(value=1,message="Please Fill theaterid")
	@ApiModelProperty(notes = "The application-specific Age")
	private int theaterid;

	@NotNull(message="Please Fill userid")
	@Column(name = "userid", length = 45)
	@Min(value=1,message="Please Fill userid")
	@ApiModelProperty(notes = "The application-specific userid")
	private int userid;

	@Column(name = "amount", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private int amount;

	@Column(name = "bookingid", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private int bookingid;

	@NotNull(message="Please Fill Seats")
	@Column(name = "seats", length = 4500)
	@ApiModelProperty(notes = "The application-specific Age")
	private String seats;

	@NotNull(message="Please Fill date")
	@Column(name = "date", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private String date;


	@Column(name = "time", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private String time;


	@Column(name = "location", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private String location;

	@Column(name = "paymentoption", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private String paymentoption;

	@Column(name = "cardnumber", length = 45)
	@ApiModelProperty(notes = "The application-specific Age")
	private String cardnumber;




	public TheaterBooking() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getTheaterid() {
		return theaterid;
	}

	public void setTheaterid(int theaterid) {
		this.theaterid = theaterid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}



	public String getPaymentoption() {
		return paymentoption;
	}



	public void setPaymentoption(String paymentoption) {
		this.paymentoption = paymentoption;
	}



	public String getCardnumber() {
		return cardnumber;
	}



	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}



	public int getBookingid() {
		return bookingid;
	}



	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}


}
