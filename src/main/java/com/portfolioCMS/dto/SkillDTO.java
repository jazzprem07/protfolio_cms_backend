/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.dto;

import com.portfolioCMS.general.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDTO {

    private Long id;

    private String skillName;

    private SkillLevel skillLevel; // Example: Beginner, Intermediate, Advanced
}