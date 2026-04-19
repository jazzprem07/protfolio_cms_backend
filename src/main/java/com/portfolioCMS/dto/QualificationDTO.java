/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDTO {
    private Long id;

    private String degree;
    private String institution;
    private LocalDate yearOfPassing;
    private LocalDate yearOfJoining;
    private String grade;
}
