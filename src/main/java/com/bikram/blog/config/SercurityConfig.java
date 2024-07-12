package com.bikram.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SercurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
		
		return http.build(); // 'http.build()' produces 'DefaultSecurityFilterChain' object
		
	}
	
}



/*
 * Reference for Basic Authentication : 
 * 1. Basic Authentication :  https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
 * 2. Spring Security without the WebSecurityConfigurerAdapter : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */

