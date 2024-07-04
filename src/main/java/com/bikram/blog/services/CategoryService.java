package com.bikram.blog.services;

import java.util.List;

import com.bikram.blog.payloads.CategoryDto;

public interface CategoryService {
	
	// create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update category
	CategoryDto UpdateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// delete category
	String deleteCategory(Integer categoryId);
	
	// get category by Id
	CategoryDto getCategoryById(Integer categoryId);
	
	// get all categories
	List<CategoryDto> getAllCategories();

}
