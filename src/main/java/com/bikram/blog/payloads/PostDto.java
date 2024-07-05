package com.bikram.blog.payloads;


import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	@NotNull
	@Size(min = 5, message = "post-title must contain atleast 5 characters !!")
	private String postTitle;
	
	@NotNull
	@Size(min = 10, message = "post-description must contain atleast 10 characters !!")
	private String postDescription;
	private String postImageName;
	private Date postAddedDate;
	private UserDto userDto;
	private CategoryDto categoryDto;
	
}
