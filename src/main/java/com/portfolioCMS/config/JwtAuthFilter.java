/**
 *
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 *
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.config;


import com.portfolioCMS.exceptions.TokenRelatedException;
import com.portfolioCMS.service.token.IJwtService;
import com.portfolioCMS.service.token.impl.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final IJwtService jwtService;
	private final UserInfoService userDetailsService;

	public JwtAuthFilter(IJwtService jwtService, UserInfoService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7).trim();
				username = jwtService.extractUsername(token);
			}

			if (username != null &&
					SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails =
						userDetailsService.loadUserByUsername(username);

				if (Boolean.TRUE.equals(jwtService.validateToken(token, userDetails))) {

					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities()
							);

					authToken.setDetails(
							new WebAuthenticationDetailsSource()
									.buildDetails(request)
					);

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

		} catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token Expired");
			return;
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid Token");
			return;
		}

		filterChain.doFilter(request, response);
	}
}