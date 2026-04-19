/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile;

import com.portfolioCMS.dto.ProfileDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Profile;

public interface IProfileService {
    ResponseDTO createProfile(Profile req);

    ResponseDTO getAllProfiles();

    ResponseDTO getProfileById(int id);

    ResponseDTO updateProfile(Long id, ProfileDTO req);
}
