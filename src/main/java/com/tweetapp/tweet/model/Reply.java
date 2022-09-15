package com.tweetapp.tweet.model;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Valid
@Embeddable
public class Reply {

	@Size(min=1,max=144)
	@NotNull(message="Reply cannot be null")
	@NotBlank(message="Reply cannot be empty")
	private String reply;
	
	@Size(min=0,max=50)
	private String tag;
	private String username;
	private String replyTime;
}
