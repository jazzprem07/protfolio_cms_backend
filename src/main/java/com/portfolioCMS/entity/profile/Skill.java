/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.entity.profile;

import com.portfolioCMS.general.SkillLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill is Required")
    private String skillName;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel; // Example: Beginner, Intermediate, Advanced
}