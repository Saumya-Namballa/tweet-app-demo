package com.tweetapp.tweet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetapp.tweet.model.Tweets;

public interface TweetRepository extends JpaRepository<Tweets, Integer> {

	Tweets findById(int id);

	List<Tweets> findByLoginId(String loginId);
}
