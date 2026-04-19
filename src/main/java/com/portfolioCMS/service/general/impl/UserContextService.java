/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.general.impl;


import com.portfolioCMS.dto.UserInfoDetails;
import com.portfolioCMS.service.general.IUserContextService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserContextService implements IUserContextService {

	@Override
	public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return "Anonymous"; 
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            userDetails = (UserDetails) principal;
            return userDetails.getUsername(); 
        } else if (principal instanceof String subject) {
            return subject;
        } else {
            return "Unknown User";
        }
    }

	@Override
    public int getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return 0;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            return (int) principal;
        }
        if (principal instanceof UserInfoDetails userDetails) {
        	userDetails = (UserInfoDetails) principal;
            return Math.toIntExact(userDetails.getId());
        }
        return 0;
    }

    
	@Override
    public UserInfoDetails getUserDetailsFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            return (UserInfoDetails) principal;
        }
        if (principal instanceof UserInfoDetails userDetails) {
        	userDetails = (UserInfoDetails) principal;
            return userDetails; 
        }
        return null;
    }

	@Override
    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "ROLE_ANONYMOUS";
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("ROLE_ANONYMOUS");
    }
    
	@Override
    public List<String> getCurrentAllUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new ArrayList<>();
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

	@Override
    public String getNameFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserInfoDetails userDetails) {
        	userDetails = (UserInfoDetails) principal;
            return userDetails.getUsername();
        }
        return null;
    }
	
}