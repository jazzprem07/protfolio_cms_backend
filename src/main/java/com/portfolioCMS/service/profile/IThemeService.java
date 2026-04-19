/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile;

import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.dto.ThemeSettingsDTO;
import jakarta.validation.Valid;

public interface IThemeService {
    ResponseDTO create(@Valid ThemeSettingsDTO req);

    ResponseDTO getAll();

    ResponseDTO getById(int id);

    ResponseDTO update(Long id, @Valid ThemeSettingsDTO req);
}
