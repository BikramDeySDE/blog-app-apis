package com.bikram.blog.services;

import java.util.List;

import com.bikram.blog.payloads.UserDto;

public interface UserService {

	// create user
	UserDto createUser(UserDto userDto);
	
	// update user
	UserDto updateUser(UserDto userDto, Integer userId);
	
	// delete user
	String deleteUser(Integer userId);
	
	// get user by userId
	UserDto getUserById(Integer userId);
	
	// get all users
	List<UserDto> getAllUsers();
}
