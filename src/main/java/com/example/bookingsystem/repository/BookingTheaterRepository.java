package com.example.bookingsystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingsystem.entity.TheaterBooking;

@Repository
public interface BookingTheaterRepository extends JpaRepository<TheaterBooking, Long> {

	@Query(value = "select max(id) from theater_booking", nativeQuery = true)
	Integer maxBookId();

	@Query(value = "select userid from theater_booking u where u.bookingid =:userbookid", nativeQuery = true)
	Integer getbookUserId(@Param("userbookid") int userbookid);


	@Query(value = "select theaterid from theater_booking u where u.bookingid=:searchBookingID ", nativeQuery = true)
	Integer searchTheaterId(@Param("searchBookingID") int searchBookingID);

	@Query(value = "select seats from theater_booking u where u.bookingid =:bookid", nativeQuery = true)
	String getBookSeats(@Param("bookid") int bookid);

	@Query(value = "select time from theater_booking u where u.bookingid =:bookTimeid", nativeQuery = true)
	String getBookedTime(@Param("bookTimeid") int bookTimeid);

	@Query(value = "select count(bookingid) from theater_booking u where u.bookingid=:searchBookingID ", nativeQuery = true)
	Integer searchId(@Param("searchBookingID") int searchBookingID);

	@Query(value = "select count(userid) from theater_booking u where u.bookingid=:searchBookingUserID ", nativeQuery = true)
	Integer searchUserId(@Param("searchBookingUserID") int searchBookingUserID);

	@Query(value = "select amount from theater_booking u where u.bookingid=:searchBookingByAmountID ", nativeQuery = true)
	Integer amount(@Param("searchBookingByAmountID") int searchBookingByAmountID);


	@Query(value = "select paymentoption from theater_booking u where u.bookingid =:bookstatusid", nativeQuery = true)
	String getPaymentStatus(@Param("bookstatusid") int bookstatusid);

	@Query(value = "select * from theater_booking u where u.bookingid =:searchBybookid", nativeQuery = true)
	List<TheaterBooking> theaterBookingDetails(@Param("searchBybookid") int searchBybookid);

	@Transactional
	@Modifying
	@Query("UPDATE TheaterBooking SET seats   = :newseat WHERE  bookingid = :seatflightid")
	Integer updateSeat(String newseat, int seatflightid);


	@Transactional
	@Modifying
	@Query("UPDATE TheaterBooking SET paymentoption = :status WHERE  bookingid = :bookingID")
	Integer updatePayment(String status, int bookingID);

	@Transactional
	@Modifying
	@Query("UPDATE TheaterBooking SET cardnumber = :card WHERE  bookingid = :bookingcardID")
	Integer updateCard(String card, int bookingcardID);






}
