package com.RestApi.journalApp.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Disabled
	@Test
	public void testsFindByUsername() {
		assertEquals(4,2+2);
		User user = userRepository.findByUsername("User1");
		assertTrue(!user.getJournalEntries().isEmpty());
		assertNotNull(userRepository.findByUsername("User1"));
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,1,2",
		"2,10,12",
		"3,3,9"
	})
	public void test(int a , int b , int expected) {
		assertEquals(expected, a+b);
	}
}
