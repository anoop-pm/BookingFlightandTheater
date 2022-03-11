package com.example.bookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingsystem.entity.FlightDetails;

@Repository
public interface FlightRepository extends JpaRepository<FlightDetails, Long> {

	@Query(value = "select max(id) from flight_details", nativeQuery = true)
	Integer maxFlightId();

	@Query(value = "select flight_name from flight_details u where u.flight_name =:flightName", nativeQuery = true)
	String getFlightName(@Param("flightName") String flightName);

	@Query(value = "select flight_id from flight_details u where u.flight_id =:flightId", nativeQuery = true)
	Integer getFlightId(@Param("flightId") int flightId);

	@Query(value = "select * from flight_details u where u.dates =:day and u.destination=:destination and u.source=:source and u.economy_class_seats + u.business_class_seats >= :noOfSeat", nativeQuery = true)
	List<FlightDetails> getFlight(@Param("day") String day, @Param("destination") String destination,
			@Param("source") String source,@Param("noOfSeat") int noOfSeat);

	@Query(value = "select seats from flight_details u where u.flight_id =:flightId", nativeQuery = true)
	String getSeats(@Param("flightId") int flightId);

	@Query(value = "select price from flight_details u where u.flight_id =:flightPriceId", nativeQuery = true)
	String getPrice(@Param("flightPriceId") int flightPriceId);

	@Query(value = "select dates from flight_details u where u.flight_id =:flightDateId", nativeQuery = true)
	String getDate(@Param("flightDateId") int flightDateId);

	@Query(value = "select destination from flight_details u where u.flight_id =:flightDestId", nativeQuery = true)
	String getDestination(@Param("flightDestId") int flightDestId);

	@Query(value = "select source from flight_details u where u.flight_id =:flightSourceId", nativeQuery = true)
	String getSource(@Param("flightSourceId") int flightSourceId);

	@Query(value = "select dispaturetimestimes from flight_details u where u.flight_id =:flightTimeId", nativeQuery = true)
	String getTime(@Param("flightTimeId") int flightTimeId);

	@Query(value = "select economy_class_seats from flight_details u where u.flight_id =:flightEcoSeatId", nativeQuery = true)
	Integer getNumberEconomicSeat(@Param("flightEcoSeatId") int flightEcoSeatId);

	@Query(value = "select business_class_seats from flight_details u where u.flight_id =:flightBuseatId", nativeQuery = true)
	Integer getNumberBusinessSeat(@Param("flightBuseatId") int flightBuseatId);

	@Query(value = "select flight_name from flight_details u where u.flight_id =:flightNameByid", nativeQuery = true)
	String getFlightNameByid(@Param("flightNameByid") int flightNameByid);

	@Transactional
	@Modifying
	@Query("UPDATE FlightDetails SET seats   = :newseat WHERE  flight_id = :seatflightid")
	Integer updateSeat(String newseat, int seatflightid);

	@Transactional
	@Modifying
	@Query("UPDATE FlightDetails SET economy_class_seats = :newEconomicSeat WHERE  flight_id = :newEconomicSeatId")
	Integer updateEconomicSeat(int newEconomicSeat, int newEconomicSeatId);

	@Transactional
	@Modifying
	@Query("UPDATE FlightDetails SET dates = :updatedates,dispaturetimestimes = :depature,price=:newPrice WHERE  flight_id = :updateWithFlightID")
	Integer updateFlight(String updatedates,String depature,String newPrice,int updateWithFlightID);

	@Transactional
	@Modifying
	@Query("UPDATE FlightDetails SET business_class_seats = :newBusinessSeat WHERE  flight_id = :newBusinessSeatId")
	Integer updateBusinessSeat(int newBusinessSeat, int newBusinessSeatId);

	@Transactional
	@Modifying
	@Query("DELETE FROM FlightDetails WHERE  flight_id = :deletebyflightid")
	Integer deleteFlight(int deletebyflightid);

}
