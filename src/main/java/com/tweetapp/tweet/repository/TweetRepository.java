package com.tweetapp.tweet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.tweet.model.Tweets;

public interface TweetRepository extends MongoRepository<Tweets, Integer> {

	Tweets findById(int id);

	@Query("{'loginId' : ?0}")
	List<Tweets> findByLoginId(String loginId);
}
