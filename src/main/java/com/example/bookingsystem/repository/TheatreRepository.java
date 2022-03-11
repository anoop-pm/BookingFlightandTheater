package com.example.bookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingsystem.entity.TheatreDetails;


public interface TheatreRepository extends JpaRepository<TheatreDetails, Long> {

	@Query(value = "select max(id) from theatre_details", nativeQuery = true)
	Integer maxFlightId();

	@Query(value = "select theatre_name from theatre_details u where u.theatre_name =:theatreName", nativeQuery = true)
	String getTheatreName(@Param("theatreName") String theatreName);

	@Query(value = "select theater_id from theatre_details u where u.theater_id =:theatreID", nativeQuery = true)
	Integer getTheatreid(@Param("theatreID") int theatreID);

	@Query(value = "select seats from theatre_details u where u.theater_id =:getseatbyId", nativeQuery = true)
	String getSeats(@Param("getseatbyId") int getseatbyId);

	@Query(value = "select location from theatre_details u where u.theater_id =:getlocationbyId", nativeQuery = true)
	String getLocation(@Param("getlocationbyId") int getlocationbyId);

	//	@Query(value = "select location from theatre_details u where u.theater_id =:getlocationbyId", nativeQuery = true)
	//	String getDate(@Param("getlocationbyId") int getlocationbyId);

	@Query(value = "select price from theatre_details u where u.theater_id =:PriceById", nativeQuery = true)
	String getPrice(@Param("PriceById") int PriceById);


	@Query(value = "select * from theatre_details u where u.moviename =:movie and u.location=:location  and u.economy_class_seats + u.business_class_seats >= :noOfSeat", nativeQuery = true)
	List<TheatreDetails> getTheater(@Param("movie") String movie, @Param("location") String location,@Param("noOfSeat") int noOfSeat);

	@Query(value = "select todate from theatre_details u where u.moviename =:movieto and u.location=:locationto  and u.economy_class_seats + u.business_class_seats >= :noOfSeatto", nativeQuery = true)
	String getToDate(@Param("movieto") String movieto, @Param("locationto") String locationto,@Param("noOfSeatto") int noOfSeatto);

	@Query(value = "select fromdate from theatre_details u where u.moviename =:moviefrom and u.location=:locationfrom  and u.economy_class_seats + u.business_class_seats >= :noOfSeatfrom", nativeQuery = true)
	String getfromDate(@Param("moviefrom") String moviefrom, @Param("locationfrom") String locationfrom,@Param("noOfSeatfrom") int noOfSeatfrom);



	@Transactional
	@Modifying
	@Query("UPDATE TheatreDetails SET seats  = :newseat WHERE  theater_id = :seattheaterid")
	Integer updateSeat(String newseat, int seattheaterid);

	@Transactional
	@Modifying
	@Query("UPDATE TheatreDetails SET economy_class_seats = :newEconomicSeat WHERE  theater_id = :newEconomicSeatId")
	Integer updateEconomicSeat(int newEconomicSeat, int newEconomicSeatId);

	@Transactional
	@Modifying
	@Query("UPDATE TheatreDetails SET business_class_seats = :newBusinessSeat WHERE  theater_id = :newBusinessSeatId")
	Integer updateBusinessSeat(int newBusinessSeat, int newBusinessSeatId);


	@Transactional
	@Modifying
	@Query("UPDATE TheatreDetails SET fromdate = :updatedates,todate = :updatetodates,moviename =:movieNames,price=:newPrice WHERE  theater_id = :updateWithTheaterID")
	Integer updateTheater(String updatedates,String updatetodates,String newPrice,String movieNames,int updateWithTheaterID);

	@Transactional
	@Modifying
	@Query("DELETE FROM TheatreDetails WHERE  theater_id = :deletebytheaterid")
	Integer deleteTheater(int deletebytheaterid);

}
