/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
	@JsonProperty("Status")
	public boolean status = true;

	@JsonProperty("Message")
	public String message = "";
	
	@JsonProperty("Data")
	public Object data = null;

	public ResponseDTO(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

//	public ResponseDTO(JwtTokenResDTO jwtTokenResDTO) {
//	}
}