package com.tweetapp.tweet.validator;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.tweet.TweetApplicationTests;
import com.tweetapp.tweet.model.RegisterUser;
import com.tweetapp.tweet.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
public class LoginIdValidatorTest {
	
	@Mock
	UserRepository repo;
	
	@InjectMocks
	LoginIdValidator loginIdValidator = new LoginIdValidator();
	
	@Test
	public void loginIdValidator() throws Exception {
		loginIdValidator.loginIdValidator("vani");
		String request ="{\r\n" + 
				"    \"firstName\" : \"Ramya\",\r\n" + 
				"    \"lastName\" : \"Vani\",\r\n" + 
				"    \"loginID\" : \"ramya123\",\r\n" + 
				"    \"emailID\" : \"ram1234@gmailcom\",\r\n" + 
				"    \"password\" : \"ramya\",\r\n" + 
				"    \"confirmPassword\" : \"ramya\",\r\n" + 
				"    \"contactNumber\" : \"9848022338\"\r\n" + 
				"}";
		RegisterUser user = new ObjectMapper().readValue(request, RegisterUser.class);
		when(repo.findByLoginID("ramya123")).thenReturn(user);
		loginIdValidator.loginIdValidator(user.getLoginID());
	}
}
