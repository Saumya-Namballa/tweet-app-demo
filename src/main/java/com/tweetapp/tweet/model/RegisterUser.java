package com.tweetapp.tweet.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import lombok.Data;

@Data
@Valid
@Entity
public class RegisterUser {
	
	@NotBlank(message = "First name cannot be empty")
	@NotNull(message="First name cannot be NULL")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be empty")
	@NotNull(message="Last name cannot be NULL")
	private String lastName;
		
	@Id
	@NotNull(message="Login ID cannot be NULL")
	@NotBlank(message = "Login ID cannot be empty")
	private String loginID;
	
	@NotNull(message="Email ID cannot be NULL")
	@NotBlank(message = "Email ID cannot be empty")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,message="Please provide valid email ID")
	private String emailID;
	

	@NotNull(message="Password cannot be NULL")
	@NotBlank(message = "Password cannot be empty")
	private String password;
	
	@NotNull(message="Confirm Password cannot be NULL")
	@NotBlank(message = "Confirm Password cannot be empty")
	private String confirmPassword;
	
	@NotNull(message="Contact Number cannot be NULL")
	@NotBlank(message = "Contact number cannot be empty")
	@Pattern(regexp = "[0-9]{10}",message="Contact Number must be a 10-digit number")
	private String contactNumber;
	
	public RegisterUser(
			@NotNull(message = "Login ID cannot be NULL") @NotBlank(message = "Login ID cannot be empty") String loginID,
			@NotNull(message = "Email ID cannot be NULL") @NotBlank(message = "Email ID cannot be empty") @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Please provide valid email ID") String emailID,
			@NotNull(message = "Password cannot be NULL") @NotBlank(message = "Password cannot be empty") String password,
			@NotNull(message = "Confirm Password cannot be NULL") @NotBlank(message = "Confirm Password cannot be empty") String confirmPassword) {
		super();
		this.loginID = loginID;
		this.emailID = emailID;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	public RegisterUser() {
	}
}
