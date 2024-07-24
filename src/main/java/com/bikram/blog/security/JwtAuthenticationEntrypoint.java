package com.bikram.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		// sending the response error (with status code and custom message, whenever some unauthorized person tries to access the APIs
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!");
	}

}
