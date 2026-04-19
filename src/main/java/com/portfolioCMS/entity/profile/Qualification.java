/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.entity.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "qualification")
@Data
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Qualification is required")
    private String degree;
    @NotBlank(message = "Institution is required")
    private String institution;
    @NotNull(message = "Passed Out year is required")
    private LocalDate yearOfPassing;
    @NotNull(message = "Joining year is required")
    private LocalDate yearOfJoining;
    private String grade;
}
