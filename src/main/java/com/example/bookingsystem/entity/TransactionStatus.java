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
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "transactions")
public class TransactionStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "transferstatus", length = 200)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String transferstatus; // book or cancel


	@Column(name = "bookingsystem", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String bookingsystem;

	@NotEmpty
	@Column(name = "paymentoption", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String paymentoption; // bank or upi or credictcard


	@Column(name = "cardnumber", length = 200)
	@NotEmpty
	@Size(min = 12, max = 14, message = "CardNumber should have  12 to 14 characters")
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String cardnumber;

	@NotEmpty
	@Column(name = "validthrough", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String validthrough;


	@NotNull
	@Min(value=1,message="Please fill CVV")
	@Column(name = "cvv", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private int cvv;

	@Column(name = "transactionid", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String transactionid; // selected option id

	@NotNull
	@Min(value=1,message="Please fill Booking ID")
	@Column(name = "bookingid", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private int bookingid; // selected option id

	@NotNull
	@Column(name = "amount", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private int amount; // selected option id

	@NotNull
	@Column(name = "userid", length = 200)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private int userid;

	public TransactionStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransferstatus() {
		return transferstatus;
	}

	public void setTransferstatus(String transferstatus) {
		this.transferstatus = transferstatus;
	}

	public String getPaymentoption() {
		return paymentoption;
	}

	public void setPaymentoption(String paymentoption) {
		this.paymentoption = paymentoption;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getValidthrough() {
		return validthrough;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getBookingsystem() {
		return bookingsystem;
	}

	public void setBookingsystem(String bookingsystem) {
		this.bookingsystem = bookingsystem;
	}



}
