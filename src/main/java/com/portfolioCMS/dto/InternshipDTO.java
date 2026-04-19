/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipDTO {

    private Long id;

    private String companyName;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}