package com.bikram.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikram.blog.entities.User;
import com.bikram.blog.exceptions.ResourceNotFoundException;
import com.bikram.blog.payloads.UserDto;
import com.bikram.blog.repositories.UserRepository;
import com.bikram.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	// create user
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		UserDto savedUserDto = this.userToUserDto(savedUser);		
		return savedUserDto;
	}

	
	// update user
	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User-Id",userId));
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		User updatedUser = this.userRepository.save(user);
		UserDto updateUserDto = this.userToUserDto(updatedUser);
		return updateUserDto;
	}

	
	// delete user
	@Override
	public String deleteUser(int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User-Id", userId));
		this.userRepository.delete(user);
		String message = "User deleted with user-Id : " + userId;
		return message;
	}

	
	// get user by userId
	@Override
	public UserDto getUserById(int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User-Id", userId));
		UserDto userDto = this.userToUserDto(user);
		return userDto;
	}
	
	
	// get all users
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}
	
	// userDto to user
	public User userDtoToUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		return user;
	}
	
	// user to userDto
	public UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setUserEmail(user.getUserEmail());
		userDto.setUserPassword(user.getUserPassword());
		userDto.setUserAbout(user.getUserAbout());
		return userDto;	
	}
	

}
