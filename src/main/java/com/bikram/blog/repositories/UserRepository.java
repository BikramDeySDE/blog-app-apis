package com.bikram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
