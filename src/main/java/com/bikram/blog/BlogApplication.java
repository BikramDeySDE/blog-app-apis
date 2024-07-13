package com.bikram.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{ // implemented the interface 'CommandLineRunner' to encode a particular password

	// for the purpose of encoding a particular password
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	// declaring bean for modelMapper : it will create ModelMapper object automatically whenever we'll use auto-wire (@Autowired) for this modelMapper 
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
	// This method is coming due to implement the interface 'CommandLineRunner' | whenever the application will run, this method will also be running | 
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("$Hey_&")); // "$Hey_&" is the password which we can encode | we will get the encoded password on the console, whenever we'll run the application
	}

}
