package com.RestApi.journalApp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Services.UserDetailServiceImpl;
import com.RestApi.journalApp.Services.UserService;
import com.RestApi.journalApp.Utils.JwtUtil;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) {
			System.out.println("Creating user");
		try {
			userService.createUser(user);
			user.setPassword(null);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			UserDetails userDetails =  userDetailServiceImpl.loadUserByUsername(user.getUsername());
			String token =  jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(token, HttpStatus.OK);
		}catch(Exception e) {
			LOGGER.error("Error while generating token",e);
			return new ResponseEntity<>("Incorrect Username nd Password", HttpStatus.BAD_REQUEST);
		}
	}
}




