/*
 * This ApiException is created to handle in case user provides wrong password while logging in
 */
package com.bikram.blog.exceptions;

public class ApiException extends RuntimeException{

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}

	
	
}
