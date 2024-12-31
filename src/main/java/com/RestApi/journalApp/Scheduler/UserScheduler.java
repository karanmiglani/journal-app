package com.RestApi.journalApp.Scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.RestApi.journalApp.Entity.JournalEntry;
import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Enums.Sentiment;
import com.RestApi.journalApp.Repository.UserRepositoryImpl;
import com.RestApi.journalApp.Services.EmailService;
import com.RestApi.journalApp.cache.AppCache;

@Component
public class UserScheduler {

	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	
	
	@Autowired
	private AppCache appCache;
	
	@Scheduled(cron =  "0 0/1 * 1/1 * ?")
	public void fethUsersAndSendSaMails() {
		List<User> users = userRepositoryImpl.getUsersForSentimentAnalysis();
		for(User user : users) {
			List<JournalEntry> journalEntries = user.getJournalEntries();
			List<Sentiment> sentiments =  journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
			Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
			for(Sentiment sentiment : sentiments) {
				if(sentiment != null) {
					sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) +1);
				}
				Sentiment mostFrequSentiment = null;
				int maxCount = 0;
				for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
					if(entry.getValue() > maxCount) {
							maxCount = entry.getValue();
							mostFrequSentiment = entry.getKey();
					}
				}
				if(mostFrequSentiment != null) {
//					emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", "You were "+ mostFrequSentiment.toString() + " in last 7 days.");
				}
			}
			
		}
	}
	
	@Scheduled(cron =  "0 0/10 * 1/1 * ?")
	public void clearAppCache() {
			appCache.init();
	}
}
