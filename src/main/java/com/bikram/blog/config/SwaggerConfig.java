package com.bikram.blog.config;


import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(
		name = "bearerScheme",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
@OpenAPIDefinition(
		info = @Info(
				title = "Blogging Appilacation",
				description = "This project is developed by SDE Bikram Dey",
				version = "1.0",
				contact = @Contact(
						name = "Bikram Dey",
						email = "bikramdeyofficial@gmail.com",
						url = "https://www.linkedin.com/in/bikramdey/"
						),
				license = @License(
						name = "OPEN License"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Software Development Engineer : Bikram Dey",
				url = "https://www.linkedin.com/in/bikramdey/"
				))
public class SwaggerConfig {

	
	
	/*  ************************************************************************************************
	 *  Commenting out this configuration using bean as we are doing the configuration using Annotations
	 *  ************************************************************************************************
	 *  
	String schemeName = "bearerScheme";
	
	// method to return OpenAPI object
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(schemeName))
				.components(new Components()
						.addSecuritySchemes(schemeName, new SecurityScheme()
								.name(schemeName)
								.type(Type.HTTP)
								.bearerFormat("JWT")
								.scheme("bearer")))
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
	
		*/
}
