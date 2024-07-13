package com.bikram.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bikram.blog.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SercurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Reference : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter#:~:text=Configuring%20HttpSecurity
		
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
		
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
	
	
}



/*
 * Reference for Basic Authentication : 
 * 1. Basic Authentication :  https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html
 * 2. Spring Security without the WebSecurityConfigurerAdapter : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 */


/*
 * previously we used to declare 'SecurityConfig' by extending the class 'WebSecurityConfigurer Adapter' class, but now in the new versions of spring, we need to follow component based configuration (by declaring @Bean)
 * Reference : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 
 */
