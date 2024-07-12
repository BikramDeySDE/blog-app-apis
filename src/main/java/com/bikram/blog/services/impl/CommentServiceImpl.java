package com.bikram.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikram.blog.entities.Comment;
import com.bikram.blog.entities.Post;
import com.bikram.blog.exceptions.ResourceNotFoundException;
import com.bikram.blog.payloads.CommentDto;
import com.bikram.blog.repositories.CommentRepository;
import com.bikram.blog.repositories.PostRepository;
import com.bikram.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// create comment
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Comment comment = this.commentDtoToComment(commentDto);
		
		// getting comment by id
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post-Id", postId));
		
		// set post in comment
		comment.setPost(post);
		
		Comment createdComment = this.commentRepository.save(comment);
		CommentDto createdCommentDto = this.commentToCommentDto(createdComment);
		return createdCommentDto;
	}

	
	// update comment
	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment-Id",commentId));
		comment.setCommentContent(commentDto.getCommentContent());
		Comment updatedComment = this.commentRepository.save(comment);
		CommentDto updatedCommentDto = this.commentToCommentDto(updatedComment);
		return updatedCommentDto;
	}

	// delete comment
	@Override
	public String deleteComment(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment-Id", commentId));
		this.commentRepository.delete(comment);
		String message = "Comment Deleted with Comment-Id : " + commentId;
		return message;
	}

	
	// commentDto to comment
	private Comment commentDtoToComment(CommentDto commentDto) {
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		return comment;
		
	}
	
	// comment to commentDto
	private CommentDto commentToCommentDto(Comment comment) {
		CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}
	
	
}
