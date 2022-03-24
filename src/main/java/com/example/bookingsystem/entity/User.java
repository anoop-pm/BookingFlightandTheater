package com.example.bookingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "name", length = 200)
	@ApiModelProperty(notes = "The application-specific User Name")
	private String name;

	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_of_birth", length = 45)
	@ApiModelProperty(notes = "The application-specific Date of Birth")
	private String dateofbirth;

	@NotNull
	@Column(name = "age", length = 45)
	@Min(value=1,message="Please Fill Age")
	@Max(value=100,message="Please Fill Age")
	@ApiModelProperty(notes = "The application-specific Age")
	private int age;

	@NotEmpty
	@Column(name = "address", length = 200)
	@ApiModelProperty(notes = "The application-specific Address")
	private String address;

	@Column(length = 45)
	@NotEmpty
	@Email
	@ApiModelProperty(notes = "The application-specific Email")
	private String email;

	@NotNull
	@Column(name = "userid", length = 45)
	@ApiModelProperty(notes = "The application-specific Auto Generated UserID")
	private int userid;

	@Column(length = 45)
	@NotEmpty
	@Size(min = 10, max = 10, message = "phonenumber should have  10 characters")
	private String phonenumber;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

}
