package com.RestApi.journalApp.Service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.UserRepositoryImpl;

@SpringBootTest
public class UserServiceImpl {
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	

	@Test
	public void getUser() {
		List<User>  users =  userRepositoryImpl.getUsersForSentimentAnalysis();
		for(User user : users) {
			System.out.println(user.getUsername());
		}
	}

}
