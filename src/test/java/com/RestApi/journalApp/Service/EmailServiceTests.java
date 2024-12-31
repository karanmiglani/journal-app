package com.RestApi.journalApp.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.RestApi.journalApp.Services.EmailService;

@SpringBootTest
public class EmailServiceTests {
	@Autowired
	private EmailService emailService;
	
	@Test
	public void Test() {
		emailService.sendEmail("it.1803270@gmail.com", "Checking mail send", "Hello Karan! Mail send from spring boot");
	}
}
