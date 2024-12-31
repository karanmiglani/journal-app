	package com.RestApi.journalApp.Entity;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import org.bson.types.ObjectId;
	import org.springframework.data.annotation.Id;
	import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
	import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
	
	import jakarta.validation.constraints.NotNull;
	
	@Document(collection = "users")
	public class User {
		
		@Id
		@JsonSerialize(using = ToStringSerializer.class)
		private ObjectId id;
		@Indexed(unique = true)
		@NotNull(message = "Username can'\t be empty.")
		private String username;
		private String email;
		private boolean sentimentAnalysis;
		@NotNull(message = "Password can'\t be empty.")
		private String password;
		@DBRef
		private List<JournalEntry> journalEntries = new ArrayList<JournalEntry>();
		private List<String> userRoles;
		
	//	Constructors
		public User() {
			super();
	
		}
		public User(ObjectId id, @NotNull(message = "Username can'\t be empty.") String username,
				@NotNull(message = "Password can'\t be empty.") String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
		}
		
	//	Getters and Setters
		public ObjectId getId() {
			return id;
		}
		public void setId(ObjectId id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public List<JournalEntry> getJournalEntries() {
			return journalEntries;
		}
		public void setJournalEntries(List<JournalEntry> journalEntries) {
			this.journalEntries = journalEntries;
		}
		public List<String> getUserRoles() {
			return userRoles;
		}
		public void setUserRoles(List<String> userRoles) {
			this.userRoles = userRoles;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isSentimentAnalysis() {
			return sentimentAnalysis;
		}
		public void setSentimentAnalysis(boolean sentimentAnalysis) {
			this.sentimentAnalysis = sentimentAnalysis;
		}
		
		
		
		
		
	}
