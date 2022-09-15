package com.tweetapp.tweet.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Tweets {
	
	@Id
	@GeneratedValue
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
	
	@ElementCollection
	private List<Reply> comments= new ArrayList<>();
	
}
