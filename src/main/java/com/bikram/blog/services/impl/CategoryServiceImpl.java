package com.bikram.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikram.blog.entities.Category;
import com.bikram.blog.exceptions.ResourceNotFoundException;
import com.bikram.blog.payloads.CategoryDto;
import com.bikram.blog.repositories.CategoryRepository;
import com.bikram.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	
	// create category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepository.save(category);
		CategoryDto savedCategoryDto = this.categoryToCategoryDto(savedCategory);
		return savedCategoryDto;
	}
	
	

	// update category
	@Override
	public CategoryDto UpdateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Categoty", "Category-Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedCategoty = this.categoryRepository.save(category);
		CategoryDto savedCategoryDto = this.categoryToCategoryDto(category);
		return savedCategoryDto;
	}

	
	
	// delete category
	@Override
	public String deleteCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Categoty", "Category-Id", categoryId));
		this.categoryRepository.delete(category);
		String message = "Categoty is deleted with Category-Id : " + categoryId;
		return message;
	}

	// get category by id
	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category-Id", categoryId));
		CategoryDto categoryDto = this.categoryToCategoryDto(category);
		return categoryDto;
	}
	
	
	// get all categories
	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((category)->this.categoryToCategoryDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}
	
	
	
	// categoryDto to category
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	// category to categoryDto
	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
}
