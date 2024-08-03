package com.bikram.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.bikram.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int userId;
	
	@NotEmpty(message = "userName is required !!")
	@Size(min=4,message = "userName must contain atleast 4 characters !!")
	private String userName;
	
	@Email(message = "userEmail is not valid !!")
	@NotEmpty(message = "userEmail is required !!")
	private String userEmail;
	
	@NotEmpty(message = "userPassword is required !!")
	@Size(min = 3, max = 10, message = "userPassword must contain atleast 3 and atmost 10 characters !!")
	private String userPassword; // we can also add some pattern using "regex" for password validation
	
	@NotEmpty(message = "userAbout is required !!")
	private String userAbout;
	
	private Set<Role> roles = new HashSet<>();	// added roles in UserDto to show roles in response of UserDto
}
