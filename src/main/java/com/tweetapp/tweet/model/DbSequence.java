package com.tweetapp.tweet.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class DbSequence {

	@Id 
	private String id;
	private int seq;
}
