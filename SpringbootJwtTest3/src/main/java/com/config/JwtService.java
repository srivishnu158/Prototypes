package com.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SEC_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	public String extractUsername(String jwt) {
		
		return extractClaims(jwt,Claims::getSubject);
	}

	private <T>T extractClaims(String jwt, Function<Claims,T> func) {
		Claims claim = extractAllClaims(jwt);
		return func.apply(claim);
	}

	private Claims extractAllClaims(String jwt) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
	}

	public String generateToken(UserDetails user) {
		
		return generateToken(user,new HashMap<>());
	}

	private String generateToken(UserDetails user, Map<String,Object> hashMap) {
	 return Jwts.builder()
		.setClaims(hashMap)
		.setSubject(user.getUsername())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 80 * 1000 * 24 ))
		.signWith(getSigningKey(), SignatureAlgorithm.HS256)
		.compact();
	 }

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SEC_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isTokenNotExpaired(String jwt) {
		Date date = getExpiration(jwt);
		System.out.println(new Date());
		System.out.print("Exp: "+date);
		return date.after(new Date());
	}

	private Date getExpiration(String jwt) {
		
		return extractClaims(jwt,Claims::getExpiration);
	}
	

}
