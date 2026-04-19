/**
 * 
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * 
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.service.general;


import com.portfolioCMS.dto.UserInfoDetails;

import java.util.List;

public interface IUserContextService {

	String getCurrentUser();
	int getUserIdFromContext();
	UserInfoDetails getUserDetailsFromContext();
	String getCurrentUserRole();
	List<String> getCurrentAllUserRole();
	String getNameFromContext();
}
