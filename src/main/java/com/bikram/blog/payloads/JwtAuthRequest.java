package com.bikram.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username; // here we'll use 'userEmail' as username login
	
	private String password;
	
}
