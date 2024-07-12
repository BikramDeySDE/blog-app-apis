package com.bikram.blog.payloads;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private UserDto user;	// the variable needs to be named as 'user' so that whenever modelMapper will face problem while setting the value after converting UserDto to User or vice-versa, it should recognize (otherwise 'user' data will be null in case of get-all-posts), otherwise we need to create custom method for the conversion : all the parameter names for an Entity and EntityDto should be same, otherwise data will show as null
	private CategoryDto category; // the variable needs to be named as 'category' so that whenever modelMapper will face problem while setting value after CategotyDto to Category or vice-versa, it should recognize (otherwise 'category' data will be null in case of get-all-posts), otherwise we need to create custom method for the conversion : all the parameter names for an Entity and EntityDto should be same, otherwise data will show as null
	// if we want to fetch all the comments, then we need to include the commentDtos here
	private Set<CommentDto> comments = new HashSet<>();
}
