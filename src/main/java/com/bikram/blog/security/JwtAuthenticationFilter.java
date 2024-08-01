package com.bikram.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	// step 1 : autowiring
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	// this doFilterInternal() method will run before each API request : if everything goes correct, then authentication will set in the security context and the request will proceed, otherwise else conditions will run and it will show the error configured in the 'commence()' method of JwtAUthenticationEntryPoint Class 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// step 2 : get JWT token from request 
		String requestToken = request.getHeader("Authorization"); // retrieving JWT token from request (request > header > value of the corresponding key "Authorization")
		
		// Sample token : Bearer 123546354874ggdsh
		
		// additional steps : here we are printing the token
		System.out.println("Token received in 'Authorization' header : " + requestToken);
		
		// step 3 : retrieve username from JWT token
		String token = null;
		String username = null;
		
		if (requestToken != null && requestToken.startsWith("Bearer")) { // check if token is not null and starts with "Bearer"
			
			token = requestToken.substring(7); // removing "Bearer" from the token and storing it into 'tpken' variable (example: Bearer 123546354874ggdsh -> 123546354874ggdsh)
			
			// retrieving username from token using 'jwtTokenHelper' class method
			try {
				username = this.jwtTokenHelper.getUsernameFromToken(token); 
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT token !!");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT token is expired !!");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
			
		} else {
			System.out.println("JWT token does not start with : Bearer !!"); // if JWT token does not start with Bearer, then print this message 
		}
		
		
		// Step 4 : Validate the token
		if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) { // check if username is not null or Security Context Authentication is null, so that we can set authentication here
			
			// Step 5 : fetching userDetails by 'loadUserByUsername()' method of 'UserDetailsService'
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) { // validate token and userDetails
				
				// now token is validated with userDetails, now we just need to set Authentication in the security context
				
				// Step 6 : Set Authentication in Security Context
				
				// creating UsernamePasswordAuthenticationToken object (based on userDetails and granted authorities) for passing authentication object in the setAUthentication() method
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				// set details in UsernamePasswordAuthenticationToken
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// set authentication
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				System.out.println("Invalid JWT TOken !!");
			}
			
		} else {
			System.out.println("username is null or Security Context Authentication is not null !!");
		}
		
		// Step 7 : filter the request
		filterChain.doFilter(request, response);
		
	}

}
