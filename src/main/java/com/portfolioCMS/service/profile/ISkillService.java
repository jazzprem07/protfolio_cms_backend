/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile;

import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.dto.SkillDTO;
import jakarta.validation.Valid;

public interface ISkillService {
    ResponseDTO create(@Valid SkillDTO req);

    ResponseDTO getAll();

    ResponseDTO getById(int id);

    ResponseDTO update(Long id, @Valid SkillDTO req);
}
