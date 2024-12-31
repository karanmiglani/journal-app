package com.RestApi.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.UserRepository;
import com.RestApi.journalApp.Services.UserService;
import com.RestApi.journalApp.Services.WeatherService;
import com.RestApi.journalApp.api.Response.WeatherResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WeatherService weatherService;

	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		try {
			List<User> users =  userService.findAllUsers();
		
			return new ResponseEntity<>(users, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("id/{myId}")
	public ResponseEntity<User> getUser(@PathVariable ObjectId id){
		try {
			Optional<User> user = userService.getUserById(id);
			if(user.isPresent()) {
				return new ResponseEntity<User>(user.get() , HttpStatus.FOUND);
			}else {
				return new ResponseEntity<User>(user.get() , HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("delete-user")
	public ResponseEntity<Void> deleteUser(){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			userRepository.deleteByUsername(authentication.getName());
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	

	
	
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(	@RequestBody User user){
		try {
			Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			User oldUser = userService.findByUsername(userName);
			oldUser.setUsername(user.getUsername());
			oldUser.setPassword(user.getPassword());
			userService.createUser(oldUser);
			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/quotes")
	public ResponseEntity<?> greetings(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse weatherResponse = weatherService.getWeather("Rohtak");
		String greeting =  "";
		if(weatherResponse != null) {
			greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike(); 
		}
		
		return new ResponseEntity<>("Hi " + authentication.getName() + greeting ,HttpStatus.OK);
	}
	
}
