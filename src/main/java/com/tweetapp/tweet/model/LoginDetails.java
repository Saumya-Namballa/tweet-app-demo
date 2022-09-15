package com.tweetapp.tweet.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class LoginDetails {
	
	@Id
	private String loginID;
	private String password;

}
