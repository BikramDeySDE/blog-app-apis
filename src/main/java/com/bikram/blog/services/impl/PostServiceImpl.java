
package com.bikram.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikram.blog.entities.Category;
import com.bikram.blog.entities.Post;
import com.bikram.blog.entities.User;
import com.bikram.blog.exceptions.ResourceNotFoundException;
import com.bikram.blog.payloads.CategoryDto;
import com.bikram.blog.payloads.PostDto;
import com.bikram.blog.payloads.UserDto;
import com.bikram.blog.repositories.CategoryRepository;
import com.bikram.blog.repositories.PostRepository;
import com.bikram.blog.repositories.UserRepository;
import com.bikram.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	// create post 
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User-Id", userId));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Catgory-Id", categoryId));
		
		Post post = this.postDtoToPost(postDto);
		
		post.setUser(user);
		post.setCategory(category);
		post.setPostImageName("Default.png");
		post.setPostAddedDate(new Date());
		
		Post savedPost = this.postRepository.save(post);
		
		PostDto savedPostDto = this.postToPostDto(savedPost); // here user and category are also converted to userDto and categoryDto respectively
		// added these two lines to get userDto and categoryDto as output #NeedToCheck
		savedPostDto.setUserDto(this.modelMapper.map(savedPost.getUser(), UserDto.class));
		savedPostDto.setCategoryDto(this.modelMapper.map(savedPost.getCategory(),CategoryDto.class));
		
		return savedPostDto;
	}





	// update post
	
	@Override
	public PostDto updatePostDto(PostDto postDto, Integer postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post-Id", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostDescription(postDto.getPostDescription());
		post.setPostImageName(postDto.getPostImageName());
		
		Post updatedPost = this.postRepository.save(post);
		
		PostDto updatedPostDto = this.postToPostDto(updatedPost);
		
		return updatedPostDto;
	}


	
	
	// delete post

	@Override
	public String deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post-Id", postId));
		this.postRepository.delete(post);
		String message = "Post is deleted with Post-Id : " + postId;
		return message;
	}
	
	

	// find post by Id
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post-Id", postId));
		PostDto postDto = this.postToPostDto(post);
		return postDto;
	}

	
	
	// get all posts by user
	
	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User-Id", userId));
		
		List<Post> posts = this.postRepository.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.postToPostDto(post)).collect(Collectors.toList());
		
		return postDtos;
	}
	
	
	
	// get all posts by category

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category-Id", categoryId));
		
		List<Post> posts =  this.postRepository.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.postToPostDto(post)).collect(Collectors.toList());
		
		return postDtos;
	}
	
	
	
	// get all posts

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = this.postRepository.findAll();
		List<PostDto> postDtos = posts.stream().map((post)->this.postToPostDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		return null;
	}

	
	// postDtoToPost
	public PostDto postToPostDto(Post post) {
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}
	
	// post to postDto
	public Post postDtoToPost(PostDto postDto) {
		Post post = this.modelMapper.map(postDto, Post.class);
		return post;
	}
	
	
}
