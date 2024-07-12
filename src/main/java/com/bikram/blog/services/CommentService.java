package com.bikram.blog.services;

import com.bikram.blog.payloads.CommentDto;

public interface CommentService {

	// create comment
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	// update comment
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	
	// delete comment
	String deleteComment(Integer commentId);
	
}
