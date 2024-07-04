package com.bikram.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message = "categoryTitle must contain atleast 4 characters !!")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "categoryDescription must contain atleast 10 characters !!")
	private String categoryDescription;

}
