/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.dto;

import com.portfolioCMS.entity.profile.ThemeSettings;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeSettingsDTO {

    private Long id;
    private String themeName;
    private String primaryColor;
    private String secondaryColor;
    private Mode mode;

    public ThemeSettingsDTO(Long id, @NotBlank(message = "Theme name is Required") String themeName, String primaryColor, String secondaryColor, ThemeSettings.Mode mode) {
    }

    public enum Mode {
        LIGHT,
        DARK
    }
}