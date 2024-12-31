package com.RestApi.journalApp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user != null) {
			 UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
														  .username(user.getUsername())
														  .password(user.getPassword())
														  .roles(user.getUserRoles().toArray(new String[0]))
														  .build();
			 return userDetails;
		}
		System.out.println("Loaded user: " + null);
		throw new UsernameNotFoundException("User not found with username:" + username);
		
	}

}
