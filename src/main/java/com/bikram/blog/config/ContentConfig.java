package com.bikram.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ContentConfig implements WebMvcConfigurer {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		configurer.favorParameter(true) // Whether a request parameter ("format" by default) should be used todetermine the requested media type. For this option to work you mustregister media type mappings. 
					.parameterName("mediaType") // setting query parameter name as 'mediaType' ('?mediaType=<value>')
					.defaultContentType(MediaType.APPLICATION_JSON)	// setting default mediaType as JSON (i.e. if query parameter 'mediaType' is not passed in the request, then MediaType will be set as JSON)
					.mediaType("json", MediaType.APPLICATION_JSON)	// if 'json' is passed as query parameter value of 'mediaType' (?mediaType=json), then MediaType will be set as JSON
					.mediaType("xml", MediaType.APPLICATION_XML);	// if 'xml' is passed as query parameter value of 'mediaType' (?mediaType=xml), then MediaType will be set as XML
		
	}

}
