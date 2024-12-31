package com.RestApi.journalApp.controller;
//package com.RestApi.journalApp.Controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.RestApi.journalApp.Entity.JournalEntry;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//	private Map<Long, JournalEntry> journalEnteries = new HashMap<>();
//	
//	@GetMapping
//	public List<JournalEntry> getAll() {
//		return new ArrayList<>(journalEnteries.values());
//	}
//	
//	@PostMapping
//	public boolean createEntry(@RequestBody JournalEntry myEntry) {
//		journalEnteries.put(myEntry.getId() , myEntry);
//		return true;
//		
//	}
//	@GetMapping("id/{myId}")
//	public JournalEntry getEntryById(@PathVariable Long myId) {
//		return journalEnteries.get(myId);
//	}
//}
