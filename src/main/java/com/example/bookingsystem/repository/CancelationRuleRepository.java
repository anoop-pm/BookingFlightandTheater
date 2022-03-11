package com.example.bookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingsystem.entity.CancelationRules;

@Repository
public interface CancelationRuleRepository extends  JpaRepository<CancelationRules,Long>{

	@Query(value = "select system_id from cancelation_rules u where u.system_id =:systemId", nativeQuery = true)
	Integer getSystemId(@Param("systemId") int systemId);

	@Query(value = "select rules from cancelation_rules u where u.system_id =:systemIds", nativeQuery = true)
	String getrule(@Param("systemIds") int systemIds);

	@Query(value = "select booking_system from cancelation_rules u where u.system_id =:systembookIds", nativeQuery = true)
	String getbookSystem(@Param("systembookIds") int systembookIds);

	@Transactional
	@Modifying
	@Query("UPDATE CancelationRules SET rules = :newrules WHERE  system_id = :cancelRuleFlightId and booking_system=:systemName")
	Integer updateCancelRule(String newrules,String systemName,int cancelRuleFlightId);

}
