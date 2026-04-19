/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.entity.profile;

import com.portfolioCMS.dto.ThemeSettingsDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "theme_settings")
@Data
public class ThemeSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Theme name is Required")
    private String themeName;


    private String primaryColor;
    private String secondaryColor;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    public void setMode(ThemeSettingsDTO.Mode mode) {

    }

    public enum Mode {
        LIGHT,
        DARK
    }
}