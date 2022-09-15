package com.tweetapp.tweet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.slf4j.LoggerFactory;
import com.tweetapp.tweet.model.LoginDetails;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.model.Reply;
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.model.UserId;
import com.tweetapp.tweet.repository.TweetRepository;
import com.tweetapp.tweet.repository.UserRepository;
import com.tweetapp.tweet.service.helper.DateFormatter;
import com.tweetapp.tweet.service.helper.TweetAppServiceHelper;
import com.tweetapp.tweet.validator.LoginIdValidator;
import com.tweetapp.tweet.validator.RegisterUserValidation;
import com.tweetapp.tweet.validator.TweetCredentialsValidator;

@Service
public class TweetAppService {

	@Autowired
	UserRepository repo;

	@Autowired
	RegisterUserValidation ruValidator;

	@Autowired
	TweetRepository tweetRepo;

	@Autowired
	LoginIdValidator loginIdValidator;

	@Autowired
	TweetAppServiceHelper serviceHelper;

	@Autowired
	TweetCredentialsValidator tweetCredentialsValidator;

	@Autowired
	DateFormatter dateFormatter;

	private static final Logger log = LoggerFactory.getLogger(TweetAppService.class);

	public boolean registerUser(RegisterUser user) {
		log.info("Inside RegisterUser Service...");
		boolean valid = ruValidator.isValidLoginId(user.getLoginID());
		// boolean uniqueEmail = ruValidator.isValidEmailId(user.getEmailID());
		if (valid && user.getPassword().equals(user.getConfirmPassword())) {
			repo.save(user);
			return true;
		} else {
			if (!user.getPassword().equals(user.getConfirmPassword()))
				log.info("Password and Confirm Password must be same");
		}

		return false;
	}

	public boolean login(LoginDetails loginDetails) {
		log.info("Inside Login service...");
		RegisterUser user = repo.findByLoginID(loginDetails.getLoginID());
		if (user != null) {
			if (loginDetails.getPassword().equals(user.getPassword())) {
				log.info("User found");
				return true;
			} else {
				log.info("Password does not match...");
			}
		} else {
			log.info("Login id not found!!");
		}
		return false;
	}

	public List<Object> searchByUsername(String loginId) {
		log.info("Inside SearchByUsername service...");
		List<Object> users = null;
		users = repo.findAllUsers();
		List<Object> userOutput = new ArrayList<>();
		for (Object user : users) {
			if ( user.toString().contains(loginId))
				userOutput.add(user);
		}
		return userOutput;

	}

	public List<Object> getAllUsers() {
		log.info("Inside getAllUsers Service...");
		List<Object> users = null;
		users = repo.findAllUsers();
		List<Object> userOutput = new ArrayList<>();
		for (Object user : users) {
			userOutput.add(user);
		}
		return userOutput;
	}

	public boolean addTweet(@Valid Tweets newTweet) {
		log.info("Inside AddTweet Service...");
		boolean valid = loginIdValidator.loginIdValidator(newTweet.getLoginId());
		if (valid) {
			newTweet.setCreationTime(dateFormatter.dateformatter(new Date()));
			newTweet.setComments(new ArrayList<>());
			tweetRepo.save(newTweet);
			log.info("Adding the Tweet:{}", newTweet);
			return true;
		} else {
			log.info("Not adding the tweet!!!");
			return false;
		}

	}

	public Tweets updateTweet(Tweets updatedTweet) {
		log.info("Inside UpdateTweet Service...");
		boolean valid = loginIdValidator.loginIdValidator(updatedTweet.getLoginId());
		if (valid) {
			Tweets oldTweet = tweetRepo.findById(updatedTweet.getTweetId());
			log.info("Tweet fetched from db:{}", oldTweet);
			if (oldTweet != null) {
				log.info("Updating the Tweet..");
				serviceHelper.populateTweetDetails(oldTweet, updatedTweet);
				tweetRepo.save(oldTweet);
			}
			return oldTweet;
		}
		return null;
	}

	public List<Tweets> getAllTweets() {
		log.info("Inside Get All Tweets Service...");
		return tweetRepo.findAll();
	}

	public List<Tweets> getTweetsOfUser(String loginId) {
		log.info("Inside Get All Tweets of user Service...");
		return tweetRepo.findByLoginId(loginId);
	}

	public boolean forgotPassword(RegisterUser forgotPassword) {
		log.info("Inside Forgot Password Service...");
		RegisterUser user = repo.findByEmailID(forgotPassword.getEmailID());
		if (user != null && user.getLoginID().equals(forgotPassword.getLoginID())) {
			user.setPassword(forgotPassword.getPassword());
			user.setConfirmPassword(forgotPassword.getPassword());
			repo.save(user);
			log.info("Password updated to {}", forgotPassword.getPassword());
			return true;
		}
		return false;
	}

	public boolean likeTweet(String loginId, int tweetId) {
		log.info("Inside Like Tweet Service...");
		boolean validUser = loginIdValidator.loginIdValidator(loginId);
		boolean validTweet = tweetCredentialsValidator.tweetValidator(tweetId);
		if (validUser && validTweet) {
			Tweets tweet = tweetRepo.findById(tweetId);
			int count = tweet.getLikes();
			count += 1;
			tweet.setLikes(count);
			log.info("Updated count of like!!!");
			tweetRepo.save(tweet);
			log.info("Tweet: {}", tweet);
			return true;
		}
		return false;
	}

	public boolean deleteTweet(String loginId, int tweetId) {
		log.info("Inside Delete Tweet Service...");
		Tweets tweet = tweetRepo.findById(tweetId);
		if (tweet != null && tweet.getLoginId().equals(loginId)) {
			log.info("Tweet is deleted");
			tweetRepo.delete(tweet);
			return true;
		} else {
			log.info("User not allowed to delete the tweet");
			return false;
		}
	}

	public boolean replyTweet(String loginId, int tweetId, Reply reply) {
		log.info("Inside Reply Tweet Service...");
		boolean validUser = loginIdValidator.loginIdValidator(loginId);
		Tweets tweet = tweetRepo.findById(tweetId);
		reply.setReplyTime(dateFormatter.dateformatter(new Date()));
		reply.setUsername(loginId);
		if (validUser && tweet != null) {
			tweet.getComments().add(reply);
			tweetRepo.save(tweet);
			log.info("Tweet:{}", tweet);
			tweetRepo.save(tweet);
			return true;
		}
		return false;
	}

	public Tweets getTweet(String loginId, int tweetId) {
		Tweets tweetFound =  tweetRepo.findById(tweetId);
		if(!ObjectUtils.isEmpty(tweetFound)) {
			return tweetFound;
		} else 
		return null;
	}
}
