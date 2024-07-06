package com.bikram.blog.services;

import java.util.List;

import org.hibernate.sql.Update;

import com.bikram.blog.payloads.PostDto;

public interface PostService {

	
	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// update post
	PostDto updatePostDto(PostDto postDto, Integer postId);
	
	// delete post
	String deletePost(Integer postId);
	
	// get post by id
	PostDto getPostById(Integer postId);
	
	// get all posts by User
	List<PostDto> getAllPostsByUser(Integer userId);
	
	// get all posts by category
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	// get all posts
	List<PostDto> getAllPosts();
	
	// search posts from a particular keyword
	List<PostDto> searchPosts(String keyword);
	
}
