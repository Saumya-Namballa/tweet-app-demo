package com.tweetapp.tweet.service.helper;

import org.springframework.stereotype.Service;

import com.tweetapp.tweet.model.Tweets;

@Service
public class TweetAppServiceHelper {

	public void populateTweetDetails(Tweets oldTweet, Tweets updatedTweet) {
		oldTweet.setTweetBody(updatedTweet.getTweetBody());
		oldTweet.setTweetTag(updatedTweet.getTweetTag());
	}
}
