/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.entity.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "workshops")
@Data
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @NotBlank(message = "Organization is required")
    private String organization;

    private LocalDate date;

    @Column(length = 1500)
    private String description;
}
