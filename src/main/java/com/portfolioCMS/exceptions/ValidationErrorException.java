/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.exceptions;

public class ValidationErrorException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ValidationErrorException(String msg) {
		super(msg);
	}
	

}