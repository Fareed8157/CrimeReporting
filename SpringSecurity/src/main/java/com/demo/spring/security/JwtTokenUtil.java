package com.demo.spring.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable{

	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 916360118716149216L;
	
	static final String CLAM_KEY_USERNAME="sub";
	static final String CLAM_KEY_AUDIENCE="audience";
	static final String CLAM_KEY_CREATED="created";

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String getUsernameFromToken(String authToken) {
		String username;
		try {
			
			final Claims claims=getClaimsFromToken(authToken);
			username=claims.getSubject();
			
		} catch (Exception e) {
			username=null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String authToken) {
		Claims claims=null;
		
		try {
			
			claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
			
		} catch (Exception e) {
			claims=null;
		}
		return claims;
	}

	public boolean validateToken(String authToken, UserDetails userDetails) {
		JwtUser user=(JwtUser)userDetails;
		final String username=getUsernameFromToken(authToken);
		return (username.equals(user.getUsername()) && !isTokenExpired(authToken));
	}

	private boolean isTokenExpired(String authToken) {
		final Date expiration=getExpirationFromToken(authToken);
		return expiration.before(new Date());
	}

	private Date getExpirationFromToken(String authToken) {
		Date expiration=null;
		try {
			
			final Claims claims=getClaimsFromToken(authToken);
			if(claims!=null) {
				expiration=claims.getExpiration();
			}else {
				expiration=null;
			}
			
		} catch (Exception e) {
			expiration=null;
		}
		return expiration;
	}

	public String generateToken(JwtUser userDetails) {
		Map<String,Object> claims=new HashMap<String,Object>();
		claims.put(CLAM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpiration()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpiration() {
		return new Date(System.currentTimeMillis()+expiration*1000);
	}

}
