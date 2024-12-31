package com.RestApi.journalApp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.RestApi.journalApp.api.Response.WeatherResponse;
import com.RestApi.journalApp.cache.AppCache;

@Component
public class WeatherService {
	@Value("${WEATHER_API_KEY}")
	private  String apiKey;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppCache appCache;
	
	@Autowired
	private RedisService redisService;
	
	public WeatherResponse getWeather(String city) {
		WeatherResponse weatherResponse =  redisService.get("weather_of_" + city,WeatherResponse.class);
		if(weatherResponse != null) {
			System.out.println("Getting from redis");
			return weatherResponse;
		}else {
			String finalApi = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace("<API_KEY>", apiKey).replace("<CITY>", city);
			System.out.println(finalApi);
			ResponseEntity<WeatherResponse> response =  restTemplate.exchange(finalApi, HttpMethod.GET,null,WeatherResponse.class);
			 WeatherResponse body =  response.getBody();
			 if(body != null) {
				 redisService.set("weather_of_"+city, body, (long) 300);
			 }
			 return body;
		}
		
	}
	
}
