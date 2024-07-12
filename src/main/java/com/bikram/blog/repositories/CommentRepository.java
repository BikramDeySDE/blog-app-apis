package com.bikram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
