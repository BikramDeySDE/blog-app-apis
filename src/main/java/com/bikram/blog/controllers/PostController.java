package com.bikram.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikram.blog.config.AppConstants;
import com.bikram.blog.payloads.ApiResponse;
import com.bikram.blog.payloads.PostDto;
import com.bikram.blog.payloads.PostResponse;
import com.bikram.blog.services.FileService;
import com.bikram.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	// path to where images should be uploaded
	@Value("${project.image}")
	private String folderPath;
	
	// create new post
	@PostMapping("/user/{userId}/category/{categoryId}/posts/create-post")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	// update post
	@PutMapping("/posts/update-post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
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
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue =  AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	
	// search posts by post-title containing a particular keyword
	@GetMapping("/posts/search-posts/post-title/{keyword}")
	ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	
	
	
	// upload image : upload image corresponding to a post
	@PostMapping("posts/{postId}/files/upload-image")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile imageFile, @PathVariable Integer postId) throws IOException{
		
		// get the post by 'postId'
		PostDto postDto = this.postService.getPostById(postId);
		
		// upload Image
		String imageFileName = this.fileService.uploadImage(imageFile, folderPath);
		
		// setting image file name in the post
		postDto.setPostImageName(imageFileName);
		
		// update the post (in DB)
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		// return the updatedPostDto
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
		
	}
	
	
	
	
	// download image (serve image)
	@GetMapping(value = "files/download-image/{imageFileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageFileName, HttpServletResponse response) throws IOException{
		
		// getting the imageFile as inputStream
		InputStream resource = this.fileService.downloadImage(folderPath, imageFileName);
		
		// setting response
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		// copying resource (got from the service method call) to response (so that when we will fire the url, image should be displayed on the page)
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
}
