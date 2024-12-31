package com.RestApi.journalApp.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.RestApi.journalApp.Entity.User;

public class UserRepositoryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<User> getUsersForSentimentAnalysis(){
		Query query = new Query();
		
		query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", "i"));
		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//		Criteria criteria = new Criteria();
//		query.addCriteria(criteria.andOperator(
//				Criteria.where("email").exists(true),
//				Criteria.where("email").ne(null).ne(""),
//				Criteria.where("sentimentAnalysis").is(true)
//));
		
		
		List<User> users =  mongoTemplate.find(query, User.class);
		return users;
		
	}
}
