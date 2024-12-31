package com.RestApi.journalApp.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	
	private final String SECRET_KEY = "a984ff0f66493021d238e1b9436d5e5853d23ca5f02fb38b97f5b11bbc33514f";
	
	
	private SecretKey getSignKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}
	
	public String generateToken(String username) {
		Map<String , Object> claims = new HashMap<>();
		return this.createToken(claims, username);
	}
	
	
	private String createToken(Map<String, Object> claims , String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.header().empty().add("typ","JWT")
				.and()
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 *60*60)) //60 minutes expirtion time
				.signWith(getSignKey())
				.compact();
				
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
