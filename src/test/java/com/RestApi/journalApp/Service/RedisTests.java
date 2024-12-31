package com.RestApi.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	@Test
	@Disabled
	public void test() {
		redisTemplate.opsForValue().set("email", "karan@gmail.com");
		Object email = redisTemplate.opsForValue().get("email");
		Object salary = redisTemplate.opsForValue().get("salary");
		System.out.println(email);
		System.out.println(salary);
		return;
	}
}
