package com.bikram.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bikram.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// for handling ResourceNotFoundException (custom exception)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	
	// for handling MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		// creating new HashMap object
		Map<String, String> response = new HashMap<>();
		
		// getting all errors from validation exception
		ex.getBindingResult().getAllErrors().forEach((error)->{ // traversing each errors 
			String fieldName = ((FieldError)error).getField(); // type-casting the error to fieldError and fetching field from the fieldError
			String message = error.getDefaultMessage(); // fetching message from the error
			response.put(fieldName, message); // adding the key (fieldName) and value (message) pair to build the response
		});
		
		// return the response (hashMap object) with status code BAD_REQUEST
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST); 
	}
	
	
	
	// for handling UserWithUsernameNotFoundException (custom exception)
	@ExceptionHandler(UserWithUsernameNotFoundException.class)
	public ResponseEntity<ApiResponse> userWithUsernameNotFoundExceptionHandler(UserWithUsernameNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		response.setSuccess(false);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
	}
	
	
	// for handling UserWithUsernameNotFoundException (custom exception)
		@ExceptionHandler(ApiException.class)
		public ResponseEntity<ApiResponse> ApiExceptionHandler(ApiException ex) {
			String message = ex.getMessage();
			ApiResponse response = new ApiResponse();
			response.setMessage(message);
			response.setSuccess(false);
			return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
		}
	
}
