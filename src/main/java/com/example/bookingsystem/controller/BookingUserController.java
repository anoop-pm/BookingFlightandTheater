package com.example.bookingsystem.controller;

import java.sql.SQLException;
import java.util.List;

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

import com.example.bookingsystem.constant.BookingConstant;
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



	/**
	 * Register A User for store user details identifying email and phone so that
	 * user must follow unique id and phone number in this service identifying
	 * unique phone number and email maxid means get maximum id from the table its
	 * used for generated unique ID email and phone variable for identify unique
	 * value then check the Database Connection then save the User
	 *
	 * INPUT as Json
	 *
	 * { "name":"Arun", "dateofbirth":"08-08-1995", "age":30, "address":"parayil",
	 * "email":"ano4opp@gmail.com", "phonenumber":"8897512866" }
	 *
	 *
	 */
	@PostMapping("/registration")
	@ApiOperation(value = BookingConstant.USERREGISTER)
	public ResponseEntity<ResponseObject> createUser(
			@ApiParam(value = BookingConstant.PARAMDOCUPDATEUSER, required = true) @Valid @RequestBody User user) throws SQLException {

		ResponseObject savedUser = bookingService.addUser(user);
		return new ResponseEntity<ResponseObject>(savedUser, HttpStatus.ACCEPTED);
	}

	/** Doc */
	@GetMapping("/bookdetail")
	public ResponseEntity<BookingObject> bookDetails(
			@ApiParam(value = BookingConstant.PARAMDOCSHOWBOOKFLIGHT, required = true) @Valid @RequestBody SearchFlightTickets userbookings) throws SQLException {
		BookingObject bookedFlights=bookingService.bookTicketDetails(userbookings); //changes
		return new ResponseEntity<BookingObject>(bookedFlights, HttpStatus.ACCEPTED);
	}


	@GetMapping("/movieDetails")
	public ResponseEntity<TheaterObject> theatrelist(
			@ApiParam(value = BookingConstant.PARAMDOCSHOWBOOKTheater, required = true) @Valid @RequestBody SearchMovie theater) throws SQLException {
		TheaterObject searchTheater=bookingService.findTheater(theater);
		return new ResponseEntity<TheaterObject>(searchTheater, HttpStatus.ACCEPTED);
	}


	/**
	 * To Booking Flight
	 *
	 * This Service used for booking Flight Tickets with given flight id with Valid
	 * Seat List
	 *
	 *
	 * @Value used TransactionStatus.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *
	 *         Input{ "flightid": 15458, "passengers": [ { "name": "jayasree1",
	 *         "age": "23", "uniqueId": "c112347854" }, { "name": "priyanka1",
	 *         "age": "22", "uniqueId": "c23545434" },{ "name": "jayasree2", "age":
	 *         "23", "uniqueId": "c112347854" }, { "name": "priyanka2", "age": "22",
	 *         "uniqueId": "c23545434" } ], "seats":
	 *         "{economic:[1,3],business:[1,3]}", "mail": "anoopmohan08@gmail.com",
	 *         "userid": 2507 }
	 *
	 */
	@PostMapping("/bookflight")
	public ResponseEntity<ResponseObject> addBookTickets(
			@ApiParam(value = BookingConstant.ADDFLIGHTDOC, required = true) @Valid @RequestBody FlightBooking book)
					throws SQLException {

		ResponseObject bookConfirm = bookingService.bookTickets(book);
		return new ResponseEntity<ResponseObject>(bookConfirm, HttpStatus.ACCEPTED);
	}


	/**
	 * Confirm Booking
	 *
	 * This Service used for confirm our booking with payment After Payment user and
	 * given mail id Got Confirm Ticket And Status Change to Payed
	 *
	 *
	 * @Value used TransactionStatus.class for Request Entity
	 * @return ResponseObject This status Code With Message Value. Input {
	 *         "bookingid":28837, "paymentoption":"UPI" }
	 *
	 */

	@PutMapping("/bookflightpayment")
	public ResponseEntity<ResponseObject> flightConfirm(
			@ApiParam(value = BookingConstant.ADDTHEATERTDOC, required = true) @Valid @RequestBody TransactionStatus book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.bookTicketsConfirm(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}

	/**
	 *
	 * CancelFlightTicket Service Used for cancel Tickets After validation Service
	 * Cancel Given Seats Here Remove Method is used for cancel tickets remove given
	 * seats from booked Tickets and add to Flight seat list
	 *
	 * After cancellation User Got confirmation ,
	 *
	 * @Value used CancelBooking.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input{ "bookingid": 28837, "seats": "{economic:[5],business:[5]}" }
	 *
	 *
	 *         Different Generic method is used like
	 *         Search,addseat,removeseat,price,count All generic method are defined
	 *         in Utilities Package
	 *
	 */
	@PutMapping("/cancelbookflight")
	public ResponseEntity<ResponseObject> cancelTickets(
			@ApiParam(value = "Confirm Payment", required = true) @Valid @RequestBody CancelBooking book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.cancelFlightTicket(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}

	/**
	 * Find Flight
	 *
	 * All flights Are arranged by weekdays
	 *
	 * Here find weekday from given date then Search Flights With given passenger
	 * count ,Destination and Source
	 *
	 * Input
	 *
	 * { "source":"Nedumbasserry", "destination":"Dubai", "dates": "03/03/2022",
	 * "seats":"4"
	 *
	 * }
	 *
	 */
	@GetMapping("/findflight")
	@ApiOperation(value = " find flight")
	public ResponseEntity<FlightObject> findFlights(
			@ApiParam(value =BookingConstant.PARAMDOCSHOWFLIGHT, required = true) @Valid @RequestBody SearchFlight flight)
					throws SQLException {
		FlightObject flightsDetails = bookingService.findFlights(flight);
		return new ResponseEntity<FlightObject>(flightsDetails, HttpStatus.ACCEPTED);
	}

	/**
	 * Book Tickets
	 *
	 * After Seatching Flight application use this api for book Tickets
	 *
	 * Different Validation Happen here for Given data is valuevable for book
	 * Tickets like flight id is currect and given seats are available or not ETC
	 *
	 * Here 2 Entity Class used Flight booking and Passenger
	 *
	 * Passenger is mapped with list<passenger> in Flightbooking
	 *
	 * This passenger List used for Store Passenger
	 *
	 * This api also used for blocking Tickets
	 *
	 * Input
	 *
	 * { "flightid": 22417, "passengers": [ { "name": "jayasree", "age": "23",
	 * "uniqueId": "c11234785" }, { "name": "priyanka", "age": "22", "uniqueId":
	 * "c23545" } ], "seats": "{economic:[2],business:[2]}", "mail":
	 * "anoopmohan08@gmail.com", "userid": 1473 } after hit the request send a mail
	 * to given and user mail id
	 *
	 * *Differnt Generic method is used like Search,addseat,removeseat,price,count
	 * All generic method are defined in Utilities Package
	 *
	 */
	@PostMapping("/bookTheater")
	public ResponseEntity<ResponseObject> bookingTheater(
			@ApiParam(value = BookingConstant.PARAMDOCBOOKTheater, required = true) @Valid @RequestBody TheaterBooking theater) throws SQLException {
		ResponseObject theaterBooking=bookingService.bookTheaterTickets(theater);//Changes//
		return new ResponseEntity<ResponseObject>(theaterBooking, HttpStatus.ACCEPTED);
	}


	/**
	 * This Service method is used to Cancel theater Ticket With Booked ID.
	 *
	 * This service for update theater seats,and booked Seats and calculate the
	 * cancelation fee After Processing This cancellation fee send user account
	 *
	 * @Value used CancelBooking.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@PutMapping("/cancelbookTheater")
	public ResponseEntity<ResponseObject> cancelTheaterTickets(
			@ApiParam(value = BookingConstant.PARAMDOCCANCELTheater, required = true) @Valid @RequestBody CancelBooking book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.cancelTheaterTicket(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}


	/**
	 * This Service method is used to Confirm BookedTheater With theater Booking ID.
	 *
	 * This Method is Update PaymentStatus To pending to Success .This method to
	 * confirm Tickets
	 *
	 * @Value used TransactionStatus.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@PutMapping("/booktheaterpayment")
	@ApiOperation(value = " Theater Payment Confirrm")
	public ResponseEntity<ResponseObject> theaterConfirm(
			@ApiParam(value = "Update Book object store in database table", required = true) @Valid @RequestBody TransactionStatus book)
					throws SQLException {

		ResponseObject bookConfirmPayment = bookingService.bookTheaterTicketsConfirm(book);
		return new ResponseEntity<ResponseObject>(bookConfirmPayment, HttpStatus.ACCEPTED);
	}


	/**
	 * This Service method is used to Update theater With theater ID . This service
	 * used to update from date,to date,movie name ,Price Values
	 *
	 * @Value used UpdateTheater.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@GetMapping("/booktheaterDetails")
	public ResponseEntity<TheaterBookingObject> booktheaterDetails(
			@ApiParam(value =BookingConstant.PARAMDOCSHOWBOOKTheater, required = true) @Valid @RequestBody SearchFlightTickets userbookings) throws SQLException {
		TheaterBookingObject bookedTheater=bookingService.bookTheaterTicketDetails(userbookings);
		return new ResponseEntity<TheaterBookingObject>(bookedTheater, HttpStatus.ACCEPTED);
	}

	@GetMapping("/testing")
	public List<Integer> booktheaterDetails()
	{

		return bookingService.test();
	}
}
