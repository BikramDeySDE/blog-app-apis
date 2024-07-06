package com.bikram.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bikram.blog.payloads.ApiResponse;
import com.bikram.blog.payloads.PostDto;
import com.bikram.blog.payloads.PostResponse;
import com.bikram.blog.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class PostController {

	@Autowired
	private PostService postService;
	
	// create new post
	@PostMapping("/user/{userId}/category/{categoryId}/posts/create-post")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	// update post
	@PutMapping("/posts/update-post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = this.postService.updatePostDto(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	
	// delete post
	@DeleteMapping("/posts/delete-post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		String message = this.postService.deletePost(postId);
		ApiResponse response = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	
	
	
	
	// get post by post-id
	@GetMapping("/posts/get-post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	
	
	
	// get all posts by user
	
	@GetMapping("/user/{userId}/posts/get-all-posts")
	public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable Integer userId){
		List<PostDto> postDtos = this.postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	
	// get all posts by category
	@GetMapping("/category/{categoryId}/posts/get-all-posts")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> postDtos = this.postService.getAllPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	// get all posts
	@GetMapping("/posts/get-all-posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue =  "5", required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, @RequestParam(value = "sortDirection", defaultValue = "ascending", required = false) String sortDirection) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
}
