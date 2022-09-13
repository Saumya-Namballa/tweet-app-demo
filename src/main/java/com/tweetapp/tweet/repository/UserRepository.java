package com.tweetapp.tweet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.model.UserId;

public interface UserRepository extends MongoRepository<RegisterUser, String>{
	
	RegisterUser  findByLoginID(String loginID);
	
	@Query(value="{}",fields="{_id:1}")
	List<UserId> findAllUsers();

	RegisterUser findByEmailID(String emailID);
}
