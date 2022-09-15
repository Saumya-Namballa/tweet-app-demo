package com.tweetapp.tweet.repository;

import java.util.List;

import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tweetapp.tweet.model.RegisterUser;

public interface UserRepository extends JpaRepository<RegisterUser, String>{
	
	RegisterUser  findByLoginID(String loginID);
	
	@Query(value="select loginid from register_user", nativeQuery=true)
	List<Object> findAllUsers();

	RegisterUser findByEmailID(String emailID);
}
