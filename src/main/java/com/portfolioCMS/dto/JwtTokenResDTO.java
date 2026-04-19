/**
 *
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 * <p>
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class JwtTokenResDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("tokenType")
    private String tokenType = "Bearer";

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("tokenExpire")
    private Date tokenExpire;

    @JsonProperty("userType")
    private String userType;

    @JsonProperty("username")
    private String username;
    
    @JsonProperty("userId")
    private Long id;
    
    @JsonProperty("email")
    private String email;
    

    @JsonProperty("tempToken")
    private String tempToken;

    
    public JwtTokenResDTO() {
        super();
    }
    
    public JwtTokenResDTO(String accessToken, String refreshToken, Date tokenExpire,
                        String username, Long id,
                        String tempToken) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenExpire = tokenExpire;
        this.username = username;
        this.id = id;
        this.tempToken = tempToken;
    }
    

}
