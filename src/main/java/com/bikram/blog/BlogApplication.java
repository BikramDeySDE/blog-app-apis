package com.bikram.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	// declaring bean for modelMapper : it will create ModelMapper object automatically whenever we'll use auto-wire (@Autowired) for this modelMapper 
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
