package com.example.bookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookingsystem.entity.TransactionStatus;

public interface TransactionRepository extends  JpaRepository <TransactionStatus,Long> {

	@Query(value = "select max(id) from transactions", nativeQuery = true)
	Integer maxTransactionId();

	@Query(value = "select cardnumber from transactions u where u.bookingid =:systemcardIds", nativeQuery = true)
	String getCardNumber(@Param("systemcardIds") int systemcardIds);


}
