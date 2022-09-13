package com.tweetapp.tweet.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.repository.UserRepository;

@Service
public class LoginIdValidator {

	@Autowired
	UserRepository repo;
	
	private static final Logger log = LoggerFactory.getLogger(LoginIdValidator.class);
	
	public boolean loginIdValidator(String loginId) {
		RegisterUser id = repo.findByLoginID(loginId);
		if(id!=null && (id.getLoginID()).equals(loginId)) {
			log.info("Valid User!!");
			return true;
		} else {
			log.info("Invalid User!!!");
			return false;
		}
	}
}
