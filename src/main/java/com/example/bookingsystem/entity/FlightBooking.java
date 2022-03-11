package com.example.bookingsystem.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
@Entity
@Table(name = "flight_booking")
public class FlightBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "flight_name", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String flightname;

	@NotNull
	@Min(value=1,message="Please Fill Flight ID")
	@Column(name = "flight_id", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int flightid;

	@NotNull
	@Min(value=1,message="Please Fill User ID")
	@Column(name = "user_id", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int userid;

	@NotEmpty(message="Please Fill Seats")
	@Column(name = "seats", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String seats;

	@Column(name = "amount", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int amount;


	@Column(name = "date", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String date;


	@Column(name = "time", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String time;

	@Column(name = "source", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String source;

	@Column(name = "destination", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String destination;

	@Column(name = "booking_id", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private int bookingid;

	@Column(name = "paymentstatus", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String paymentstatus;

	@Column(name = "bookedtime", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String bookedtime;

	@Column(name = "paymentoption", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String paymentoption;


	@Email
	@Column(name = "mail", length = 200)
	@ApiModelProperty(notes = "The application-specific Flight Name")
	private String mail;

	@NotNull(message="Please Fill Passenger List")
	@OneToMany(targetEntity = Passenger.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "b_fk", referencedColumnName = "id")
	private List<Passenger> passengers;

	public FlightBooking(String flightname, int flightid, int userid, String seats, int amount, String date,
			String time, String source, String destination, int bookingid, String paymentstatus, String bookedtime,
			String paymentoption, String mail, List<Passenger> passengers) {
		super();
		this.flightname = flightname;
		this.flightid = flightid;
		this.userid = userid;
		this.seats = seats;
		this.amount = amount;
		this.date = date;
		this.time = time;
		this.source = source;
		this.destination = destination;
		this.bookingid = bookingid;
		this.paymentstatus = paymentstatus;
		this.bookedtime = bookedtime;
		this.paymentoption = paymentoption;
		this.mail = mail;
		this.passengers = passengers;
	}

	public FlightBooking() {
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

	public int getFlightid() {
		return flightid;
	}

	public void setFlightid(int flightid) {
		this.flightid = flightid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public String getPaymentoption() {
		return paymentoption;
	}

	public void setPaymentoption(String paymentoption) {
		this.paymentoption = paymentoption;
	}

	public String getBookedtime() {
		return bookedtime;
	}

	public void setBookedtime(String bookedtime) {
		this.bookedtime = bookedtime;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
