package com.tweetapp.tweet.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweet.TweetApplicationTests;
import com.tweetapp.tweet.model.LoginDetails;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.model.Reply;
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.model.UserId;
import com.tweetapp.tweet.service.SequenceGeneratorService;
import com.tweetapp.tweet.service.TweetAppService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
class TweetControllerTest {
	
	@Mock
	private TweetAppService service;
	
	@Mock
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Mock 
	ObjectMapper mapper;

	@InjectMocks
	TweetController tweetController = new TweetController();
	
	@Test
	void registerUserTest() throws Exception{
		String request = "{\r\n" + 
				"    \"firstName\" : \"Saumya\",\r\n" + 
				"    \"lastName\" : \"Vani\",\r\n" + 
				"    \"loginID\" : \"vani55\",\r\n" + 
				"    \"emailID\" : \"saumya123@gmail.com\",\r\n" + 
				"    \"password\" : \"vani1\",\r\n" + 
				"    \"confirmPassword\" : \"vani1\",\r\n" + 
				"    \"contactNumber\" : \"9999119339\"\r\n" + 
				"}";
		RegisterUser user = new ObjectMapper().readValue(request, RegisterUser.class);
		when(service.registerUser(user)).thenReturn(true);
		tweetController.registerUser(user);
		tweetController.registerUser(null);
	}
	
	@Test
	void login() throws Exception {
		String request = "{ \"loginID\" : \"vani55\",\r\n" + 
				"	\"password\": \"vani1\"}";
		LoginDetails loginDetails = new ObjectMapper().readValue(request, LoginDetails.class);
		when(service.login(loginDetails)).thenReturn(true);
		tweetController.login(loginDetails.getLoginID(), loginDetails.getPassword());
		tweetController.login(null,null);
	}
	
	@Test
	void getAllUsers() {
		tweetController.getAllUsers();
		List<UserId> users = null;
		when(service.getAllUsers()).thenReturn(users);
		tweetController.getAllUsers();
	}
	
	@Test
	void forgotPassword() throws Exception {
		String request = "{\r\n" + 
				"    \"loginID\" : \"vani55\",\r\n" + 
				"    \"emailID\" : \"saumya123@gmail.com\",\r\n" + 
				"    \"password\" : \"vani1\",\r\n" + 
				"    \"confirmPassword\" : \"vani1\",\r\n" + 
				"}";
		RegisterUser user = new ObjectMapper().readValue(request, RegisterUser.class);
		tweetController.forgotPassword(user);
		when(service.forgotPassword(user)).thenReturn(true);
		tweetController.forgotPassword(user);
	}
	
	@Test
	void searchByUsername() {
		tweetController.searchByUsername("vani1");
	}
	
	@Test
	void addTweet() throws Exception {
		String request = "{\r\n" + 
				"    \"tweetTag\" : \"Greetings\",\r\n" + 
				"    \"tweetBody\" : \"Hi.. This is my first tweet\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		tweetController.addTweet(null, tweet);
		when(service.addTweet(tweet)).thenReturn(true);
		tweetController.addTweet("vani1", tweet);
	}

	@Test
	void updateTweet() throws Exception {
		String request = "{\r\n" + 
				"    \"tweetTag\" : \"Greetings\",\r\n" + 
				"    \"tweetBody\" : \"Hi.. I'm updating my tweet\",\r\n" + 
				"    \"loginId\" : \"vani1\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		tweetController.updateTweet("vani1", 6, tweet);
		Tweets newTweet = new Tweets();
		when(service.updateTweet(tweet)).thenReturn(newTweet);
		tweetController.updateTweet(tweet.getLoginId(), 6, tweet);
	}
	
	@Test
	void getAllTweets() {
		tweetController.getAllTweets();
	}
	
	@Test
	void getTweetsOfUser() {
		tweetController.getTweetsOfUser("vani1");
	}
	
	@Test
	void likeTweet() throws Exception{
		tweetController.likeTweet("vani123", 5);
		String request ="{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		when(service.likeTweet(tweet.getLoginId(), tweet.getTweetId())).thenReturn(true);
		tweetController.likeTweet(tweet.getLoginId(), tweet.getTweetId());
	}
	
	@Test
	void deleteTweet() throws Exception {
		tweetController.deleteTweet("vani1", 5);
		String request ="{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		when(service.deleteTweet(tweet.getLoginId(), tweet.getTweetId())).thenReturn(true);
		tweetController.deleteTweet(tweet.getLoginId(), tweet.getTweetId());
	}
	
	@Test
	void replyTweet() throws Exception{
		String request = "{\r\n" + 
				"    \"reply\": \"Yes Indeed!!\",\r\n" + 
				"	\"username\": \"sam123\"\r\n" + 
				"}";
		Reply reply = new ObjectMapper().readValue(request, Reply.class);
		tweetController.replyTweet("vani1", 6, reply);
		when(service.replyTweet(reply.getUsername(), 5, reply)).thenReturn(true);
		tweetController.replyTweet(reply.getUsername(), 5, reply);
	}
}
