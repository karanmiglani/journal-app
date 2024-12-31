package com.RestApi.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Services.UserService;
import com.RestApi.journalApp.cache.AppCache;


@RestController
@RequestMapping("/admin")
public class AdminCotroller {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppCache appCache;
	
	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers() {
		List<User> users =  userService.findAllUsers();
		if(!users.isEmpty() && users != null) {
			return new ResponseEntity<>(users , HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/clear-app-cache")
	public void clearAppCache() {
		appCache.init();
	}
}
