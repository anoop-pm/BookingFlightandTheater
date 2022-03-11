package com.example.bookingsystem.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingsystem.dto.CancelBooking;
import com.example.bookingsystem.dto.SearchFlightTickets;
import com.example.bookingsystem.dto.SearchMovie;
import com.example.bookingsystem.entity.FlightBooking;
import com.example.bookingsystem.entity.SearchFlight;
import com.example.bookingsystem.entity.TheaterBooking;
import com.example.bookingsystem.entity.TransactionStatus;
import com.example.bookingsystem.entity.User;
import com.example.bookingsystem.model.BookingObject;
import com.example.bookingsystem.model.FlightObject;
import com.example.bookingsystem.model.ResponseObject;
import com.example.bookingsystem.model.TheaterBookingObject;
import com.example.bookingsystem.model.TheaterObject;
import com.example.bookingsystem.repository.BookingFlightRepository;
import com.example.bookingsystem.repository.FlightRepository;
import com.example.bookingsystem.service.BookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@RestController
@RequestMapping("/api/v2")
@Api(value = "Ticket Booking Solutions", description = "Book Tickets")
public class BookingUserController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BookingFlightRepository bookingFlight;

	@PostMapping("/registration")
	@ApiOperation(value = "Register A User")
	public ResponseEntity<ResponseObject> createUser(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody User user) throws SQLException {

		ResponseObject savedUser = bookingService.addUser(user);
		return new ResponseEntity<ResponseObject>(savedUser, HttpStatus.ACCEPTED);
	}

	@GetMapping("/bookdetail")
	public ResponseEntity<BookingObject> bookDetails(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody SearchFlightTickets userbookings) throws SQLException {
		BookingObject abc=bookingService.bookTicketDetails(userbookings);
		return new ResponseEntity<BookingObject>(abc, HttpStatus.ACCEPTED);
	}


	@GetMapping("/movieDetails")
	public ResponseEntity<TheaterObject> theatrelist(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody SearchMovie theater) throws SQLException {
		TheaterObject abc=bookingService.findTheater(theater);
		return new ResponseEntity<TheaterObject>(abc, HttpStatus.ACCEPTED);
	}


	@PostMapping("/bookflight")
	@ApiOperation(value = "Add book flight")
	public ResponseEntity<ResponseObject> addBookTickets(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody FlightBooking book)
					throws SQLException {

		ResponseObject bookConfirm = bookingService.bookTickets(book);
		return new ResponseEntity<ResponseObject>(bookConfirm, HttpStatus.ACCEPTED);
	}

	@PutMapping("/bookflightpayment")
	@ApiOperation(value = "Add book flight Payment")
	public ResponseEntity<ResponseObject> flightConfirm(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody TransactionStatus book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.bookTicketsConfirm(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}

	@PutMapping("/cancelbookflight")
	@ApiOperation(value = "Add book flight Payment")
	public ResponseEntity<ResponseObject> cancelTickets(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody CancelBooking book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.cancelFlightTicket(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}

	@GetMapping("/findflight")
	@ApiOperation(value = "Add find flight")
	public ResponseEntity<FlightObject> findFlights(
			@ApiParam(value = "Add Cancel Object object store in database table", required = true) @Valid @RequestBody SearchFlight flight)
					throws SQLException {
		FlightObject flightsDetails = bookingService.findFlights(flight);
		return new ResponseEntity<FlightObject>(flightsDetails, HttpStatus.ACCEPTED);
	}

	@PostMapping("/bookTheater")
	public ResponseEntity<ResponseObject> bookingTheater(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody TheaterBooking theater) throws SQLException {
		ResponseObject abc=bookingService.bookTheaterTickets(theater);
		return new ResponseEntity<ResponseObject>(abc, HttpStatus.ACCEPTED);
	}


	@PutMapping("/cancelbookTheater")
	@ApiOperation(value = "Add book flight Payment")
	public ResponseEntity<ResponseObject> cancelTheaterTickets(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody CancelBooking book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.cancelTheaterTicket(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}


	@PutMapping("/booktheaterpayment")
	@ApiOperation(value = "Add book flight Payment")
	public ResponseEntity<ResponseObject> theaterConfirm(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody TransactionStatus book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.bookTheaterTicketsConfirm(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}



	@GetMapping("/booktheaterDetails")
	public ResponseEntity<TheaterBookingObject> booktheaterDetails(
			@ApiParam(value = "Register User object store in database table", required = true) @Valid @RequestBody SearchFlightTickets userbookings) throws SQLException {
		TheaterBookingObject abc=bookingService.bookTheaterTicketDetails(userbookings);
		return new ResponseEntity<TheaterBookingObject>(abc, HttpStatus.ACCEPTED);
	}


}
