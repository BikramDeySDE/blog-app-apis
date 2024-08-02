package com.bikram.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithUsernameNotFoundException extends RuntimeException{

	private String username;

	public UserWithUsernameNotFoundException(String username) {
		super("User not found with username : " + username);
		this.username = username;
	}
	
	
	
}
