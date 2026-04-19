/**
 * @author Prem kumar
 * @date 3/19/26
 * Copyright © 2026
 */
package com.portfolioCMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;
    private String address;
    private String phone;
    private String profileImage;
    private String summary;
    private String title;
}
