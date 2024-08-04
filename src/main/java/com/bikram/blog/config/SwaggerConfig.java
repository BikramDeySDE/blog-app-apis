package com.bikram.blog.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	// method to return OpenAPI object
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(
					new Info()
						.title("Blogging Appilacation")
						.description("This project is developed by Bikram Dey")
						.version("1.0")
						.contact(new Contact().name("Bikram Dey").url("https://www.linkedin.com/in/bikramdey/").email("bikramdeyofficial@gmail.com"))
						.license(new License().name("Apache")))
				.externalDocs(new ExternalDocumentation()
						.description("This project is developed by Bikram Dey")
						.url("https://www.linkedin.com/in/bikramdey/"));		
	}
		
}
