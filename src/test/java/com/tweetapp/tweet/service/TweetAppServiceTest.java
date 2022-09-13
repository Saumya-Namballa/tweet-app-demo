package com.tweetapp.tweet.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweet.TweetApplicationTests;
import com.tweetapp.tweet.model.LoginDetails;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.model.Reply;
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.repository.TweetRepository;
import com.tweetapp.tweet.repository.UserRepository;
import com.tweetapp.tweet.service.helper.DateFormatter;
import com.tweetapp.tweet.service.helper.TweetAppServiceHelper;
import com.tweetapp.tweet.validator.LoginIdValidator;
import com.tweetapp.tweet.validator.RegisterUserValidation;
import com.tweetapp.tweet.validator.TweetCredentialsValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
public class TweetAppServiceTest {
	
	@Mock
	UserRepository repo;
	
	@Mock
	RegisterUserValidation ruValidator;
	
	@Mock
	TweetRepository tweetRepo;
	
	@Mock
	LoginIdValidator loginIdValidator;
	
	@Mock
	TweetAppServiceHelper serviceHelper;
	
	@Mock
	DateFormatter dateFormatter;
	
	@Mock
	TweetCredentialsValidator tweetCredentialsValidator;
	
	@InjectMocks
	TweetAppService TweetAppService = new TweetAppService();
	
	@Test
	void registerUserTest() throws Exception{
		RegisterUser user = new RegisterUser();
		user.setPassword("vani12");
		user.setConfirmPassword("vani12");
		user.setLoginID("vani55");
		when(ruValidator.isValidLoginId("vani55")).thenReturn(Boolean.TRUE);
		assertEquals(user.getPassword(),user.getConfirmPassword());
		TweetAppService.registerUser(user);
		
		RegisterUser user1 = new RegisterUser();
		user1.setPassword("vani12");
		user1.setConfirmPassword("vani");
		user1.setLoginID("vani");
		when(ruValidator.isValidLoginId("vani")).thenReturn(Boolean.TRUE);
		assertNotEquals(user1.getPassword(),user1.getConfirmPassword());
		TweetAppService.registerUser(user1);
	}
	
	@Test
	void login()throws Exception {
		String request = "{ \"loginID\" : \"vani55\",\r\n" + 
				"	\"password\": \"vani1\"}";
		LoginDetails loginDetails = new ObjectMapper().readValue(request, LoginDetails.class);
		TweetAppService.login(loginDetails);
		
		RegisterUser user = new RegisterUser();
		user.setLoginID(loginDetails.getLoginID());
		user.setPassword(loginDetails.getPassword());
		when(repo.findByLoginID(loginDetails.getLoginID())).thenReturn(user);
		TweetAppService.login(loginDetails);
		
		RegisterUser user1 = new RegisterUser();
		user1.setLoginID(loginDetails.getLoginID());
		when(repo.findByLoginID(loginDetails.getLoginID())).thenReturn(user1);
		TweetAppService.login(loginDetails);
	}
	
	@Test
	void searchByUsername() {
		TweetAppService.searchByUsername("vani1");
	}
	
	@Test
	void getAllUsers() {
		TweetAppService.getAllUsers();
	}
	
	@Test
	void addTweet() throws Exception{
		String request = "{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		TweetAppService.addTweet(tweet);
		
		when(loginIdValidator.loginIdValidator(tweet.getLoginId())).thenReturn(true);
		TweetAppService.addTweet(tweet);
	}
	
	@Test 
	void updateTweet() throws Exception{
		String request = "{\r\n" + 
				"    \"tweetTag\" : \"Greetings\",\r\n" + 
				"    \"tweetBody\" : \"Hi.. I'm updating my tweet\",\r\n" + 
				"    \"loginId\" : \"vani1\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		TweetAppService.updateTweet(tweet);
		
		when(loginIdValidator.loginIdValidator(tweet.getLoginId())).thenReturn(true);
		when(tweetRepo.findById(tweet.getTweetId())).thenReturn(tweet);
		TweetAppService.updateTweet(tweet);
	}
	
	@Test
	void getAllTweets() {
		TweetAppService.getAllTweets();
	}
	
	@Test
	void getTweetsOfUser() {
		TweetAppService.getTweetsOfUser("vani");
	}
	
	@Test
	void forgotPassword() throws Exception{
		String request = "{\r\n" + 
				"    \"loginID\": \"ramya1234\",\r\n" + 
				"    \"emailID\": \"sam1@gmail.com\",\r\n" + 
				"    \"password\": \"ramya12\",\r\n" + 
				"    \"confirmPassword\": \"ramya12\"\r\n" + 
				"}";
		RegisterUser user = new ObjectMapper().readValue(request, RegisterUser.class);
		TweetAppService.forgotPassword(user);
		when(repo.findByLoginID(user.getEmailID())).thenReturn(user);
		TweetAppService.forgotPassword(user);
	}
	
	@Test
	void likeTweet() throws Exception {
		TweetAppService.likeTweet("vani1", 5);
		
		String request = "{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\",\r\n" + 
				"\"likes\": 4\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		when(loginIdValidator.loginIdValidator(tweet.getLoginId())).thenReturn(true);
		when(tweetCredentialsValidator.tweetValidator(tweet.getTweetId())).thenReturn(true);
		when(tweetRepo.findById(tweet.getTweetId())).thenReturn(tweet);
		TweetAppService.likeTweet(tweet.getLoginId(), tweet.getTweetId());
	}
	
	@Test
	void deleteTweet() throws Exception {
		TweetAppService.deleteTweet("vani", 5);
		
		String request = "{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\",\r\n" + 
				"\"likes\": 4\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		when(tweetRepo.findById(tweet.getTweetId())).thenReturn(tweet);
		TweetAppService.deleteTweet(tweet.getLoginId(), tweet.getTweetId());
	}
	
	@Test
	void replyTweet() throws Exception{
		String request = "{\r\n" + 
				"    \"reply\": \"Yes Indeed!!\"\r\n" + 
				"}";
		Reply reply = new ObjectMapper().readValue(request, Reply.class);
		String request1 = "{\"tweetId\": 6,\r\n" + 
				"\"loginId\": \"vani\",\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\",\r\n" + 
				"\"likes\": 4,\r\n" + 
				" \"comments\" : [ { \"reply\" : \"Yes Indeed!!\", \"username\" : \"ayush12\", \"replyTime\" : \"Wed 2022.08.03 at 11:27:17 PM\" } ]\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request1, Tweets.class);
		TweetAppService.replyTweet(tweet.getLoginId(), tweet.getTweetId(), reply);
		when(tweetRepo.findById(6)).thenReturn(tweet);
		when(loginIdValidator.loginIdValidator(tweet.getLoginId())).thenReturn(true);
		TweetAppService.replyTweet(tweet.getLoginId(), tweet.getTweetId(), reply);
	}
}
