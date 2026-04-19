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
@Table(name = "job_experience")
@Data
public class JobExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Position Name is required")
    private String position;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    @Column(length = 1500)
    private String description;
}