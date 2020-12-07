package com.example.memegenerator.shared;

import java.util.Date;

import com.example.memegenerator.websecurity.SecurityConstants;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utils {

	public static boolean hasTokenExpired(String token) {
        boolean expired = false;

		try {
            Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token).getBody();

			Date tokenExpirationDate = claims.getExpiration();
			Date todayDate = new Date();

			expired = tokenExpirationDate.before(todayDate);
		} catch (ExpiredJwtException ex) {
			return expired;
		}

		return expired;
	}

    public String generateEmailVerificationToken(Integer userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
    }
    
    public String generatePasswordResetToken(Integer userId)
    {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
    }
}