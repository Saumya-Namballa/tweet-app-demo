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
import com.tweetapp.tweet.model.Tweets;
import com.tweetapp.tweet.repository.TweetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
class TweetCredentialsValidatorTest {

	@Mock
	TweetRepository tweetRepo;
	
	@InjectMocks
	TweetCredentialsValidator tweetValidator = new TweetCredentialsValidator();
	
	@Test
	void tweetValidator() throws Exception {
		String request= "{\"tweetId\": 6,\r\n" + 
				"\"tweetTag\" : \"Choclate\",\r\n" + 
				"\"tweetBody\" : \"Today is a beatiful day..\"\r\n" + 
				"}";
		Tweets tweet = new ObjectMapper().readValue(request, Tweets.class);
		when(tweetRepo.findById(6)).thenReturn(tweet);
		tweetValidator.tweetValidator(tweet.getTweetId());
		tweetValidator.tweetValidator(1);
	}

}
