/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile;

import com.portfolioCMS.dto.InternshipDTO;
import com.portfolioCMS.dto.ResponseDTO;
import jakarta.validation.Valid;

public interface IInternshipService {
    ResponseDTO create(@Valid InternshipDTO req);

    ResponseDTO getAll();

    ResponseDTO getById(int id);

    ResponseDTO update(Long id, @Valid InternshipDTO req);
}
