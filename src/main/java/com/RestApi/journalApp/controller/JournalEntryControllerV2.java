package com.RestApi.journalApp.controller;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestApi.journalApp.Entity.JournalEntry;
import com.RestApi.journalApp.Entity.User;
import com.RestApi.journalApp.Services.JournalEntryService;
import com.RestApi.journalApp.Services.UserService;



@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	@Autowired 
	private JournalEntryService journalEntryService;
	@Autowired
	private UserService userService;
	
	
//	Get all entry of user
	@GetMapping
	public ResponseEntity<?> getAllJournalEnteriesOfUser() {	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUsername(userName);
		List<JournalEntry> enteries = user.getJournalEntries();
		
		if(!enteries.isEmpty()) {
			return new ResponseEntity<>(enteries,HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
//	Create New Entry
	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			System.out.println(username);
			myEntry.setDate(LocalDateTime.now());
			journalEntryService.saveEntry(myEntry,username);
			return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
//	Get Entry using Id
	@GetMapping("id/{myId}")
	public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> journalEntries =  user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		if(!journalEntries.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findEntryById(myId);
			if(journalEntry.isPresent()) {
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
		
//	Delete entry
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
		boolean removed = journalEntryService.deleteEntry(myId);
		if(removed)
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("id/{myId}")
	public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> entries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).collect(Collectors.toList());
		if(!entries.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findEntryById(myId);
			if(journalEntry.isPresent()) {
				JournalEntry old = journalEntry.get();
				old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
				old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
				journalEntryService.saveEntry(old);
				return new ResponseEntity<>(old, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
