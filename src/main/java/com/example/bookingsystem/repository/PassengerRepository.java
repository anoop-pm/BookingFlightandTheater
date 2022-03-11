package com.example.bookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingsystem.entity.Passenger;

@Repository
public interface PassengerRepository  extends JpaRepository<Passenger, Long> {

	@Query(value = "select * from passenger u where u.b_fk =:bookFkID", nativeQuery = true)
	List<Passenger> getpassenger(@Param("bookFkID") int bookFkID);

	@Query(value = "select name from passenger u where u.b_fk =:bookFkIDN", nativeQuery = true)
	String getpassengername(@Param("bookFkIDN") int bookFkIDN);



}
