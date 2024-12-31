package com.RestApi.journalApp.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	private static final PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public boolean createUser(User user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setUserRoles(Arrays.asList("USER"));
			userRepository.save(user);
			System.out.println("Helloooo....");
			return true;
		}catch(Exception e) {
			logger.info("Error Occurred for {} ",user.getUsername(),e);
			logger.info("Hahahahahaha");
			logger.warn("Hahahahahaha");
			logger.debug("Hahahahahaha");
//			logger.trace("Hahahahahaha");
//			e.printStackTrace();
			return false;
		}
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getUserById(ObjectId id){
		return userRepository.findById(id);
	}
	
	public boolean deleteUser(ObjectId id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
}
