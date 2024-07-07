package com.bikram.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.Category;
import com.bikram.blog.entities.Post;
import com.bikram.blog.entities.User;
import com.bikram.blog.payloads.PostDto;

public interface PostRepository extends JpaRepository<Post, Integer> {

	// implementations of all the methods in the Repository interface (which extends JpaRepository) are automatically provided
	
	// get all posts by User
	List<Post> findByUser(User user); // method name must be in this format : findBy{EnttyName}(Entity)
	
	// get all posts by Category
	List<Post> findByCategory(Category category);
	
	// searching posts by postTitle containing a particular keyword
	List<Post> findByPostTitleContaining(String keyword);
	
}
