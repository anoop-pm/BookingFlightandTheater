package com.example.bookingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingsystem.entity.User;

@Repository
public interface UserRepository extends  JpaRepository<User, Long>{


	@Query(value = "select count(email) from users u where u.email =:email", nativeQuery = true)//change to count
	String getEmailAddress(@Param("email") String email);

	@Query(value = "select count(phonenumber) from users u where u.phonenumber =:phone", nativeQuery = true)
	String getPhoneNumber(@Param("phone") String phone);

	@Query(value = "select max(id) from users", nativeQuery = true)
	Integer maxuserid();

	@Query(value = "select userid from users u where u.userid =:uid", nativeQuery = true)
	Integer getUserIds(@Param("uid") int uid);

	@Query(value = "select email from users u where u.userid =:userid", nativeQuery = true)//change to count
	String getEmailId(@Param("userid") int userid);

	@Query(value = "select email from users", nativeQuery = true)//change to count
	List<String> allEmail();

}
