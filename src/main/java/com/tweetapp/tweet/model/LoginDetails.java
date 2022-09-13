package com.tweetapp.tweet.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document
public class LoginDetails {
	
	private String loginID;
	private String password;

}
