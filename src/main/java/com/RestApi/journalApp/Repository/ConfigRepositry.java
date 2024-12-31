package com.RestApi.journalApp.Repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.RestApi.journalApp.Entity.ConfigJournalAppEntity;

@Repository
public interface ConfigRepositry extends MongoRepository<ConfigJournalAppEntity , ObjectId> {
	
}
