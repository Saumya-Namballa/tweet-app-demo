package com.tweetapp.tweet;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void applicationStarts() {
		TweetApplication.main(new String[] {});
	}
}
