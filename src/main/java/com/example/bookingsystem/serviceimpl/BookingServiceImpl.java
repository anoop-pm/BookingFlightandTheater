package com.example.bookingsystem.serviceimpl;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.bookingsystem.constant.BookingConstant;
import com.example.bookingsystem.dto.BookDetails;
import com.example.bookingsystem.dto.CancelBooking;
import com.example.bookingsystem.dto.FlightDelete;
import com.example.bookingsystem.dto.SearchFlightTickets;
import com.example.bookingsystem.dto.SearchMovie;
import com.example.bookingsystem.entity.CancelationOffer;
import com.example.bookingsystem.entity.CancelationRules;
import com.example.bookingsystem.entity.FlightBooking;
import com.example.bookingsystem.entity.FlightDetails;
import com.example.bookingsystem.entity.Passenger;
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
import com.example.bookingsystem.repository.BookingFlightRepository;
import com.example.bookingsystem.repository.BookingTheaterRepository;
import com.example.bookingsystem.repository.CancelationRuleRepository;
import com.example.bookingsystem.repository.FlightRepository;
import com.example.bookingsystem.repository.PassengerRepository;
import com.example.bookingsystem.repository.TheatreRepository;
import com.example.bookingsystem.repository.TransactionRepository;
import com.example.bookingsystem.repository.UserRepository;
import com.example.bookingsystem.service.BookingService;
import com.example.bookingsystem.utilities.BookingUtilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private ResponseObject response;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private CancelationRuleRepository cancelationRuleRepository;

	@Autowired
	private BookingFlightRepository bookingFlight;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TheatreRepository theatreRepository;

	@Autowired
	private BookingTheaterRepository bookingTheaterRepository;

	@Autowired
	private DataSource dataSource;

	BookingConstant constant = new BookingConstant();

	BookingUtilities utilities = new BookingUtilities();

	FlightObject flightResponce = new FlightObject();

	BookingObject bookingObject = new BookingObject();

	TheaterObject theaterObject = new TheaterObject();

	TheaterBookingObject theaterBookingObject = new TheaterBookingObject();

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
	 *
	 */

	@Override
	public ResponseObject addUser(User user) throws SQLException {

		String email = null;
		String phone = null;
		int maxid = 0;
		try {
			email = userRepository.getEmailAddress(user.getEmail());
			phone = userRepository.getPhoneNumber(user.getPhonenumber());
			maxid = userRepository.maxuserid();
			boolean connection = dataSource.getConnection().isValid(1000);
			System.out.println(email + phone + maxid + "connection " + connection);
		} catch (Exception e) {
			// logger
		}

		int userid = utilities.randomNumber(4, maxid);
		System.out.println(utilities.validateJavaDate(user.getDateofbirth()) + email + phone + userid);
		boolean dateValid = utilities.validateJavaDate(user.getDateofbirth());
		if (dateValid == false) {
			response.setStatus(constant.StatusError);
			response.setMessage(constant.DATENOTVALID);
			return response;
		}
		if (utilities.emailPhoneValid(email, phone) == true) { // Check phone and Email is Unique
			user.setName(user.getName());
			user.setDateofbirth(user.getDateofbirth());
			user.setAge(user.getAge());
			user.setAddress(user.getAddress());
			user.setPhonenumber(user.getPhonenumber());
			user.setEmail(user.getEmail());
			user.setUserid(userid);
			System.out.println("connection Check");
			boolean connection = dataSource.getConnection().isValid(1000);

			System.out.println("connection " + connection);
			if (connection == true) {
				userRepository.save(user);
				response.setStatus(constant.StatusOk);
				response.setMessage(constant.UserCreated + userid);
			} else {
				response.setStatus(constant.StatusError);
				response.setMessage(constant.ConnectionError);

			}
		} else {
			response.setStatus(constant.StatusError);
			response.setMessage(constant.EmailPhoneExist);
		}
		return response;
	}

	/**
	 * add Flight This Service for add flight Details
	 *
	 * Maxflight and Flight name used for set Unique value flight name used for
	 * identify flightname already Exist MaxflightID used for Generatinf Random
	 * Unique Number
	 *
	 * Here Economic and Business Seats arranged as List used for loop for that
	 *
	 * ALso default Cancelation Rule Mapped With Generated Flight ID
	 *
	 * Admin can change rules in future
	 *
	 *
	 * Input
	 *
	 * { "flightname":"Air Asia", "businessclassseats":15, "economyclassseats":15,
	 * "times":"10am", "dates":"Thursday", "source":"Nedumbasserry",
	 * "arrivaltime":"9am", "destination":"Dubai",
	 * "price":"{economic:10000,business:30000}" }
	 *
	 */
	@Override
	public ResponseObject addFights(FlightDetails flightDetails) throws SQLException {
		// TODO Auto-generated method stub

		int maxFlightId = 0;
		int businessSeat = 0;
		int economicSeat = 0;
		String flightName = null;

		try {
			maxFlightId = flightRepository.maxFlightId();
			flightName = flightRepository.getFlightName(flightDetails.getFlightname());
		} catch (Exception e) {
			// Logger
		}
		int newFlightId = utilities.randomNumber(5, maxFlightId);
		System.out.println(maxFlightId + "value=" + newFlightId);
		if (flightName == null) {
			flightDetails.setFlightname(flightDetails.getFlightname());
			flightDetails.setBusinessclassseats(flightDetails.getBusinessclassseats());
			flightDetails.setEconomyclassseats(flightDetails.getEconomyclassseats());

			List<Integer> businessList = new ArrayList<>();
			List<Integer> economicList = new ArrayList<>();
			Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
			businessSeat = flightDetails.getBusinessclassseats();
			economicSeat = flightDetails.getEconomyclassseats();
			for (int i = 1; i < businessSeat + 1; i++) {
				businessList.add(i);
			}
			for (int i = 1; i < economicSeat + 1; i++) {
				economicList.add(i);
			}
			dict.put(constant.Business, businessList);
			dict.put(constant.Economic, economicList);
			JSONObject json = new JSONObject(dict);
			flightDetails.setSeats(json.toString());
			flightDetails.setDates(flightDetails.getDates());
			flightDetails.setDispaturetimes(flightDetails.getDispaturetimes());
			flightDetails.setDuration(flightDetails.getDuration());
			JSONObject priceJson = new JSONObject(flightDetails.getPrice());
			flightDetails.setPrice(priceJson.toString());
			flightDetails.setSource(flightDetails.getSource());
			flightDetails.setDestination(flightDetails.getDestination());
			flightDetails.setFlightid(newFlightId);

			CancelationRules rules = new CancelationRules();
			Map<String, Integer> ruleMap = new HashMap<String, Integer>();
			ruleMap.put(constant.CancelWithin4, 100);
			ruleMap.put(constant.Before1hr, 40);
			ruleMap.put(constant.CancelationCharge, 4);
			ruleMap.put(constant.Offers, 0);
			JSONObject ruleJson = new JSONObject(ruleMap);
			String defaultRules = ruleJson.toString();
			rules.setBookingsystem("Flight");
			rules.setSystemid(newFlightId);
			Instant now = Instant.now();
			rules.setTime(now.toString());
			rules.setRules(defaultRules);
			boolean connection = dataSource.getConnection().isValid(1000);
			if (connection == true) {
				flightRepository.save(flightDetails);
				cancelationRuleRepository.save(rules);
				response.setStatus(200);
				response.setMessage(constant.FlightDetails + newFlightId);
			} else {
				response.setStatus(404);
				response.setMessage(constant.ConnectionError);
			}
		} else {
			response.setStatus(404);
			response.setMessage(constant.FlightName);
		}
		return response;
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
	@Override
	public FlightObject findFlights(SearchFlight flightDetails) throws SQLException {
		// TODO Auto-generated method stub

		boolean dateValid = utilities.validateJavaDate(flightDetails.getDates());
		if (dateValid == false) {
			flightResponce.setStatus(400);
			flightResponce.setMessage("PLease Fill Valid Date Format like DD/MM/YEAR");
			return flightResponce;
		}
		Date date = new Date();
		String dayWeekText = null;
		try {
			dayWeekText = utilities.dateDay(flightDetails.getDates());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("day" + dayWeekText + "date" + date);
		List<FlightDetails> flightDetailsList = null;
		String flightList = null;
		int noOfSeat = Integer.parseInt(flightDetails.getSeats());
		try {
			flightDetailsList = flightRepository.getFlight(dayWeekText, flightDetails.getDestination(),
					flightDetails.getSource(), noOfSeat);
			flightList = flightDetailsList.toString();
		} catch (Exception e) {
			// logger
		}
		if (flightList != null) {
			flightResponce.setStatus(200);
			flightResponce.setMessage(constant.FlightDetailsList);
			flightResponce.setFlightList(flightDetailsList);
		} else {
			flightResponce.setStatus(400);
			flightResponce.setMessage(constant.FlightNotAvailable);
		}
		return flightResponce;
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

	@Override
	public ResponseObject bookTheaterTickets(TheaterBooking bookTheater) throws SQLException {
		// TODO Auto-generated method stub

		String seats = bookTheater.getSeats();
		JSONObject seatJson = new JSONObject(seats);
		String economicSeat = seatJson.get(constant.Economic).toString();
		String businessSeat = seatJson.get(constant.Business).toString();
		List<String> selectEconomicList = utilities.createList(economicSeat);
		List<String> selectBusinessList = utilities.createList(businessSeat);
		int theaterID = 0;
		int systemId = 0;
		String cancelRule = null;
		int maxBookId = 0;
		int userId = 0;
		String availableSeat = null;
		String seatPrice = null;
		String location = null;
		try {
			theaterID = theatreRepository.getTheatreid(bookTheater.getTheaterid());
			systemId = cancelationRuleRepository.getSystemId(bookTheater.getTheaterid());
			cancelRule = cancelationRuleRepository.getrule(systemId);
			seatPrice = theatreRepository.getPrice(theaterID);
			availableSeat = theatreRepository.getSeats(theaterID);
			userId = userRepository.getUserIds(bookTheater.getUserid());
			maxBookId = bookingTheaterRepository.maxBookId();
			location = theatreRepository.getLocation(theaterID);

		} catch (Exception e) {

		}

		boolean seatSearchEconomic = false;
		boolean seatSearchBusiness = false;
		List<String> newEconomicList = null;
		List<String> newBusinessList = null;
		int sizeEco = 0;
		int sizeBis = 0;
		int sizeofSeats = 0;
		float afteroffers = 0;
		int bookingid = 0;

		if (utilities.validateJavaDate(bookTheater.getDate()) == false) {
			response.setStatus(404);
			response.setMessage(constant.DATENOTVALID);
			return response;
		}

		if (theaterID == 0) {
			System.out.println("Error");
			response.setStatus(404);
			response.setMessage(constant.THEATERIDNOTVALID);
			return response;
		}

		bookingid = utilities.randomNumber(5, maxBookId);
		JSONObject availableSeatJson = new JSONObject(availableSeat);
		String economicSeats = availableSeatJson.get(constant.Economic).toString();
		String businessSeats = availableSeatJson.get(constant.Business).toString();
		newEconomicList = utilities.createList(economicSeats);
		newBusinessList = utilities.createList(businessSeats);
		seatSearchEconomic = utilities.search(newEconomicList, selectEconomicList);
		seatSearchBusiness = utilities.search(newBusinessList, selectBusinessList);
		System.out.println("searched e" + seatSearchEconomic + seatSearchBusiness);

		if (seatSearchEconomic == false || seatSearchBusiness == false) {
			System.out.println("Error");
			response.setStatus(404);
			response.setMessage("Seats not Available");
			return response;
		}
		JSONObject ruleJson = new JSONObject(cancelRule);
		JSONObject price = new JSONObject(seatPrice);
		int business = (int) price.get(constant.Business);
		int economic = (int) price.get(constant.Economic);
		int offers = (int) ruleJson.get(constant.Offers);
		int economicPrice;
		int businessPrice;

		if (seatSearchEconomic == false || seatSearchBusiness == false) {
			System.out.println("Size is" + "Save here");
		}

		if (selectEconomicList.get(0) != "") {
			economicPrice = selectEconomicList.size() * economic;
		} else {
			economicPrice = selectEconomicList.size() * 0;
		}
		if (selectBusinessList.get(0) != "") {
			businessPrice = selectBusinessList.size() * business;
		} else {
			businessPrice = selectBusinessList.size() * 0;
		}

		int totalAmount = economicPrice + businessPrice;

		afteroffers = totalAmount - totalAmount * (offers / 100);
		if (selectEconomicList.size() == 1) {
			newEconomicList.remove(selectEconomicList.get(0));
			sizeEco = utilities.sizeOfList(selectEconomicList);
		} else {
			newEconomicList = utilities.removeSeat(newEconomicList, selectEconomicList);
			sizeEco = selectEconomicList.size();
		}
		if (newBusinessList.size() == 1) {
			newBusinessList.remove(newBusinessList.get(0));
			sizeBis = utilities.sizeOfList(selectBusinessList);
		} else {
			newBusinessList = utilities.removeSeat(newBusinessList, selectBusinessList);
			sizeBis = selectBusinessList.size();
		}
		sizeofSeats = sizeEco + sizeBis;
		System.out.println("Size is" + sizeofSeats + newBusinessList + "and" + newEconomicList + "PriceE"
				+ economicPrice + "PriceB" + afteroffers);
		bookTheater.setAmount((int) afteroffers);
		bookTheater.setCardnumber("Pending");
		bookTheater.setDate(bookTheater.getDate());
		bookTheater.setPaymentoption("Pending");
		Instant now = Instant.now();
		bookTheater.setTime(now.toString());// create gettime
		bookTheater.setLocation(location);
		bookTheater.setTheaterid(theaterID);
		bookTheater.setBookingid(bookingid);
		bookTheater.setDate(bookTheater.getDate());
		bookTheater.setUserid(bookTheater.getUserid());
		bookTheater.setSeats(bookTheater.getSeats());

		Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
		List<Integer> integerList = newEconomicList.stream().map(Integer::valueOf).collect(Collectors.toList());
		List<Integer> integerBList = newBusinessList.stream().map(Integer::valueOf).collect(Collectors.toList());
		dict.put(constant.Business, integerBList);
		dict.put(constant.Economic, integerList);
		JSONObject newSeatJson = new JSONObject(dict);
		// update seat
		bookingTheaterRepository.save(bookTheater);
		theatreRepository.updateSeat(newSeatJson.toString(), theaterID);

		// update no of seat

		theatreRepository.updateBusinessSeat(newBusinessList.size(), theaterID);

		theatreRepository.updateEconomicSeat(newEconomicList.size(), theaterID);

		response.setStatus(202);
		response.setMessage(constant.MOVIETICKETBOOKED + bookingid + "Booked Seats Are" + seatJson);
		return response;
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
	@Override
	public ResponseObject bookTickets(FlightBooking bookFlight) throws SQLException {
		// TODO Auto-generated method stub
		int flightId = 0;
		int systemId = 0;
		String cancelRule = null;
		String seatPrice = null;
		String availableSeat = null;

		String seats = bookFlight.getSeats();
		JSONObject seatJson = new JSONObject(seats);
		String economicSeat = seatJson.get(constant.Economic).toString();
		String businessSeat = seatJson.get(constant.Business).toString();
		List<String> selectEconomicList = utilities.createList(economicSeat);
		List<String> selectBusinessList = utilities.createList(businessSeat);
		int bookingid = 0;
		int maxBookId = 0;
		int userId = 0;
		try {
			flightId = flightRepository.getFlightId(bookFlight.getFlightid());
			systemId = cancelationRuleRepository.getSystemId(bookFlight.getFlightid());
			cancelRule = cancelationRuleRepository.getrule(systemId);
			seatPrice = flightRepository.getPrice(flightId);
			availableSeat = flightRepository.getSeats(flightId);
			userId = userRepository.getUserIds(bookFlight.getUserid());
			maxBookId = bookingFlight.maxBookId();
		} catch (Exception e) {
			// logger
		}

		boolean seatSearchEconomic = false;
		boolean seatSearchBusiness = false;
		List<String> newEconomicList = null;
		List<String> newBusinessList = null;
		int sizeEco = 0;
		int sizeBis = 0;
		int sizeofSeats = 0;
		float afteroffers = 0;
		if (flightId != 0 && systemId != 0) {
			System.out.println(userId + "userid" + bookFlight.getUserid() + flightId + "ss" + systemId);
			bookingid = utilities.randomNumber(5, maxBookId);
			JSONObject availableSeatJson = new JSONObject(availableSeat);
			String economicSeats = availableSeatJson.get(constant.Economic).toString();
			String businessSeats = availableSeatJson.get(constant.Business).toString();
			newEconomicList = utilities.createList(economicSeats);
			newBusinessList = utilities.createList(businessSeats);
			seatSearchEconomic = utilities.search(newEconomicList, selectEconomicList);
			seatSearchBusiness = utilities.search(newBusinessList, selectBusinessList);
			System.out.println("searched e" + seatSearchEconomic + seatSearchBusiness);
			if (seatSearchEconomic == false && seatSearchBusiness == false) {

				response.setStatus(404);
				response.setMessage("Seats Not Available PLease Give Valid Seats ");
				return response;
			}

			JSONObject ruleJson = new JSONObject(cancelRule);
			JSONObject price = new JSONObject(seatPrice);
			int business = (int) price.get(constant.Business);
			int economic = (int) price.get(constant.Economic);
			int offers = (int) ruleJson.get(constant.Offers);
			int economicPrice;
			int businessPrice;

			if (selectEconomicList.get(0) != "") {
				economicPrice = selectEconomicList.size() * economic;
			} else {
				economicPrice = selectEconomicList.size() * 0;
			}
			if (selectBusinessList.get(0) != "") {
				businessPrice = selectBusinessList.size() * business;
			} else {
				businessPrice = selectBusinessList.size() * 0;
			}

			int totalAmount = economicPrice + businessPrice;

			afteroffers = totalAmount - totalAmount * (offers / 100);
			if (selectEconomicList.size() == 1) {
				newEconomicList.remove(selectEconomicList.get(0));
				sizeEco = utilities.sizeOfList(selectEconomicList);
			} else {
				newEconomicList = utilities.removeSeat(newEconomicList, selectEconomicList);
				sizeEco = selectEconomicList.size();
			}
			if (newBusinessList.size() == 1) {
				newBusinessList.remove(newBusinessList.get(0));
				sizeBis = utilities.sizeOfList(selectBusinessList);
			} else {
				newBusinessList = utilities.removeSeat(newBusinessList, selectBusinessList);
				sizeBis = selectBusinessList.size();
			}
			sizeofSeats = sizeEco + sizeBis;
			System.out.println("Size is" + sizeofSeats + "vs" + bookFlight.getPassengers().size());
		} else {
			System.out.println("Error");
			response.setStatus(404);
			response.setMessage(constant.NOTMATCHEDFLIGHTID);
			return response;
		}

		if (flightId != 0 && systemId != 0 && userId != 0 && seatSearchEconomic == true && seatSearchBusiness == true
				&& bookFlight.getPassengers().size() == sizeofSeats) {
			boolean connection = dataSource.getConnection().isValid(1000);
			if (connection == true) {

				Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
				List<Integer> integerList = newEconomicList.stream().map(Integer::valueOf).collect(Collectors.toList());
				List<Integer> integerBList = newBusinessList.stream().map(Integer::valueOf)
						.collect(Collectors.toList());
				dict.put(constant.Business, integerBList);
				dict.put(constant.Economic, integerList);
				JSONObject newSeatJson = new JSONObject(dict);
				flightRepository.updateSeat(newSeatJson.toString(), flightId);
				bookFlight.setBookingid(bookingid);
				bookFlight.setUserid(userId);
				bookFlight.setAmount((int) afteroffers);
				String flightDate = flightRepository.getDate(flightId);
				String destination = flightRepository.getDestination(flightId);
				String source = flightRepository.getSource(flightId);
				String time = flightRepository.getTime(flightId);
				String flightName = flightRepository.getFlightNameByid(flightId);
				bookFlight.setDate(flightDate);
				bookFlight.setDestination(destination);
				bookFlight.setSource(source);
				bookFlight.setFlightname(flightName);
				bookFlight.setTime(time);
				bookFlight.setSeats(bookFlight.getSeats());
				bookFlight.setFlightid(flightId);
				bookFlight.setPaymentstatus("notpayed");
				bookFlight.setPaymentoption("notpayed");
				bookFlight.setMail(bookFlight.getMail());
				Instant now = Instant.now();
				bookFlight.setBookedtime(now.toString());
				int numberEconomicSeat = flightRepository.getNumberEconomicSeat(flightId);
				int numberBusinessSeat = flightRepository.getNumberBusinessSeat(flightId);
				int newNumberEconomicSeat = numberEconomicSeat - selectEconomicList.size();
				int newNumberBusinessSeat = numberBusinessSeat - selectBusinessList.size();
				flightRepository.updateEconomicSeat(newNumberEconomicSeat, flightId);
				flightRepository.updateBusinessSeat(newNumberBusinessSeat, flightId);
				bookingFlight.save(bookFlight);
				int newbookId = 0;

				try {
					Thread.sleep(2000);
					newbookId = bookingFlight.getBookMapId(bookingid);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Passenger> passengerList = new ArrayList<Passenger>();
				passengerList = passengerRepository.getpassenger(newbookId);
				Map<String, Passenger> passengerdict = new HashMap<String, Passenger>();
				for (int i = 0; i < passengerList.size(); i++) {
					Passenger name = passengerList.get(i);
					passengerdict.put("passenger" + i, name);
				}

				String passengerJson = null;
				try {
					passengerJson = new ObjectMapper().writeValueAsString(passengerdict);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JSONObject newSeatJsonPass = new JSONObject(passengerJson);

				System.out.println(newSeatJsonPass.toString());
				System.out.println(
						"Available Economic Seats " + newEconomicList + "Available Business Seats " + newBusinessList
						+ "id" + newbookId + "Passenger" + passengerJson + bookFlight.getPassengers().size()
						+ "Available Seat " + newNumberEconomicSeat + " " + newNumberBusinessSeat);
				response.setStatus(200);

				String emailUserId = userRepository.getEmailId(userId);

				String mailContent = " Your Booking ID IS " + bookingid + BookingConstant.PAYABLEAMOUNT + afteroffers
						+ BookingConstant.PassengerDetails + newSeatJsonPass.toString() + "Your Seats"
						+ selectEconomicList + selectBusinessList + BookingConstant.CONFIRMBILL;
				sendEmail(mailContent, emailUserId);
				sendEmail(mailContent, bookFlight.getMail());
				response.setMessage(mailContent);
			} else {
				response.setStatus(404);
				response.setMessage(constant.ConnectionError);
			}
		} else if (seatSearchEconomic != true && seatSearchBusiness != true) {
			response.setStatus(404);
			response.setMessage("Seat Not Available");
		} else {
			response.setStatus(404);
			response.setMessage(constant.BookUserFlightId);
		}
		return response;
	}

	@Override
	public List<FlightBooking> getAllPassangersCustom(int page) {
		int size = 3;
		Pageable pagable = PageRequest.of(page, size);
		return bookingFlight.findAll(pagable).toList();
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
	@Override
	public ResponseObject bookTicketsConfirm(TransactionStatus bookDeails) throws SQLException {
		int bookidcount = bookingFlight.searchId(bookDeails.getBookingid());
		int max = 0;
		String mail = null;
		String paymentStatus = null;
		int userid = 0;
		int bookAmount = 0;
		try {
			bookAmount = bookingFlight.amount(bookDeails.getBookingid());

			paymentStatus = bookingFlight.getPaymentStatus(bookDeails.getBookingid());
			max = transactionRepository.maxTransactionId();
			userid = bookingFlight.getbookUserId(bookDeails.getBookingid());

		} catch (Exception e) {
			max = 0;
		}
		System.out.println(paymentStatus + "paymnetStatus" + bookAmount);
		if (bookAmount != bookDeails.getAmount()) {
			response.setStatus(404);
			response.setMessage(constant.AmountNotMatch);
			return response;
		}
		if (paymentStatus.equals("payed")) {
			response.setStatus(404);
			response.setMessage("Payment Already Succeed");
			return response;
		}
		if (bookidcount != 0 && bookAmount == bookDeails.getAmount() && paymentStatus.equals("notpayed")) {
			bookingFlight.updatePayment("payed", bookDeails.getBookingid());
			bookingFlight.updatePaymentOption(bookDeails.getPaymentoption(), bookDeails.getBookingid());

			bookDeails.setTransferstatus("Booking");
			bookDeails.setPaymentoption(bookDeails.getPaymentoption());
			bookDeails.setUserid(bookDeails.getUserid());
			int selectAmount = bookingFlight.amount(bookDeails.getBookingid());
			bookDeails.setAmount(selectAmount);
			String transactionID = "TRA" + utilities.randomNumber(5, max);
			bookDeails.setTransactionid(transactionID);
			bookDeails.setBookingid(bookDeails.getBookingid());
			bookDeails.setCardnumber(bookDeails.getCardnumber());
			bookDeails.setCvv(bookDeails.getCvv());
			bookDeails.setValidthrough(bookDeails.getValidthrough());
			bookDeails.setUserid(userid);
			bookDeails.setBookingsystem("Flight");
			transactionRepository.save(bookDeails);
			response.setStatus(200);
			response.setMessage(constant.PAYMENTSUCCESS);
		} else {
			response.setStatus(404);
			response.setMessage(constant.PAYMENTNOTSUCCESS);
		}
		return response;
	}

	public void sendEmail(String rep, String mailid) {
		// for Send Mail
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailid);
		msg.setSubject(" Flight Booking Status ");
		msg.setText(rep);
		javaMailSender.send(msg); // Send mail
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

	@Override
	public ResponseObject cancelFlightTicket(CancelBooking bookDeails) throws SQLException {
		// TODO Auto-generated method stub

		String cancelRule = null;
		String seatPrice = null;
		String availableSeat = null;

		String seats = bookDeails.getSeats();
		JSONObject seatJson = new JSONObject(seats);
		String economicSeat = seatJson.get(constant.Economic).toString();
		String businessSeat = seatJson.get(constant.Business).toString();
		List<String> selectEconomicList = utilities.createList(economicSeat);
		List<String> selectBusinessList = utilities.createList(businessSeat);
		System.out.println(selectEconomicList + "" + selectBusinessList);
		int flightId = 0;
		int systemId = 0;
		int userId = 0;
		String availableBookSeat = null;
		String bookedTime = null;
		String cardNumber = null;
		try {
			flightId = bookingFlight.searchFlightId(bookDeails.getBookingid());
			systemId = cancelationRuleRepository.getSystemId(flightId);
			userId = userRepository.getUserIds(bookDeails.getUserid());
			cancelRule = cancelationRuleRepository.getrule(flightId);
			seatPrice = flightRepository.getPrice(flightId);
			availableSeat = flightRepository.getSeats(flightId);
			availableBookSeat = bookingFlight.getBookSeats(bookDeails.getBookingid());
			bookedTime = bookingFlight.getBookedTime(bookDeails.getBookingid());
			cardNumber = transactionRepository.getCardNumber(bookDeails.getBookingid());

		} catch (Exception e) {
			// logger

			System.out.println("error happened in try" + flightId + "s" + systemId + "u" + userId + cancelRule
					+ seatPrice + availableSeat + availableBookSeat + bookedTime);
		}
		if (flightId == 0) {

			response.setStatus(404);
			response.setMessage(constant.NOTMATCHBOOKING);
			return response;
		}
		JSONObject availableSeatJson = new JSONObject(availableSeat);
		String economicSeats = availableSeatJson.get(constant.Economic).toString();
		String businessSeats = availableSeatJson.get(constant.Business).toString();
		List<String> newEconomicList = utilities.createList(economicSeats);
		List<String> newBusinessList = utilities.createList(businessSeats);
		System.out.println("values" + flightId + "s" + systemId + "u" + userId + cancelRule + seatPrice + availableSeat
				+ availableBookSeat + bookedTime + "and" + newEconomicList + "" + newBusinessList);

		boolean seatSearchEconomic = utilities.search(newEconomicList, selectEconomicList);
		boolean seatSearchBusiness = utilities.search(newBusinessList, selectBusinessList);
		System.out.println(newEconomicList + "serach" + selectEconomicList);
		System.out.println(newBusinessList + "bookedbissserach" + selectBusinessList);
		if (selectEconomicList.size() == 1) {
			newEconomicList.add(selectEconomicList.get(0));

		} else {
			newEconomicList = utilities.addSeat(newEconomicList, selectEconomicList);

		}
		if (newBusinessList.size() == 1) {
			newBusinessList.add(newBusinessList.get(0));
		} else {
			newBusinessList = utilities.addSeat(newBusinessList, selectBusinessList);
		}
		flightRepository.updateEconomicSeat(newEconomicList.size(), flightId);
		flightRepository.updateBusinessSeat(newBusinessList.size(), flightId);
		System.out.println(newEconomicList + "added" + newBusinessList);
		JSONObject availableBookSeatJson = new JSONObject(availableBookSeat);
		String economicBookSeats = availableBookSeatJson.get(constant.Economic).toString();
		String businessBookSeats = availableBookSeatJson.get(constant.Business).toString();
		List<String> newEconomicBookList = utilities.createList(economicBookSeats);
		List<String> newBusinessBookList = utilities.createList(businessBookSeats);
		boolean seatSearchBookEconomic = utilities.search(newEconomicBookList, selectEconomicList);
		boolean seatSearchBookBusiness = utilities.search(newBusinessBookList, selectBusinessList);
		System.out.println(newEconomicBookList + "bookedserach" + selectEconomicList);
		System.out.println(newBusinessBookList + "bookedserach" + selectBusinessList);
		if (selectEconomicList.size() == 1) {
			newEconomicBookList.remove(selectEconomicList.get(0));

		} else {
			newEconomicBookList = utilities.removeSeat(newEconomicBookList, selectEconomicList);

		}
		if (selectBusinessList.size() == 1) {
			newBusinessBookList.remove(selectBusinessList.get(0));
		} else {
			newBusinessBookList = utilities.removeSeat(newBusinessBookList, selectBusinessList);
		}
		System.out.println(newEconomicBookList + "afterremove" + newBusinessBookList);
		System.out.println(seatSearchBookEconomic + "" + seatSearchBookBusiness + "" + seatSearchEconomic + ""
				+ seatSearchBusiness);
		System.out.println(seatSearchBookEconomic + "afterremove" + seatSearchBookBusiness);
		if (seatSearchBookEconomic == true && seatSearchBookBusiness == true && seatSearchEconomic == false
				&& seatSearchBusiness == false) {

			Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
			List<Integer> integerList = newEconomicList.stream().map(Integer::valueOf).collect(Collectors.toList());
			List<Integer> integerBList = newBusinessList.stream().map(Integer::valueOf).collect(Collectors.toList());
			dict.put(constant.Business, integerBList);
			dict.put(constant.Economic, integerList);
			JSONObject newSeatJson = new JSONObject(dict);
			flightRepository.updateSeat(newSeatJson.toString(), flightId);

			Map<String, List<Integer>> dictBook = new HashMap<String, List<Integer>>();
			List<Integer> integerListBook = newEconomicBookList.stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			List<Integer> integerBListBook = newBusinessBookList.stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			dictBook.put(constant.Business, integerBListBook);
			dictBook.put(constant.Economic, integerListBook);
			JSONObject newSeatBookJson = new JSONObject(dictBook);
			bookingFlight.updateSeat(newSeatBookJson.toString(), bookDeails.getBookingid());
			Instant instant1 = Instant.parse(bookedTime);
			Instant now = Instant.now();
			long difference = ChronoUnit.HOURS.between(instant1, now);
			// Cancel Amount
			JSONObject ruleJson = new JSONObject(cancelRule);
			JSONObject price = new JSONObject(seatPrice);
			int business = (int) price.get(constant.Business);
			int economic = (int) price.get(constant.Economic);
			int offers = (int) ruleJson.get(constant.Offers);
			int cancelfour = (int) ruleJson.get(constant.CancelWithin4);
			int cancelationCharge = (int) ruleJson.get(constant.CancelationCharge);
			int aftercancel = (int) ruleJson.get(constant.Before1hr);
			int afterAppliedCharge = 0;
			int economicPrice;
			int businessPrice;

			if (selectEconomicList.get(0) != "") {
				economicPrice = selectEconomicList.size() * economic;
			} else {
				economicPrice = selectEconomicList.size() * 0;
			}
			if (selectBusinessList.get(0) != "") {
				businessPrice = selectBusinessList.size() * business;
			} else {
				businessPrice = selectBusinessList.size() * 0;
			}
			int totalAmount = economicPrice + businessPrice;
			TransactionStatus transaction = new TransactionStatus();
			transaction.setTransferstatus("Cancel");
			transaction.setPaymentoption("Card");
			transaction.setCardnumber(cardNumber);
			transaction.setCvv(111);
			transaction.setValidthrough("11/2030");
			transaction.setUserid(userId);
			int max = 0;
			try {
				max = transactionRepository.maxTransactionId();
			} catch (Exception e) {
				max = 0;
			}

			String transactionID = "TRA" + utilities.randomNumber(5, max);
			transaction.setTransactionid(transactionID);
			transaction.setBookingid(bookDeails.getBookingid());
			transaction.setBookingsystem("Flight");

			if (difference <= 4) {
				afterAppliedCharge = (totalAmount * cancelfour) / 100;
				System.out.println("insideif" + afterAppliedCharge);
			}

			else {

				afterAppliedCharge = (totalAmount * aftercancel) / 100;
				System.out.println("outsideIF" + afterAppliedCharge);
			}

			String emailUserId = userRepository.getEmailId(userId);
			afterAppliedCharge = afterAppliedCharge - (afterAppliedCharge * cancelationCharge) / 100;
			String mailContent = "Cancelation Successful your Canceled Seat is" + seatJson + "Seat Amount" + totalAmount
					+ "After Cancelation Charges Applied The amount will transerd to your account within 2days amount is"
					+ afterAppliedCharge;

			sendEmail(mailContent, emailUserId);
			transaction.setAmount(afterAppliedCharge);
			System.out.println("time Difference" + bookedTime + "n" + now.toString() + "diff" + difference + "cancel"
					+ cancelfour + aftercancel + "aaa " + afterAppliedCharge + "bbb " + totalAmount);
			transactionRepository.save(transaction);
			response.setStatus(200);
			response.setMessage(constant.CANCELED + constant.AMOUNT + afterAppliedCharge);

		} else {
			response.setStatus(404);
			response.setMessage("Selected Seat and Booking ID Not Matched Please Check");
		}

		return response;

	}

	/**
	 * This Service method is used to delete Flight With using Flight ID.
	 *
	 * @Value used FlightDelete.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "flightid":28039 }
	 *
	 */
	@Override
	public ResponseObject deleteFlight(FlightDelete delete) throws SQLException {
		// TODO Auto-generated method stub
		int flightId = 0;
		try {
			flightId = flightRepository.getFlightId(delete.getFlightid());
		} catch (Exception e) {

		}
		System.out.println("Flight ID" + flightId);
		if (flightId != 0) {
			flightRepository.deleteFlight(flightId);
			response.setStatus(200);
			response.setMessage(constant.FLIGHTID + delete.getFlightid() + constant.DELETED);
		} else {
			response.setStatus(404);
			response.setMessage(constant.NOTMATCHEDFLIGHTID);
		}
		return response;

	}

	/**
	 * This Service method is used to update Cancelation and offer Details.
	 *
	 * datas Are updated with system id and system name
	 *
	 *
	 *
	 * @Value used CancelationOffer.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "systemid":15458, "within4hr":100, "after4hr":40,
	 *         "cancelationcharge":10, "offers":1, "booking_system":"Flight" }
	 *
	 */
	@Override
	public ResponseObject cancelationRuleUpdate(CancelationOffer cancelUpdate) throws SQLException {
		// TODO Auto-generated method stub

		int systemId = 0;
		String bookingSystem = null;
		try {

			systemId = cancelationRuleRepository.getSystemId(cancelUpdate.getSystemid());
			bookingSystem = cancelationRuleRepository.getbookSystem(systemId);
		} catch (Exception e) {

		}
		if (systemId == 0) {
			response.setStatus(404);
			response.setMessage(constant.NOTMATCHEDFLIGHTID);
			return response;
		}
		if (bookingSystem.equals(cancelUpdate.getBooking_system())) {
			Map<String, Integer> ruleMap = new HashMap<String, Integer>();
			ruleMap.put(constant.CancelWithin4, cancelUpdate.getWithin4hr());
			ruleMap.put(constant.Before1hr, cancelUpdate.getAfter4hr());
			ruleMap.put(constant.CancelationCharge, cancelUpdate.getCancelationcharge());
			ruleMap.put(constant.Offers, cancelUpdate.getOffers());
			JSONObject ruleJson = new JSONObject(ruleMap);
			String defaultRules = ruleJson.toString();
			cancelationRuleRepository.updateCancelRule(defaultRules, cancelUpdate.getBooking_system(),
					cancelUpdate.getSystemid());

			response.setStatus(200);
			response.setMessage(cancelUpdate.getSystemid() + "Flight rules has been Updated");
		} else {
			response.setStatus(404);
			response.setMessage(constant.SYSTEMNAMEINCURRECT);
		}
		return response;
	}

	/**
	 * This Service method is used to Add Theater Details.
	 *
	 * need a unique name for add theater after that theater got a unique theater id
	 * we can procees all other activities with this id
	 *
	 * Unique theater name is allowed
	 *
	 * @Value used TheatreDetails.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "times": "10am", "theatrename": "INOX1", "price":
	 *         "{economic:100,business:200}", "economyclassseats": 20,
	 *         "businessclassseats": 20, "fromdate": "21/02/2022", "todate":
	 *         "28/03/2022", "moviename": "2", "location": "Thrissur" }
	 *
	 */

	@Override
	public ResponseObject addTheatre(TheatreDetails theatreDetails) throws SQLException {
		// TODO Auto-generated method stub

		int maxTheatreId = 0;
		int businessSeat = 0;
		int economicSeat = 0;
		String theatreName = null;
		//
		try {
			maxTheatreId = theatreRepository.maxFlightId();
			theatreName = theatreRepository.getTheatreName(theatreDetails.getTheatrename());
		} catch (Exception e) {
			// Logger
		}
		int newTheateId = utilities.randomNumber(5, maxTheatreId);
		System.out.println(maxTheatreId + "value=" + newTheateId);
		if (theatreName == null) {
			theatreDetails.setTheatrename(theatreDetails.getTheatrename());
			theatreDetails.setBusinessclassseats(theatreDetails.getBusinessclassseats());
			theatreDetails.setEconomyclassseats(theatreDetails.getEconomyclassseats());
			//
			List<Integer> businessList = new ArrayList<>();
			List<Integer> economicList = new ArrayList<>();
			Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
			businessSeat = theatreDetails.getBusinessclassseats();
			economicSeat = theatreDetails.getEconomyclassseats();
			for (int i = 1; i < businessSeat + 1; i++) {
				businessList.add(i);
			}
			for (int i = 1; i < economicSeat + 1; i++) {
				economicList.add(i);
			}
			dict.put(constant.Business, businessList);
			dict.put(constant.Economic, economicList);

			JSONObject json = new JSONObject(dict);
			theatreDetails.setSeats(json.toString());
			theatreDetails.setTodate(theatreDetails.getTodate());
			theatreDetails.setFromdate(theatreDetails.getFromdate());
			theatreDetails.setTimes(theatreDetails.getTimes());
			theatreDetails.setMoviename(theatreDetails.getMoviename());
			theatreDetails.setLocation(theatreDetails.getLocation());
			JSONObject priceJson = new JSONObject(theatreDetails.getPrice());
			theatreDetails.setPrice(priceJson.toString());
			theatreDetails.setTheaterid(newTheateId);
			CancelationRules rules = new CancelationRules();
			Map<String, Integer> ruleMap = new HashMap<String, Integer>();
			ruleMap.put(constant.CancelWithin4, 100);
			ruleMap.put(constant.Before1hr, 40);
			ruleMap.put(constant.CancelationCharge, 4);
			ruleMap.put(constant.Offers, 0);
			JSONObject ruleJson = new JSONObject(ruleMap);
			String defaultRules = ruleJson.toString();
			rules.setBookingsystem("Theater");
			rules.setSystemid(newTheateId);
			Instant now = Instant.now();
			rules.setTime(now.toString());
			rules.setRules(defaultRules);
			boolean connection = dataSource.getConnection().isValid(1000);
			if (connection == true) {
				theatreRepository.save(theatreDetails);
				cancelationRuleRepository.save(rules);
				response.setStatus(200);
				response.setMessage(constant.MOVIEADDED + newTheateId);
			} else {
				response.setStatus(404);
				response.setMessage(constant.ConnectionError);
			}
		} else {
			response.setStatus(404);
			response.setMessage(constant.AlREADYEXISTTHEATRE);
		}
		return response;
	}

	/**
	 * This Service method is used to Find Booked Flight Tickets with Booking ID and
	 * User ID.
	 *
	 * This method is get all Booked Details data with given Booking ID is Satisfied
	 *
	 * @Value used SearchFlightTickets.class for Request Entity
	 * @return BookingObject This status Code With Message Value.
	 *
	 *         Input { "userid": 2507, "bookingid":25845 }
	 *
	 */
	@Override
	public BookingObject bookTicketDetails(SearchFlightTickets bookDeails) throws SQLException {
		// TODO Auto-generated method stub
		int bookingId = 0;
		int userid = 0;
		int flightId = 0;
		int systemId = 0;
		int userId = 0;
		try {
			flightId = bookingFlight.searchFlightId(bookDeails.getBookingid());
			systemId = cancelationRuleRepository.getSystemId(flightId);
			userId = bookingFlight.searchUserId(bookDeails.getUserid());
		} catch (Exception e) {
		}
		BookDetails bookDetail = new BookDetails();
		if (flightId != 0 && userId != 0) {

			System.out.println(flightId + "" + userId);

			bookDetail.setBookingid(bookDeails.getBookingid());
			bookDetail.setDate(bookingFlight.getDay(bookDeails.getBookingid()));
			bookDetail.setDestination(flightRepository.getDestination(flightId));
			bookDetail.setFlightid(flightId);
			bookDetail.setUserid(bookDeails.getUserid());
			bookDetail.setSource(flightRepository.getSource(flightId));
			bookDetail.setSeats(bookingFlight.getBookSeats(bookDeails.getBookingid()));
			bookDetail.setTime(flightRepository.getTime(flightId));
			bookingObject.setMessage("messss");
			bookingObject.setStatus(200);
			bookingObject.setBookdetails(bookDetail);

		} else {
			bookingObject.setMessage("messss");
			bookingObject.setStatus(404);
			bookingObject.setBookdetails(null);
		}

		return bookingObject;

	}

	/**
	 * This Service method is used to Find theater with location and Movie Name.
	 *
	 * This method is get all theater data with given condition is Satisfied Date
	 * ,Movie name ,Location
	 *
	 * @Value used CancelBooking.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@Override
	public ResponseObject updateFlight(UpdateFlight update) throws SQLException {
		// TODO Auto-generated method stub
		int flightID = 0;
		try {
			flightID = flightRepository.getFlightId(update.getFlight_id());
		} catch (Exception e) {

		}
		boolean checkDay = utilities.validDay(update.getDates());
		if (checkDay == false) {
			response.setStatus(404);
			response.setMessage(constant.DAYNOTVALID);
			return response;
		}
		if (flightID == update.getFlight_id()) {
			Map<String, Integer> dict = new HashMap<String, Integer>();
			dict.put("business", update.getPrice().getBusiness());
			dict.put("economic", update.getPrice().getEconomic());
			JSONObject price = new JSONObject(dict);
			String[] days = new DateFormatSymbols().getWeekdays();

			flightRepository.updateFlight(update.getDates(), update.getTime(), price.toString(), flightID);
			response.setMessage(
					price + "" + dict + "Details" + update.getDates() + update.getFlight_id() + update.getTime()
					+ update.getPrice().getBusiness() + update.getPrice().getEconomic() + days + checkDay);
			response.setStatus(200);
			return response;
		} else {
			response.setStatus(404);
			response.setMessage(constant.NOTMATCHEDFLIGHTID);
			return response;
		}
	}

	/**
	 * This Service method is used to Find theater with location and Movie Name.
	 *
	 * This method is get all theater data with given condition is Satisfied Date
	 * ,Movie name ,Location
	 *
	 * @Value used CancelBooking.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@Override
	public TheaterObject findTheater(SearchMovie theater) throws SQLException {
		// TODO Auto-generated method stub

		int userid = 0;
		boolean dates = false;
		String fromdate = null;
		String todate = null;
		int userId = 0;
		try {
			userId = userRepository.getUserIds(theater.getUserid());
			fromdate = theatreRepository.getfromDate(theater.getMovie(), theater.getLocation(),
					theater.getNumberofseats());
			todate = theatreRepository.getToDate(theater.getMovie(), theater.getLocation(), theater.getNumberofseats());
		} catch (Exception e) {

		}

		if (fromdate == null && todate == null) {
			theaterObject.setMessage("Theater Not Available in your location");
			theaterObject.setStatus(404);
			theaterObject.setTheatreList(null);
			return theaterObject;
		}

		if (utilities.validateJavaDate(theater.getDate()) == false) {
			theaterObject.setMessage("Please Enter Valid Date format (DD/MM/YYYY)");
			theaterObject.setStatus(404);
			theaterObject.setTheatreList(null);
			return theaterObject;
		}
		if (userId == 0) {
			theaterObject.setMessage("User ID Not Matched");
			theaterObject.setStatus(404);
			theaterObject.setTheatreList(null);
			return theaterObject;
		}

		try {
			dates = utilities.datebetween(fromdate, todate, theater.getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dates == true) {
			theaterObject.setMessage("Available Theater is Listed Out" + dates);
			theaterObject.setStatus(200);
			theaterObject.setTheatreList(theatreRepository.getTheater(theater.getMovie(), theater.getLocation(),
					theater.getNumberofseats()));
			return theaterObject;
		} else {
			theaterObject.setMessage("Theater Not Available in your location");
			theaterObject.setStatus(404);
			theaterObject.setTheatreList(null);
			return theaterObject;
		}
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
	@Override
	public ResponseObject cancelTheaterTicket(CancelBooking bookDeails) throws SQLException {
		// TODO Auto-generated method stub

		String cancelRule = null;
		String seatPrice = null;
		String availableSeat = null;

		String seats = bookDeails.getSeats();
		JSONObject seatJson = new JSONObject(seats);
		String economicSeat = seatJson.get(constant.Economic).toString();
		String businessSeat = seatJson.get(constant.Business).toString();
		List<String> selectEconomicList = utilities.createList(economicSeat);
		List<String> selectBusinessList = utilities.createList(businessSeat);
		System.out.println(selectEconomicList + "" + selectBusinessList);
		int theaterid = 0;
		int systemId = 0;
		int userId = 0;
		String availableBookSeat = null;
		String bookedTime = null;
		String cardNumber = null;
		int max = 0;
		try {
			theaterid = bookingTheaterRepository.searchTheaterId(bookDeails.getBookingid());
			systemId = cancelationRuleRepository.getSystemId(theaterid);
			userId = userRepository.getUserIds(bookDeails.getUserid());
			cancelRule = cancelationRuleRepository.getrule(theaterid);
			seatPrice = theatreRepository.getPrice(theaterid);
			availableSeat = theatreRepository.getSeats(theaterid);
			availableBookSeat = bookingTheaterRepository.getBookSeats(bookDeails.getBookingid());
			bookedTime = bookingTheaterRepository.getBookedTime(bookDeails.getBookingid());
			max = transactionRepository.maxTransactionId();
			// cardNumber = transactionRepository.getCardNumber(bookDeails.getBookingid());

		} catch (Exception e) {
			// logger

			System.out.println("error happened in try" + theaterid + "s" + systemId + "u" + userId + cancelRule
					+ seatPrice + availableSeat + availableBookSeat + bookedTime);
		}

		System.out.println("success happened in try" + theaterid + "s" + systemId + "u" + userId + cancelRule
				+ seatPrice + availableSeat + availableBookSeat);

		JSONObject availableSeatJson = new JSONObject(availableSeat);
		String economicSeats = availableSeatJson.get(constant.Economic).toString();
		String businessSeats = availableSeatJson.get(constant.Business).toString();
		List<String> newEconomicList = utilities.createList(economicSeats);
		List<String> newBusinessList = utilities.createList(businessSeats);
		System.out.println("values" + theaterid + "s" + systemId + "u" + userId + cancelRule + seatPrice + availableSeat
				+ availableBookSeat + bookedTime + "and" + newEconomicList + "" + newBusinessList);

		boolean seatSearchEconomic = utilities.search(newEconomicList, selectEconomicList);
		boolean seatSearchBusiness = utilities.search(newBusinessList, selectBusinessList);
		System.out.println(newEconomicList + "serach" + selectEconomicList);
		System.out.println(newBusinessList + "bookedbissserach" + selectBusinessList);
		if (selectEconomicList.size() == 1) {
			newEconomicList.add(selectEconomicList.get(0));

		} else {
			newEconomicList = utilities.addSeat(newEconomicList, selectEconomicList);

		}
		if (newBusinessList.size() == 1) {
			newBusinessList.add(newBusinessList.get(0));
		} else {
			newBusinessList = utilities.addSeat(newBusinessList, selectBusinessList);
		}
		theatreRepository.updateEconomicSeat(newEconomicList.size(), theaterid);
		theatreRepository.updateBusinessSeat(newBusinessList.size(), theaterid);
		System.out.println(newEconomicList + "added" + newBusinessList);
		JSONObject availableBookSeatJson = new JSONObject(availableBookSeat);
		String economicBookSeats = availableBookSeatJson.get(constant.Economic).toString();
		String businessBookSeats = availableBookSeatJson.get(constant.Business).toString();
		List<String> newEconomicBookList = utilities.createList(economicBookSeats);
		List<String> newBusinessBookList = utilities.createList(businessBookSeats);
		boolean seatSearchBookEconomic = utilities.search(newEconomicBookList, selectEconomicList);
		boolean seatSearchBookBusiness = utilities.search(newBusinessBookList, selectBusinessList);
		System.out.println(newEconomicBookList + "bookedserach" + selectEconomicList);
		System.out.println(newBusinessBookList + "bookedserach" + selectBusinessList);
		if (selectEconomicList.size() == 1) {
			newEconomicBookList.remove(selectEconomicList.get(0));

		} else {
			newEconomicBookList = utilities.removeSeat(newEconomicBookList, selectEconomicList);

		}
		if (selectBusinessList.size() == 1) {
			newBusinessBookList.remove(selectBusinessList.get(0));
		} else {
			newBusinessBookList = utilities.removeSeat(newBusinessBookList, selectBusinessList);
		}
		System.out.println(newEconomicBookList + "afterremove" + newBusinessBookList);
		System.out.println(seatSearchBookEconomic + "" + seatSearchBookBusiness + "" + seatSearchEconomic + ""
				+ seatSearchBusiness);
		System.out.println(seatSearchBookEconomic + "afterremove" + seatSearchBookBusiness);
		if (seatSearchBookEconomic == true && seatSearchBookBusiness == true && seatSearchEconomic == false
				&& seatSearchBusiness == false) {
			System.out.println(seatSearchBookEconomic + "afterremoveinside" + seatSearchBookBusiness);
			Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
			List<Integer> integerList = newEconomicList.stream().map(Integer::valueOf).collect(Collectors.toList());
			List<Integer> integerBList = newBusinessList.stream().map(Integer::valueOf).collect(Collectors.toList());
			dict.put(constant.Business, integerBList);
			dict.put(constant.Economic, integerList);
			JSONObject newSeatJson = new JSONObject(dict);
			theatreRepository.updateSeat(newSeatJson.toString(), theaterid);
			System.out.println(seatSearchBookEconomic + "afterremove1358" + newSeatJson);
			Map<String, List<Integer>> dictBook = new HashMap<String, List<Integer>>();
			List<Integer> integerListBook = newEconomicBookList.stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			List<Integer> integerBListBook = newBusinessBookList.stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			dictBook.put(constant.Business, integerBListBook);
			dictBook.put(constant.Economic, integerListBook);
			JSONObject newSeatBookJson = new JSONObject(dictBook);
			System.out.println(seatSearchBookEconomic + "afterremove1367" + newSeatBookJson);
			bookingTheaterRepository.updateSeat(newSeatBookJson.toString(), bookDeails.getBookingid());
			Instant instant1 = Instant.parse(bookedTime);
			Instant now = Instant.now();
			long difference = ChronoUnit.HOURS.between(instant1, now);
			// Cancel Amount
			System.out.println(seatSearchBookEconomic + "difference" + seatSearchBookBusiness);
			JSONObject ruleJson = new JSONObject(cancelRule);
			JSONObject price = new JSONObject(seatPrice);
			int business = (int) price.get(constant.Business);
			int economic = (int) price.get(constant.Economic);
			int offers = (int) ruleJson.get(constant.Offers);
			int cancelfour = (int) ruleJson.get(constant.CancelWithin4);
			int cancelationCharge = (int) ruleJson.get(constant.CancelationCharge);
			int aftercancel = (int) ruleJson.get(constant.Before1hr);
			int afterAppliedCharge = 0;
			int economicPrice;
			int businessPrice;

			if (selectEconomicList.get(0) != "") {
				economicPrice = selectEconomicList.size() * economic;
			} else {
				economicPrice = selectEconomicList.size() * 0;
			}
			if (selectBusinessList.get(0) != "") {
				businessPrice = selectBusinessList.size() * business;
			} else {
				businessPrice = selectBusinessList.size() * 0;
			}
			int totalAmount = economicPrice + businessPrice;
			if (difference <= 4) {
				afterAppliedCharge = (totalAmount * cancelfour) / 100;
				System.out.println("insideif" + afterAppliedCharge);
			}

			else {
				afterAppliedCharge = (totalAmount * aftercancel) / 100;
				System.out.println("outsideIF" + afterAppliedCharge);
			}

			String emailUserId = userRepository.getEmailId(userId);
			afterAppliedCharge = afterAppliedCharge - (afterAppliedCharge * cancelationCharge) / 100;
			String mailContent = "Cancelation Successful your Canceled Seat is" + seatJson + "Seat Amount" + totalAmount
					+ "After Cancelation Charges Applied The amount will transerd to your account within 2days amount is"
					+ afterAppliedCharge;

			sendEmail(mailContent, emailUserId);
			System.out.println("time Difference" + bookedTime + "n" + now.toString() + "diff" + difference + "cancel"
					+ cancelfour + aftercancel + "aaa " + afterAppliedCharge + "bbb " + totalAmount);

			TransactionStatus transaction = new TransactionStatus();

			transaction.setTransferstatus("Cancel");
			transaction.setPaymentoption("Card");
			transaction.setUserid(userId);

			transaction.setAmount(afterAppliedCharge);
			String transactionID = "TRA" + utilities.randomNumber(5, max);
			transaction.setTransactionid(transactionID);
			transaction.setBookingid(bookDeails.getBookingid());
			transaction.setCardnumber("12345678");
			transaction.setCvv(111);
			transaction.setValidthrough("11/2030");
			transaction.setUserid(userId);
			transaction.setBookingsystem("Theater");

			transactionRepository.save(transaction);
			response.setStatus(200);
			response.setMessage(constant.CANCELED + constant.AMOUNT + afterAppliedCharge);

		} else {
			response.setStatus(404);
			response.setMessage("Selected Seat and Booking ID Not Matched Please Check");
		}

		return response;
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
	@Override
	public ResponseObject bookTheaterTicketsConfirm(TransactionStatus bookDeails) throws SQLException {
		// TODO Auto-generated method stub
		int bookidcount = bookingTheaterRepository.searchId(bookDeails.getBookingid());
		int max = 0;
		String mail = null;
		String paymentStatus = null;
		int userid = 0;
		int bookAmount = 0;

		try {
			bookAmount = bookingTheaterRepository.amount(bookDeails.getBookingid());
			paymentStatus = bookingTheaterRepository.getPaymentStatus(bookDeails.getBookingid());
			max = transactionRepository.maxTransactionId();
			userid = bookingTheaterRepository.getbookUserId(bookDeails.getBookingid());

		} catch (Exception e) {
			max = 0;
		}
		System.out.println(paymentStatus + "paymnetStatus" + bookAmount + "aa" + bookidcount);
		if (bookAmount != bookDeails.getAmount()) {
			response.setStatus(404);
			response.setMessage(constant.AmountNotMatch);
			return response;
		}
		if (bookidcount != 0 && bookAmount == bookDeails.getAmount() && paymentStatus.equals("Pending")) {
			bookingTheaterRepository.updatePayment("Sucess", bookDeails.getBookingid());
			bookingTheaterRepository.updateCard(bookDeails.getCardnumber(), bookDeails.getBookingid());

			bookDeails.setTransferstatus("Booking");
			bookDeails.setPaymentoption(bookDeails.getPaymentoption());
			bookDeails.setUserid(bookDeails.getUserid());
			int selectAmount = bookingTheaterRepository.amount(bookDeails.getBookingid());
			bookDeails.setAmount(selectAmount);
			String transactionID = "TRA" + utilities.randomNumber(5, max);
			bookDeails.setTransactionid(transactionID);
			bookDeails.setBookingid(bookDeails.getBookingid());
			bookDeails.setCardnumber(bookDeails.getCardnumber());
			bookDeails.setCvv(bookDeails.getCvv());
			bookDeails.setValidthrough(bookDeails.getValidthrough());
			bookDeails.setUserid(userid);
			bookDeails.setBookingsystem("Theater");
			transactionRepository.save(bookDeails);

			response.setStatus(200);
			response.setMessage(constant.PAYMENTSUCCESS);
		} else {
			response.setStatus(404);
			response.setMessage(constant.PAYMENTNOTSUCCESS);
		}
		return response;
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
	@Override
	public TheaterBookingObject bookTheaterTicketDetails(SearchFlightTickets bookDeails) throws SQLException {
		// TODO Auto-generated method stub

		int bookidcount = bookingTheaterRepository.searchId(bookDeails.getBookingid());
		int useridCount = bookingTheaterRepository.searchUserId(bookDeails.getBookingid());
		if (useridCount == 0 && bookidcount == 0) {
			theaterBookingObject.setStatus(404);
			theaterBookingObject.setMessage("Please Check Booking ID");

		} else {
			theaterBookingObject.setStatus(202);
			theaterBookingObject.setMessage("Bookind Details Are");
			theaterBookingObject
			.setTheatreList(bookingTheaterRepository.theaterBookingDetails(bookDeails.getBookingid()));

		}
		return theaterBookingObject;
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
	@Override
	public ResponseObject updateTheater(UpdateTheater update) throws SQLException {
		// TODO Auto-generated method stub

		int theaterID = 0;
		try {
			theaterID = theatreRepository.getTheatreid(update.getTheaterid());
		} catch (Exception e) {

		}
		boolean checkDay = utilities.validateJavaDate(update.getTodate());
		boolean checkDayfrom = utilities.validateJavaDate(update.getFromdate());
		if (checkDay == false && checkDayfrom == false) {
			response.setStatus(404);
			response.setMessage("Please Enter Valid Day DD/MM/YYYY");
			return response;
		}
		if (theaterID == update.getTheaterid()) {
			Map<String, Integer> dict = new HashMap<String, Integer>();
			dict.put("business", update.getPrice().getBusiness());
			dict.put("economic", update.getPrice().getEconomic());
			JSONObject price = new JSONObject(dict);
			String[] days = new DateFormatSymbols().getWeekdays();

			theatreRepository.updateTheater(update.getFromdate(), update.getTodate(), update.getMoviename(),
					price.toString(), theaterID);
			response.setMessage(
					price + "" + dict + "Details" + update.getFromdate() + update.getTheaterid() + update.getFromdate()
					+ update.getPrice().getBusiness() + update.getPrice().getEconomic() + days + checkDay);
			response.setStatus(200);
			return response;
		} else {
			response.setStatus(404);
			response.setMessage("Theater ID Not Matched");
			return response;
		}

	}

	/**
	 * This Service method is used to delete theater With theater ID.
	 *
	 * @Value used TheaterDelete.class for Request Entity
	 * @return ResponseObject This status Code With Message Value.
	 *
	 *         Input { "theaterid":28039 }
	 *
	 */
	@Override
	public ResponseObject deleteTheater(TheaterDelete delete) throws SQLException {
		int theaterID = 0;
		try {
			theaterID = theatreRepository.getTheatreid(delete.getTheaterid());
		} catch (Exception e) {

		}
		System.out.println("Flight ID" + theaterID);
		if (theaterID != 0) {
			theatreRepository.deleteTheater(theaterID);
			response.setStatus(200);
			response.setMessage("Theater ID " + delete.getTheaterid() + constant.DELETED);
		} else {
			response.setStatus(404);
			response.setMessage(constant.NOTMATCHEDFLIGHTID);
		}
		return response;

	}
}