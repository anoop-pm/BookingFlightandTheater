package com.example.bookingsystem.service;

import java.sql.SQLException;
import java.util.List;

import com.example.bookingsystem.dto.CancelBooking;
import com.example.bookingsystem.dto.FlightDelete;
import com.example.bookingsystem.dto.SearchFlightTickets;
import com.example.bookingsystem.dto.SearchMovie;
import com.example.bookingsystem.entity.CancelationOffer;
import com.example.bookingsystem.entity.FlightBooking;
import com.example.bookingsystem.entity.FlightDetails;
import com.example.bookingsystem.entity.SearchFlight;
import com.example.bookingsystem.entity.TheaterBooking;
import com.example.bookingsystem.entity.TheaterDelete;
import com.example.bookingsystem.entity.TheatreDetails;
import com.example.bookingsystem.entity.TransactionStatus;
import com.example.bookingsystem.entity.UpdateFlight;
import com.example.bookingsystem.entity.UpdateTheater;
import com.example.bookingsystem.entity.User;
import com.example.bookingsystem.model.BookingObject;
import com.example.bookingsystem.model.FlightObject;
import com.example.bookingsystem.model.ResponseObject;
import com.example.bookingsystem.model.TheaterBookingObject;
import com.example.bookingsystem.model.TheaterObject;



public interface BookingService {

	public ResponseObject addUser(User user) throws SQLException;
	public ResponseObject addFights(FlightDetails flightDetails) throws SQLException;
	public ResponseObject updateFlight(UpdateFlight update) throws SQLException;

	public ResponseObject updateTheater(UpdateTheater update) throws SQLException;
	public ResponseObject addTheatre(TheatreDetails theatreDetails) throws SQLException;
	//	public ResponseObject cancelationRules(CancelationRules rules) throws SQLException;
	public ResponseObject bookTickets(FlightBooking bookFlights) throws SQLException;
	public ResponseObject bookTheaterTickets(TheaterBooking bookTheater) throws SQLException;
	public FlightObject findFlights(SearchFlight flightDetails) throws SQLException;
	public TheaterObject findTheater(SearchMovie theater) throws SQLException;

	public ResponseObject bookTicketsConfirm(TransactionStatus bookDeails) throws SQLException;
	public ResponseObject bookTheaterTicketsConfirm(TransactionStatus bookDeails) throws SQLException;
	public BookingObject bookTicketDetails(SearchFlightTickets bookDeails) throws SQLException;

	public TheaterBookingObject bookTheaterTicketDetails(SearchFlightTickets bookDeails) throws SQLException;
	public ResponseObject deleteFlight(FlightDelete bookDeails) throws SQLException;
	public ResponseObject deleteTheater(TheaterDelete delete) throws SQLException;
	public ResponseObject cancelationRuleUpdate(CancelationOffer cancelUpdate) throws SQLException;
	public ResponseObject cancelFlightTicket(CancelBooking bookDeails) throws SQLException;
	public ResponseObject cancelTheaterTicket(CancelBooking bookDeails) throws SQLException;
	List<FlightBooking> getAllPassangersCustom(int page);

}
