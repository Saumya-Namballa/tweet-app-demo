package com.tweetapp.tweet.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.repository.TweetRepository;

@Service
public class TweetCredentialsValidator {
	
	@Autowired
	TweetRepository tweetRepo;
	
	private static final Logger log = LoggerFactory.getLogger(TweetCredentialsValidator.class);
	
	public boolean tweetValidator(int tweetId) {
		Tweets id = tweetRepo.findById(tweetId);
		if(id!=null && id.getTweetId()== tweetId) {
			log.info("Valid Tweet!!");
			return true;
		} else {
			log.info("Invalid Tweet!!!");
			return false;
		}
	}
}
