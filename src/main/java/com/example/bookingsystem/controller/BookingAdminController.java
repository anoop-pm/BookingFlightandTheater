package com.example.bookingsystem.controller;

import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingsystem.dto.FlightBookingDto;
import com.example.bookingsystem.dto.FlightDelete;
import com.example.bookingsystem.entity.CancelationOffer;
import com.example.bookingsystem.entity.FlightBooking;
import com.example.bookingsystem.entity.FlightDetails;
import com.example.bookingsystem.entity.TheaterDelete;
import com.example.bookingsystem.entity.TheatreDetails;
import com.example.bookingsystem.entity.UpdateFlight;
import com.example.bookingsystem.entity.UpdateTheater;
import com.example.bookingsystem.model.ResponseObject;
import com.example.bookingsystem.repository.BookingFlightRepository;
import com.example.bookingsystem.repository.CancelationRuleRepository;
import com.example.bookingsystem.repository.FlightRepository;
import com.example.bookingsystem.repository.PassengerRepository;
import com.example.bookingsystem.service.BookingService;
import com.example.bookingsystem.utilities.BookingUtilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v2")
@Api(value = "Ticket Booking Solutions", description = "Book Tickets")
public class BookingAdminController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private CancelationRuleRepository cancelationRuleRepository;

	@Autowired
	private BookingFlightRepository bookingFlight;

	BookingUtilities utilities = new BookingUtilities();

	/**
	 * This post request for add new flight details
	 *
	 * Flight Departuretime,Source,Destination No of Seats Etc
	 *
	 * After Hit this request data was saved to database with unique flight name
	 *
	 *ALso default Cancelation Rule Mapped With Generated Flight ID
	 *
	 * Admin can change rules in future
	 *
	 * @Value used FlightDetails.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *
	 *         * Input
	 *
	 *         { "flightname":"Air Asia", "businessclassseats":15,
	 *         "economyclassseats":15, "times":"10am", "dates":"Thursday",
	 *         "source":"Nedumbasserry", "arrivaltime":"9am", "destination":"Dubai",
	 *         "price":"{economic:10000,business:30000}" }
	 *
	 *
	 *
	 */
	@PostMapping("/addflight")
	@ApiOperation(value = "Add new Flight Details")
	public ResponseEntity<ResponseObject> addFlightDetails(
			@ApiParam(value = "Add flight Object object store in database table", required = true) @Valid @RequestBody FlightDetails flight)
					throws SQLException {

		ResponseObject savedFlights = bookingService.addFights(flight);
		return new ResponseEntity<ResponseObject>(savedFlights, HttpStatus.ACCEPTED);
	}


	/**
	 * This Post Request is used to Add Theater Details.
	 *
	 * need a unique name for add theater after that theater got a unique theater id
	 * we can procees all other activities with this id
	 *
	 * Unique theater name is allowed
	 *
	 * @Value used SearchFlightTickets.class for Request Entity
	 * @return BookingObject This status Code With Message Value.
	 *
	 *         Input { "times": "10am", "theatrename": "INOX1", "price":
	 *         "{economic:100,business:200}", "economyclassseats": 20,
	 *         "businessclassseats": 20, "fromdate": "21/02/2022", "todate":
	 *         "28/03/2022", "moviename": "2", "location": "Thrissur" }
	 *
	 */
	@PostMapping("/addtheater")
	@ApiOperation(value = "Add new Flight Details")
	public ResponseEntity<ResponseObject> addTheaterDetails(
			@ApiParam(value = "Add flight Object object store in database table", required = true) @Valid @RequestBody TheatreDetails theater)
					throws SQLException {

		ResponseObject savedTheaters = bookingService.addTheatre(theater);
		return new ResponseEntity<ResponseObject>(savedTheaters, HttpStatus.ACCEPTED);
	}

	// @PostMapping("/bookflight")
	// @ApiOperation(value = "Add book flight")
	// public ResponseEntity<ResponseObject> addBookTickets(
	// @ApiParam(value = "Add Book Object object store in database table", required
	// = true) @Valid @RequestBody FlightBooking book)
	// throws SQLException {
	//
	// ResponseObject bookConfirm = bookingService.bookTickets(book);
	// return new ResponseEntity<ResponseObject>(bookConfirm, HttpStatus.ACCEPTED);
	// }

	// @PutMapping("/bookflightpayment")
	// @ApiOperation(value = "Add book flight Payment")
	// public ResponseEntity<ResponseObject> flightConfirm(
	// @ApiParam(value = "Add Book Object object store in database table", required
	// = true) @Valid @RequestBody TransactionStatus book)
	// throws SQLException {
	//
	// ResponseObject bookConfirmPayment = bookingService.bookTicketsConfirm(book);
	// return new ResponseEntity<ResponseObject>(bookConfirmPayment,
	// HttpStatus.ACCEPTED);
	// }

	@PostMapping("/bookingRequest")
	public FlightBooking placeOrder(@RequestBody FlightBookingDto request) {

		return bookingFlight.save(request.getBookingFlights());
	}

	// @PutMapping("/cancelbookflight")
	// @ApiOperation(value = "Add book flight Payment")
	// public ResponseEntity<ResponseObject> cancelTickets(
	// @ApiParam(value = "Add Book Object object store in database table", required
	// = true) @Valid @RequestBody CancelBooking book)
	// throws SQLException {
	//
	// ResponseObject bookConfirmPayment = bookingService.cancelFlightTicket(book);
	// return new ResponseEntity<ResponseObject>(bookConfirmPayment,
	// HttpStatus.ACCEPTED);
	// }

	@PostMapping("/bookingRequestFlight")
	public FlightBooking bookFlightss(@RequestBody FlightBooking flightBook) {

		flightBook.setBookingid(111);
		flightBook.setUserid(1473);
		flightBook.setAmount(120000);
		String flightDate = flightRepository.getDate(135702);
		String destination = flightRepository.getDestination(135702);
		String source = flightRepository.getSource(135702);
		String time = flightRepository.getTime(135702);
		String flightName = flightRepository.getFlightNameByid(135702);
		flightBook.setDate(flightDate);
		flightBook.setDestination(destination);
		flightBook.setSource(source);
		flightBook.setFlightname(flightName);
		flightBook.setTime(time);
		flightBook.setSeats(flightBook.getSeats());
		flightBook.setFlightid(135702);

		return bookingFlight.save(flightBook);
	}

	// @GetMapping("/findAllOrders")
	// public List<Object[]> findAllOrders() {
	//
	// return bookingFlight.getbookedFlightDetails(2507);
	// }

	// @GetMapping("/findAllOrdersName")
	// public Object findName() {
	//
	// List<Passenger> pass = new ArrayList<Passenger>();
	// pass = passengerRepository.getpassenger(1);
	// Map<String, Passenger> dict = new HashMap<String, Passenger>();
	// for (int i = 0; i < pass.size(); i++) {
	// Passenger name = pass.get(i);
	//
	// dict.put("passenger" + i, name);
	//
	// }
	//
	// String json = null;
	// try {
	// json = new ObjectMapper().writeValueAsString(dict);
	// } catch (JsonProcessingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// System.out.println(json);
	//
	// return json;
	//
	// }

	// @GetMapping("/findflight")
	// @ApiOperation(value = "Add find flight")
	// public ResponseEntity<List<FlightDetails>> findFlights(
	// @ApiParam(value = "Add Cancel Object object store in database table",
	// required = true) @Valid @RequestBody FlightDetails flight) throws
	// SQLException {
	//
	// return new
	// ResponseEntity<List<FlightDetails>>(bookingService.findFlights(flight),
	// HttpStatus.ACCEPTED);
	// }

	// @GetMapping("/findflight")
	// @ApiOperation(value = "Add find flight")
	// public ResponseEntity<FlightObject> findFlights(
	// @ApiParam(value = "Add Cancel Object object store in database table",
	// required = true) @Valid @RequestBody SearchFlight flight)
	// throws SQLException {
	// FlightObject flightsDetails = bookingService.findFlights(flight);
	// return new ResponseEntity<FlightObject>(flightsDetails, HttpStatus.ACCEPTED);
	// }

	@PutMapping("/cancelationRuleUpdate")
	ResponseEntity<ResponseObject> cancelRuleUpdate(
			@ApiParam(value = "Add Cancel Object object store in database table", required = true) @Valid @RequestBody CancelationOffer flight)
					throws SQLException {
		ResponseObject flightsDetails = bookingService.cancelationRuleUpdate(flight);
		return new ResponseEntity<ResponseObject>(flightsDetails, HttpStatus.ACCEPTED);
	}

	@GetMapping("/check")
	public String checking(@RequestBody UpdateFlight flights) {

		int ahr = flights.getPrice().getBusiness();

		return "values" + ahr;
	}

	@DeleteMapping("/deleteflight")
	@ApiOperation(value = "Add book flight")
	public ResponseEntity<ResponseObject> deleteflight(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody FlightDelete book)
					throws SQLException {

		ResponseObject deleteConfirm = bookingService.deleteFlight(book);

		return new ResponseEntity<ResponseObject>(deleteConfirm, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteTheater")
	@ApiOperation(value = "Add book flight")
	public ResponseEntity<ResponseObject> deleteTheater(
			@ApiParam(value = "Add Book Object object store in database table", required = true) @Valid @RequestBody TheaterDelete book)
					throws SQLException {

		ResponseObject deleteConfirm = bookingService.deleteTheater(book);

		return new ResponseEntity<ResponseObject>(deleteConfirm, HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateFlight")
	ResponseEntity<ResponseObject> flightUpdate(
			@ApiParam(value = "Add Cancel Object object store in database table", required = true) @Valid @RequestBody UpdateFlight flight)
					throws SQLException {
		ResponseObject updateDetails = bookingService.updateFlight(flight);
		return new ResponseEntity<ResponseObject>(updateDetails, HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateTheater")
	ResponseEntity<ResponseObject> theaterUpdate(
			@ApiParam(value = "Add Cancel Object object store in database table", required = true) @Valid @RequestBody UpdateTheater theater)
					throws SQLException {
		ResponseObject updateDetails = bookingService.updateTheater(theater);
		return new ResponseEntity<ResponseObject>(updateDetails, HttpStatus.ACCEPTED);
	}

}
