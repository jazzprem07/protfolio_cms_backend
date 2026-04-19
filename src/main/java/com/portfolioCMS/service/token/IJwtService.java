/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.token;

import com.portfolioCMS.dto.JwtTokenResDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.UserMaster;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface IJwtService {

//	ResponseDTO generateToken(String userName, UserMaster userAuth);
	JwtTokenResDTO generateToken(String userName, Long id);
	JwtTokenResDTO generateTokenByRefreshToken(String userName, String refreshToken);
	String extractUsername(String token);
	Date extractExpiration(String token);
	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);
	Boolean isTokenExpired(String token);
	Boolean validateToken(String token, UserDetails userDetails);
}
