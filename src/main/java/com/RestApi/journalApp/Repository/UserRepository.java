package com.RestApi.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.RestApi.journalApp.Entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
	User findByUsername(String username);
	void deleteByUsername(String username);
}
