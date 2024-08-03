package com.bikram.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bikram.blog.config.AppConstants;
import com.bikram.blog.entities.Role;
import com.bikram.blog.entities.User;
import com.bikram.blog.exceptions.ResourceNotFoundException;
import com.bikram.blog.payloads.RoleDto;
import com.bikram.blog.payloads.UserDto;
import com.bikram.blog.repositories.RoleRepository;
import com.bikram.blog.repositories.UserRepository;
import com.bikram.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
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
	public UserDto updateUser(UserDto userDto, Integer userId) {
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
	public String deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User-Id", userId));
		this.userRepository.delete(user);
		String message = "User deleted with user-Id : " + userId;
		return message;
	}

	
	// get user by userId
	@Override
	public UserDto getUserById(Integer userId) {
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
	
	
	// register new user
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// convert userDto to user
		User user = this.modelMapper.map(userDto, User.class);
		// set encoded password
		user.setUserPassword(this.passwordEncoder.encode(user.getPassword())); // getting password from user > encode the password > set the encoded password into the user
		// set roles for ROLE_NORMAL
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER_ROLE_ID).get(); // fetch role from DB
		user.getRoles().add(role);	// add role
		// create (register) new user
		User registeredUser = this.userRepository.save(user);
		// convert user to userDto
		UserDto registeredUserDto = this.modelMapper.map(registeredUser, UserDto.class);
		// return new created (registered) user 
		return registeredUserDto;
	}
	
		// userDto to user (using modelMapper)
		public User userDtoToUser(UserDto userDto) {
			User user = this.modelMapper.map(userDto, User.class);
			return user;
		}
		
		// user to userDto (using modelMapper)
		public UserDto userToUserDto(User user) {
			UserDto userDto = this.modelMapper.map(user, UserDto.class);
			return userDto;	
		}

	
	
	/*
	
	// userDto to user (manually)
	public User userDtoToUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		return user;
	}
	
	// user to userDto (manually)
	public UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setUserEmail(user.getUserEmail());
		userDto.setUserPassword(user.getUserPassword());
		userDto.setUserAbout(user.getUserAbout());
		return userDto;	
	}
	
	*/

}
