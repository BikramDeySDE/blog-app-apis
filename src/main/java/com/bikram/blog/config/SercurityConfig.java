package com.bikram.blog.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bikram.blog.security.CustomUserDetailsService;
import com.bikram.blog.security.JwtAuthenticationEntrypoint;
import com.bikram.blog.security.JwtAuthenticationFilter;


import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //now we can apply security in each method
public class SercurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	// autowire required specifically for JWT Authentication 
	@Autowired
	private JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Reference : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter#:~:text=Configuring%20HttpSecurity
		
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/auth/login").permitAll() // making 'login' url public
		.requestMatchers(HttpMethod.GET).permitAll() // making all HTTP GET methods publicly accessible
		.requestMatchers("/categories/**").hasRole("ADMIN") // only ADMIN can access the category APIs (POST, PUT & DELETE) but GET APIs will be accessible publicly as configuration is already done for all GET end-points such that all the GET end-points are accessible publicly (because In Spring Security, the order of rules matters, and rules are applied in the sequence they are defined) 
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntrypoint) // whenever any exception is generated due to unauthorized, then it will go to this class and 'commence()' method of this class will be executed
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // setting session creation policy(under session management) as stateless
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // setting Authentication Filter
		
		// setting authenticationProvider to http
		http.authenticationProvider(daoAuthenticationProvider()); // 'daoAuthenticationProvider()' method will be used as http.authenticationProvider
		
		return http.build(); // 'http.build()' produces 'DefaultSecurityFilterChain' object | this statement is equivalent to : DefaultSecurityFilterChain defaultSecurityFilterChain = http.build(); return defaultSecurityFilterChain;
		
	}
	
	// configure method : in the new versions of spring, instead of declaring 'configure()', we will declare 'daoAuthenticationProvider()'
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		// DaoAUthenticationProvider object
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		// setting userDetailsService and passwordEncoder to the 'provider' : which userDetailsService will be used (customUserDetailsService) and which passwordEncoder will be used ('passwordEncoder()' method)
		provider.setUserDetailsService(this.customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		
		// return provider object
		return provider;
	}
	
	// declaring passwordEncoder for encoding password as we'll store the encoded password in the DB
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// AuthenticationManager : specific for JWT authentication
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
}



/*
 * Reference for Basic Authentication : 
 * 1. Basic Authentication :  https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
 * 2. Spring Security without the WebSecurityConfigurerAdapter : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 3. Spring Security without the WebSecurityConfigurerAdapter (LCWD Video): https://youtu.be/F31lvNRil10?si=-BnsUyc7h_Op2gwu
 */


/*
 * previously we used to declare 'SecurityConfig' by extending the class 'WebSecurityConfigurer Adapter' class, but now in the new versions of spring, we need to follow component based configuration (by declaring @Bean)
 * Reference : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 
 */
