package com.example.bookingsystem.constant;

public class BookingConstant {

	public static final String UserCreated = "User registered successfully and your user id is ";
	public static final int StatusOk = 200;
	public static final int StatusError = 404;
	public static final String ConnectionError = " Error server issue please try Later . ";
	public static final String EmailPhoneExist = " hone Number and Email already exist . ";
	public static final String Business = "business";
	public static final String Economic = "economic";
	public static final String CancelWithin4 = "within4hr";
	public static final String Before1hr = "after4hr";
	public static final String CancelationCharge = "cancelationcharge";
	public static final String Offers = "offers";
	public static final String FlightDetails = " Flight details added successfully and flight ID is ";
	public static final String FlightName = " Flight name already exist . ";
	public static final String FlightDetailsList = " Available flights are ";
	public static final String FlightNotAvailable = " Sorry no flights are available . ";
	public static final String BookUserFlightId = " User id or Booking id not found .  ";

	public static final String PAYABLEAMOUNT = " Total bill amount is  ";
	public static final String PassengerDetails = " Passenger Details  ";
	public static final String CONFIRMBILL = " For confirm the ticket please pay the bill .";

	public static final String AmountNotMatch = " Payment not successful .Entered amount not matched . ";

	public static final String PAYMENTSUCCESS = " Payment successful . Booking details send to your email id .";

	public static final String PAYMENTNOTSUCCESS = " Payment not successful . Booking id not exist . ";

	public static final String NOTMATCHBOOKING = " Booking Id has not been matched .";
	public static final String CANCELED = " You cancelled tickets sucessfully . Refund Amount will Send to your account . Cancelation charge Will Applied . ";

	public static final String AMOUNT = " Amount is ";
	public static final String FLIGHTID = " flight ID ";
	public static final String DELETED = " has been deleted ";
	public static final String NOTMATCHEDFLIGHTID = "Selected system ID  does not matched . please check your given id . ";

	public static final String MOVIEADDED = " New movie and theater added successfully theater ID is  ";

	public static final String AlREADYEXISTTHEATRE = " Theater all ready exist ";
	public static final String SYSTEMNAMEINCURRECT = " Booking system not matched . Please check entered system name Like [Flight or Theater] ";
	public static final String DATENOTVALID = "	Date is not valid format . please follow DD/MM/YEAR ";
	public static final String DAYNOTVALID = "	Please enter valid week day [Sunday Monday Tuesday Wednesday Thursday Friday  Saturday] ";

	public static final String THEATERIDNOTVALID = " Theater id not Exist please give valid Theater ID ";

	public static final String MOVIETICKETBOOKED = " Movie ticket booked successFully . Please confirm your ticket . Your booking id is ";

	public static final String CANCELLATIONRULE = " You can cancel the tickets at any time . And cancelation charges applied 4% ,If you cancel the ticket with in 4 hour You got full amount refund other vice 60% cancelation charges applied  ";
	public static final String THEATERNOTAVAILABLE = "	Theater not available in your location  ";
	public static final String VALIDDATES = "	PLease enter valid Date . Please follow DD/MM/YYYY ";

	//SWAGGER
	public static final String PARAMDOCADDFLIGHT = " Add flight details Object store in database table ";
	public static final String ADDFLIGHTDOC = "	Add Flight Details ";
	public static final String PARAMDOCADDTHEATER = " Add Theater Details Object store in database table ";
	public static final String ADDTHEATERTDOC = " Add Theater Details ";

	public static final String PARAMDOCUPDATETHEATER = " Update Theater Details Object store in database table ";
	public static final String UPDATETHEATERTDOC = " Update Theater Details ";
	public static final String UPDATEFLIGHTDOC = " Update Flight Details ";
	public static final String PARAMDOCUPDATEFLIGHT = " Update Flight Details Object store in database table ";

	public static final String DELETEFLIGHTDOC = " Delete Flight Details ";
	public static final String PARAMDOCDELETEFLIGHT = " Delete Flight Details Object ";
	public static final String DELETETHEATERDOC = " Delete Theater Details ";
	public static final String PARAMDOCDELETETHEATER = " Delete Theater Details Object ";

	public static final String PARAMDOCUPDATECANCELATIONRULE = " Update cancelation and rules details object store in database table ";

	public static final String USERREGISTER = " USER REGISTRATION ";
	public static final String PARAMDOCUPDATEUSER = " ADD User Object store in database table ";
	public static final String PARAMDOCBOOKFLIGHT = " ADD BOOK FLight Object store in database table And Update Flight seats ";
	public static final String PARAMDOCBOOKTheater = " ADD BOOK Theater Object store in database table And Update Theater seats ";
	public static final String PARAMDOCCANCELFLIGHT = " CANCEL BOOK FLight Object store in database table And Update Flight seats and Booked Seats ";
	public static final String PARAMDOCCANCELTheater = " CANCRL BOOK Theater Object store in database table And Update Theater seats and Booked Seats ";
	public static final String PARAMDOCSHOWTheater = " SHOW Theater Details";
	public static final String PARAMDOCSHOWFLIGHT = " SHOW Flight Details ";
	public static final String PARAMDOCSHOWBOOKTheater = " SHOW BOOK Theater Details";
	public static final String PARAMDOCSHOWBOOKFLIGHT = " SHOW BOOK Flight Details ";
	public static final String SEATNOTMATCH = " Selected Seat or Booking ID Not Matched Please Check ";

	public static final String CANCELSUCESS = "Cancelation Successful your Canceled Seat is";
	public static final String UPDATETHEATER= "Update Theater SuccessFully Your Current Seat Price Is";
	public static final String CANCELSUCESSCHARGE = "After Cancelation Charges Applied The amount will transerd to your account within 2days amount is";



}
