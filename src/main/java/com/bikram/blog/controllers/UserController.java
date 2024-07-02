package com.bikram.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bikram.blog.payloads.UserDto;
import com.bikram.blog.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	
	// create user
	@PostMapping("/create-user")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUserDto,HttpStatus.CREATED);
	}
	
	
	
	// update user
	@PutMapping("/update-user/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @RequestParam int userId){
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUserDto,HttpStatus.OK);
	}
	
	// delete user
	@DeleteMapping("/delete-user/{userId}")
	public ResponseEntity<String> deleteUser(@RequestParam int userId){
		String message = this.userService.deleteUser(userId);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	// get user by id
	@GetMapping("/get-user/{userId}")
	public ResponseEntity<UserDto> getUserById(@RequestParam int userId){
		UserDto userDto = this.userService.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	// get all users
	@GetMapping("/get-all-users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> userDtos = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
	}
}
