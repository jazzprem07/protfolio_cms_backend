/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.token.impl;


import com.portfolioCMS.dto.JwtTokenResDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.UserMaster;
import com.portfolioCMS.service.token.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService implements IJwtService {

	@Value("${jwt.secretkey}")
	public String secretKey;

	
	long accessTokenExpiredInMin = 3600000; // 1 hrs
	long refreshTokenExpiredInMin = 28800000; // 8 hrs

    private UserInfoService userDetailsService;

    public JwtService(UserInfoService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    @Override
    public JwtTokenResDTO generateToken(String userName, Long id) {

        Map<String, Object> claims = new HashMap<>();

        Date accessTokenExpiration =
                new Date(System.currentTimeMillis() + accessTokenExpiredInMin);

        Date refreshTokenExpiration =
                new Date(System.currentTimeMillis() + refreshTokenExpiredInMin);

        String accessToken = createToken(claims, userName, accessTokenExpiration);
        String refreshToken = refreshToken(claims, userName, refreshTokenExpiration);

        String tempToken = "";

        return new JwtTokenResDTO(
                accessToken,
                refreshToken,
                accessTokenExpiration, // ✅ better
                userName,
                id,
                tempToken
        );
    }
    
    @Override
    public JwtTokenResDTO generateTokenByRefreshToken(String userName, String refreshToken) {
    	Map<String, Object> claims = extractAllClaims(refreshToken);
        Date accessTokenExpiration = new Date(System.currentTimeMillis() + accessTokenExpiredInMin);
        String accessToken = createToken(claims, userName, accessTokenExpiration);
        UserMaster userDetails = (UserMaster) userDetailsService.loadUserByUsername(userName);
        String tempToken = "";
        return new JwtTokenResDTO(accessToken, refreshToken, accessTokenExpiration,userName, userDetails.getId(),tempToken);
    }

    // Create a JWT token with specified claims and subject (user name)
    private String createToken(Map<String, Object> claims, String userName, Date accessTokenExpiration) {
    	return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiration) // Token valid ACCESS_TOKEN_EXPIRED_IN_MIN minutes
                .signWith(getSignKey())
                .compact();
    	
    }
    
    private String refreshToken(Map<String, Object> claims, String userName, Date refreshTokenExpiration) {
    	return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(refreshTokenExpiration) // Token valid ACCESS_TOKEN_EXPIRED_IN_MIN minutes
                .signWith(getSignKey())
                .compact();
    	
    }

    // Get the signing key for JWT token
    private SecretKey getSignKey() {
    	 return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Extract the username from the token
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()   // ✅ correct
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
