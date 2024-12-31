package com.RestApi.journalApp.Services;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.RestApi.journalApp.api.Response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);  
	
	
	
	public <T> T get(String Key, Class<T> entityClass) {
		try {
			Object o = redisTemplate.opsForValue().get(Key);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(o.toString(), entityClass);
		}catch(Exception e) {
			logger.error("An error occured in redis service while getting the key",e);
			return null;
		}
	}
	
	
	public void set(String key , Object o , Long ttl) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonValue = mapper.writeValueAsString(o);
			redisTemplate.opsForValue().set(key, jsonValue,ttl,TimeUnit.SECONDS);
		}catch(Exception e) {
			logger.error("An error occured in redis service while setting the key",e);
			return;
		}
	}
}
