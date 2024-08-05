package com.bikram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikram.blog.payloads.ApiResponse;
import com.bikram.blog.payloads.CommentDto;
import com.bikram.blog.services.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerScheme")	// for implementation of security in swagger
@RequestMapping("/")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	// create comment
	@PostMapping("/posts/{postId}/comments/create-comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdCommentDto,HttpStatus.CREATED);
	}
	
	// update comment
	@PutMapping("/comments/update-comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId) {
		CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updatedCommentDto,HttpStatus.OK);
	}
	
	// delete comment
	@DeleteMapping("/comments/delete-comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		String message = this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(message,true),HttpStatus.OK);
	}
	
}
