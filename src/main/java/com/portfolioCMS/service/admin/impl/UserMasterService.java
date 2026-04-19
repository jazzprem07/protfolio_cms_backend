/**
 * @author Prem kumar
 * @date 3/16/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.admin.impl;

import com.portfolioCMS.dto.AuthRequest;
import com.portfolioCMS.dto.JwtTokenResDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.UserMaster;
import com.portfolioCMS.repository.admin.UserMasterRepository;
import com.portfolioCMS.service.admin.IUserMasterService;
import com.portfolioCMS.service.token.IJwtService;
import com.portfolioCMS.service.token.impl.UserInfoService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UserMasterService implements IUserMasterService {
    private final UserMasterRepository userMasterRepository;
    private final UserInfoService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;


    public UserMasterService(UserMasterRepository userMasterRepository, UserInfoService userDetailsService, PasswordEncoder passwordEncoder, IJwtService jwtService) {
        super();
        this.userMasterRepository = userMasterRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserMaster registerUserMaster(UserMaster admin) {

        admin.setCreatedAt(LocalDateTime.now());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return userMasterRepository.save(admin);
    }

    public ResponseDTO login(AuthRequest request) {

        try {
            Optional<UserMaster> optionalUser =
                    userMasterRepository.findByUsername(request.getUsername());

            // ✅ Check user exists
            if (optionalUser.isEmpty()) {
                return new ResponseDTO(false, "User not found");
            }

            UserMaster user = optionalUser.get();

            // ✅ Check password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new ResponseDTO(false, "Invalid username or password");
            }

            // ✅ Generate JWT
            JwtTokenResDTO tokenRes = jwtService.generateToken(user.getUsername(), user.getId());


            return new ResponseDTO(true, "",tokenRes);

        } catch (Exception e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

}
