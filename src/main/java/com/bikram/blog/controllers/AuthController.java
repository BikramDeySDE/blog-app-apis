package com.bikram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikram.blog.exceptions.ApiException;
import com.bikram.blog.payloads.JwtAuthRequest;
import com.bikram.blog.payloads.JwtAuthResponse;
import com.bikram.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// login method (here we'll authenticate the user from username and password, and create token)
	/*
	 * STEPS :
	 * 	i. 	authenticate by using username and password
	 * 	ii.	if authenticated, after that generate token and return 
	 * 		a. load the userDetails from username and password
	 * 		b. generate token for the userDetails
	 * 		c. return the token in the JwtAuthResponse (create JwtAuthResponse onject and set the token in the response and return the response)
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		
		// authenticate by using username and password
		this.authenticate(request.getUsername(),request.getPassword());
		
		// now user is authenticated, now we need to create token for the user
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername()); // load userDetails from username
		
		String token = this.jwtTokenHelper.generateToken(userDetails); // now generate token for this particular userDetails
		
		// return the token
		JwtAuthResponse response = new JwtAuthResponse();	// create JwtAuthResponse object
		response.setToken(token);	// set the token in the response object
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK); // return token in JwtAuthResponse
		
	}


	
	// method to authenticate user from username and password
	private void authenticate(String username, String password) throws Exception {
	
		// Authentication Object
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		// now authenticate using the authentication object
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);			
		} catch (BadCredentialsException e) { // in case of wrong username or password, user will not be authenticated and exception will be thrown
			System.out.println("Invalid username or password !!");
			throw new ApiException("Invalid username or password !!");
		}
		
	}
}
