package com.bikram.blog.services;

import java.util.List;

import com.bikram.blog.payloads.UserDto;

public interface UserService {

	// create user
	UserDto createUser(UserDto userDto);
	
	// update user
	UserDto updateUser(UserDto userDto, int userId);
	
	// delete user
	String deleteUser(int userId);
	
	// get user by userId
	UserDto getUserById(int userId);
	
	// get all users
	List<UserDto> getAllUsers();
}
