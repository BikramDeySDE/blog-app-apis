package com.bikram.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bikram.blog.entities.User;
import com.bikram.blog.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// fetch user ('UserDetails' : as 'User' class implements the interface 'UserDetails') by username (here we will consider 'userEmail' as username)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUserEmail(username);
		return user;
	}

}
