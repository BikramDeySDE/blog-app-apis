package com.bikram.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int userId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userAbout;
}
