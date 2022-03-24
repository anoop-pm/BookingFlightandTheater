package com.example.bookingsystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingsystem.entity.FlightBooking;

@Repository
public interface BookingFlightRepository extends JpaRepository<FlightBooking, Long> {

	@Query(value = "select max(id) from flight_booking", nativeQuery = true)
	Integer maxBookId();

	@Query(value = "select id from flight_booking u where u.booking_id =:uid", nativeQuery = true)
	Integer getBookMapId(@Param("uid") int uid);

	@Query(value = "select user_id from flight_booking u where u.booking_id =:userbookid", nativeQuery = true)
	Integer getbookUserId(@Param("userbookid") int userbookid);

	@Query(value = "select count(booking_id) from flight_booking u where u.booking_id=:searchBookingID ", nativeQuery = true)
	Integer searchId(@Param("searchBookingID") int searchBookingID);

	@Query(value = "select count(user_id) from flight_booking u where u.user_id=:searchBookingUserID ", nativeQuery = true)
	Integer searchUserId(@Param("searchBookingUserID") int searchBookingUserID);

	@Query(value = "select user_id from flight_booking u where u.flight_id =:flightsById", nativeQuery = true)
	List<Integer> getUserIdForReport(@Param("flightsById") int flightsById);

	@Query(value = "select flight_id from flight_booking u where u.booking_id=:searchBookingID ", nativeQuery = true)
	Integer searchFlightId(@Param("searchBookingID") int searchBookingID);

	@Query(value = "select amount from flight_booking u where u.booking_id=:searchBookingByAmountID ", nativeQuery = true)
	Integer amount(@Param("searchBookingByAmountID") int searchBookingByAmountID);

	@Query(value = "select seats from flight_booking u where u.booking_id =:bookid", nativeQuery = true)
	String getBookSeats(@Param("bookid") int bookid);

	@Query(value = "select mail from flight_booking u where u.booking_id =:bookmailid", nativeQuery = true)
	String getMailId(@Param("bookmailid") int bookmailid);

	@Query(value = "select bookedtime from flight_booking u where u.booking_id =:bookTimeid", nativeQuery = true)
	String getBookedTime(@Param("bookTimeid") int bookTimeid);

	@Query(value = "select paymentstatus from flight_booking u where u.booking_id =:bookstatusid", nativeQuery = true)
	String getPaymentStatus(@Param("bookstatusid") int bookstatusid);

	@Query(value = "select date from flight_booking u where u.booking_id =:bookdaybyid", nativeQuery = true)
	String getDay(@Param("bookdaybyid") int bookdaybyid);

	@Query(value = "select * from flight_booking u where u.user_id =:searchWithUserId", nativeQuery = true)
	Object getIdAndBookingId(@Param("searchWithUserId") int searchWithUserId);


	@Transactional
	@Modifying
	@Query("UPDATE FlightBooking SET paymentstatus = :status WHERE  booking_id = :bookingID")
	Integer updatePayment(String status, int bookingID);

	@Transactional
	@Modifying
	@Query("UPDATE FlightBooking SET paymentoption = :paymentoption WHERE  booking_id = :bookingpayID")
	Integer updatePaymentOption(String paymentoption, int bookingpayID);

	@Transactional
	@Modifying
	@Query("UPDATE FlightBooking SET seats   = :newseat WHERE  booking_id = :seatflightid")
	Integer updateSeat(String newseat, int seatflightid);

}
