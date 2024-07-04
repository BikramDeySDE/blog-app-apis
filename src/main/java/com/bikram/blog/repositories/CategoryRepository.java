package com.bikram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
