/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.config;


import com.portfolioCMS.service.token.IJwtService;
import com.portfolioCMS.service.token.impl.UserInfoService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	private IJwtService jwtService;
	private UserInfoService userDetailsService;
	
	public FilterConfig(IJwtService jwtService, UserInfoService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	FilterRegistrationBean<JwtAuthFilter> loggingFilter() {
		FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthFilter(jwtService, userDetailsService));
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
}
