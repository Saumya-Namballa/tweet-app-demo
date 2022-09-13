package com.tweetapp.tweet.service.helper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tweetapp.tweet.TweetApplicationTests;
import com.tweetapp.tweet.model.Tweets;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
class TweetAppServiceHelperTest {

	@InjectMocks
	TweetAppServiceHelper TweetAppServiceHelper=new TweetAppServiceHelper();
	
	@Test
	void populateTweetDetails() {
		
		Tweets oldTweet = new Tweets(1,null, null, null, 0, null, null);
		Tweets updatedTweet = new Tweets(1,null, null, null, 0, null, null);
		TweetAppServiceHelper.populateTweetDetails(oldTweet, updatedTweet);
	}

}
