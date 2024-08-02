package com.bikram.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikram.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	// find user by userEmail : (this will be used in CustomUserDetailsService to fetch 'User' (UserDetails) by username ('userEmail')
	Optional<User> findByUserEmail(String userEmail);
}
