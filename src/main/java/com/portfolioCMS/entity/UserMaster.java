/**
 * @author Prem kumar
 * @date 3/16/26
 * Copyright © 2026
 */
package com.portfolioCMS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "userMaster")
public class UserMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;
}
