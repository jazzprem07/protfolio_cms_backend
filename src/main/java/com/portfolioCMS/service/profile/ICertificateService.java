/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile;

import com.portfolioCMS.dto.CertificateDTO;
import com.portfolioCMS.dto.ResponseDTO;
import jakarta.validation.Valid;

public interface ICertificateService {
    ResponseDTO create(@Valid CertificateDTO req);

    ResponseDTO getAll();

    ResponseDTO getById(int id);

    ResponseDTO update(Long id, @Valid CertificateDTO req);
}
