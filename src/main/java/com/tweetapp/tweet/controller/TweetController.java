package com.tweetapp.tweet.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweet.model.LoginDetails;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.model.Reply;
import com.tweetapp.tweet.model.Response;
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.model.UserId;
import com.tweetapp.tweet.service.TweetAppService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1.0/tweets/")
@RestController
public class TweetController {

	@Autowired
	private TweetAppService service;

	private static final Logger log = LoggerFactory.getLogger(TweetController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	// Register
	@PostMapping("register")
	public ResponseEntity<Response> registerUser(@Valid @RequestBody RegisterUser user) {
		Response res = new Response();
		try {
			boolean register = service.registerUser(user);
			if (register) {
				res.setMessage("User registered successfully");
				log.info("User registered successfully");
				return new ResponseEntity<>(res, HttpStatus.CREATED);
			} else {
				res.setMessage("Details does not satify the conditions..Please check!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Error Occurred while registration!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// Login
	@GetMapping("login")
	public ResponseEntity<Response> login(@RequestParam("loginId") String loginId,
			@RequestParam("password") String password) {
		Response res = new Response();
		try {
			LoginDetails loginDetails = new LoginDetails();
			loginDetails.setLoginID(loginId);
			loginDetails.setPassword(password);

			boolean login = service.login(loginDetails);
			if (login) {
				log.info("Logged in");
				res.setMessage("User Logged In Successfully!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setMessage("Credentials are incorrect");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Error Occurred while login..");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// Forgot Password
	@PutMapping("/forgot")
	public ResponseEntity<Response> forgotPassword(@RequestBody RegisterUser forgotPassword) {
		Response res= new Response();
		try {
			boolean changed = service.forgotPassword(forgotPassword);
			if (changed) {
				res.setMessage("Password changed Successfully!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				res.setMessage("Credentials are incorrect!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Error Occurred while changing password..");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// Get all Users
	@GetMapping("users/all")
	public List<Object> getAllUsers() {
		try {
			List<Object> users = service.getAllUsers();
			List<Object> usernames = new ArrayList<>();
			for(Object user : users) {
				usernames.add(user);
			}
			log.info("Users: {}", usernames);
			return usernames;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// Search by username
	@GetMapping("user/search/{username}")
	public List<Object> searchByUsername(@PathVariable String username) {

		try {
			List<Object> users = service.searchByUsername(username);
			List<Object> usernames = new ArrayList<>();
			for(Object user : users) {
				usernames.add(user);
			}
			log.info("Users: {}", usernames);
			return usernames;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// Post Tweet
	@PostMapping("{username}/add")
	public ResponseEntity<Response> addTweet(@PathVariable(value = "username") String loginId,
			@Valid @RequestBody Tweets tweet) {
		Response res = new Response();
		try {
			tweet.setLoginId(loginId);
			boolean addTweet = service.addTweet(tweet);
			if (addTweet) {
				log.info("Tweet Added successfully with id:{}", tweet.getTweetId());
				res.setMessage("Tweet Added successfully!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				log.info("Username not present...");
				res.setMessage("Username not present!!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Error while adding tweet...");
			e.printStackTrace();
			res.setMessage("Tweet Not Added!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// Update Tweet
	@PutMapping("{username}/update/{id}")
	public ResponseEntity<Response> updateTweet(@PathVariable(value = "username") String loginId,
			@PathVariable(value = "id") int tweetId, @RequestBody Tweets tweet) {
		Response res = new Response();
		try {
			tweet.setTweetId(tweetId);
			Tweets newTweet = service.updateTweet(tweet);
			if (newTweet != null) {
				log.info("Tweet Updated Successfully!!");
				res.setMessage("Tweet Updated successfully!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				log.info("TweetId/Username not present");
				res.setMessage("TweetId/Username not present");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Error while Updating tweet.");
			e.printStackTrace();
			res.setMessage("Error while Updating tweet!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// Get all Tweets
	@GetMapping("all")
	public List<Tweets> getAllTweets() {

		try {
			List<Tweets> allTweets = service.getAllTweets();
			log.info("All Tweets: {}", allTweets);
			return allTweets;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// Get all Tweets Of a User
	@GetMapping("{username}")
	public List<Tweets> getTweetsOfUser(@PathVariable(value = "username") String loginId) {
		try {
			List<Tweets> allTweets = service.getTweetsOfUser(loginId);
			log.info("All Tweets: {}", allTweets);
			return allTweets;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

	}

	// Like a tweet
	@PutMapping("{username}/like/{id}")
	public ResponseEntity<Response> likeTweet(@PathVariable(value = "username") String loginId,
			@PathVariable(value = "id") int tweetId) {
		Response res= new Response();
		try {
			boolean response = service.likeTweet(loginId, tweetId);
			if (response) {
				log.info("You liked the tweet!!!");
				res.setMessage("You liked the tweet!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				log.info("Username/tweetId not present");
				res.setMessage("Username/Tweetid not present!!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Error occurred while liking the tweet!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

	}

	// delete a tweet
	@DeleteMapping("{username}/delete/{id}")
	public ResponseEntity<Response> deleteTweet(@PathVariable(value = "username") String loginId,
			@PathVariable(value = "id") int tweetId) {
		Response res = new Response();
		try {
			boolean response = service.deleteTweet(loginId, tweetId);
			if (response) {
				log.info("You deleted the tweet!!!");
				res.setMessage("You deleted the tweet!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				log.info("Tweet Not deleted...");
				res.setMessage("User not allowed to delete the tweet!!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage("Error occurred while deleting the tweet!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	// reply to a tweet
	@PostMapping("{username}/reply/{id}")
	public ResponseEntity<Response> replyTweet(@PathVariable(value = "username") String loginId,
			@PathVariable(value = "id") int tweetId, @Valid @RequestBody Reply reply){
		Response res = new Response();
		try {
			boolean response = service.replyTweet(loginId, tweetId, reply);
			if (response) {
				log.info("You replied to a tweet!!!");
				res.setMessage("You replied to a tweet!!!");
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				log.info("Username/tweetId not present");
				res.setMessage("Username/tweetId not present!!!");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			e.printStackTrace();
			res.setMessage("Error occurred while replying to a tweet!!!");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("{username}/getTweet/{id}")
	public ResponseEntity<?> getTweetDetails(@PathVariable(value = "username") String loginId,
			@PathVariable(value = "id") int tweetId){
		return new ResponseEntity<>(service.getTweet(loginId, tweetId), HttpStatus.OK);
	}
}
