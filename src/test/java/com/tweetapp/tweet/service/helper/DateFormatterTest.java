package com.tweetapp.tweet.service.helper;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tweetapp.tweet.TweetApplicationTests;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TweetApplicationTests.class)
class DateFormatterTest {

	@InjectMocks
	DateFormatter dateFormatter=new DateFormatter();
	
	@Test
	void dateformatter() {
		dateFormatter.dateformatter(new Date());
	}

}
