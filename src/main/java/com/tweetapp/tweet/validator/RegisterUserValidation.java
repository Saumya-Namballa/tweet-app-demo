package com.tweetapp.tweet.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.repository.UserRepository;

@Service
public class RegisterUserValidation {

	@Autowired
	UserRepository repo;

	private static final Logger log = LoggerFactory.getLogger(RegisterUserValidation.class);

	public boolean isValidLoginId(String id) {
		
		RegisterUser user = repo.findByLoginID(id);
		if (user != null) {
			log.info("LoginID not unique");
			return false;
		} else {
			log.info("Unique loginID");
			return true;
		}
	}

	public boolean isValidEmailId(String emailID) {
		RegisterUser user = repo.findByEmailID(emailID);
		if (user != null) {
			log.info("Email not unique");
			return false;

		} else {
			log.info("Unique EmailID");
			return true;
		}
	}

}
