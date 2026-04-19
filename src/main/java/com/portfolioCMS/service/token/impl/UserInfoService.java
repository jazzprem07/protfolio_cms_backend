/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.token.impl;


import com.portfolioCMS.dto.UserInfoDetails;
import com.portfolioCMS.entity.UserMaster;
import com.portfolioCMS.exceptions.CustomSQLException;
import com.portfolioCMS.repository.admin.UserMasterRepository;
import com.portfolioCMS.service.general.IGeneralFunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Primary
public class UserInfoService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	private final UserMasterRepository userMasterRepository;

	public UserInfoService(UserMasterRepository userMasterRepository) {
		this.userMasterRepository = userMasterRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		 userDetail = null;
		UserMaster userDetail = userMasterRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		return new UserInfoDetails(userDetail);
	}

}
