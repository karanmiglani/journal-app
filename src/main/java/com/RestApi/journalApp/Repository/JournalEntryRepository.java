package com.RestApi.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.RestApi.journalApp.Entity.JournalEntry;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
	
}
