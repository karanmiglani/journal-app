package com.RestApi.journalApp.Services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RestApi.journalApp.Entity.JournalEntry;
import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalRepository;
    
    @Autowired
    private UserService userService;
    
     
    
    public User getUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
    	return userService.findByUsername(userName);
    }
    @Transactional
    public void saveEntry(JournalEntry entry, String username){
    	try {
    		User user = this.getUser();
        	entry.setDate(LocalDateTime.now());
            JournalEntry id =  journalRepository.save(entry);
            user.getJournalEntries().add(id);        
            userService.updateUser(user);
    	}catch(Exception e) {
    		
    		throw new RuntimeException("An error occured while savig the entry.");
    	}
    }
    
    public void saveEntry(JournalEntry entry){
    	entry.setDate(LocalDateTime.now());
    	journalRepository.save(entry);
    }

    public List<JournalEntry> getallEntry(String username){
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id){
        return journalRepository.findById(id);
    }
    
    @Transactional
    public boolean deleteEntry(ObjectId id){
    	boolean removed = false;
    	try {
    		User user = this.getUser();
        	removed =  user.getJournalEntries().removeIf(x ->  x.getId().equals(id));
        	if(removed) {
        		userService.updateUser(user);
                journalRepository.deleteById(id);
        	}
        	return removed;
    	}catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("An error ocuured while deleting the entry");
    	}
    	
    }
    
    public void updateEntry(ObjectId id, JournalEntry entry) {
    	
    }
    
    
    public List<JournalEntry> findByUsername(String username){
    	return null;
    }

}
