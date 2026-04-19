/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.admin;

import com.portfolioCMS.dto.AuthRequest;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.UserMaster;

public interface IUserMasterService {
    public UserMaster registerUserMaster(UserMaster admin);

    ResponseDTO login(AuthRequest request);
}
