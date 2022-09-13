package com.tweetapp.tweet.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Tweets {
	
	@Transient
	public static final String SEQUENCE_NAME = "tweet_sequence";
	
	@Id
	private int tweetId;
	private String loginId;
	
	//@Max(value= 144, message = "Tweet Body characters exceeded...")
	@Size(min=1,max=144,message = "Tweet Body characters exceeded...max size 144")
	@NotNull(message="Tweet Body cannot be null")
	@NotBlank(message="Tweet Body cannot be empty")
	private String tweetBody;
	
	@Size(min=0,max=50,message = "TweetTag between 0 to 50")
	private String tweetTag;
	
	private int likes;
	private String creationTime;
	
	private List<Reply> comments;
	
}
