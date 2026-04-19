/**
 * @author Prem kumar
 * @date 3/16/26
 * Copyright © 2026
 */
package com.portfolioCMS.controller.admin;

import com.portfolioCMS.dto.AuthRequest;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.UserMaster;
import com.portfolioCMS.service.admin.IUserMasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class UserMasterController {
    private final IUserMasterService userMasterService;

    public UserMasterController(IUserMasterService userMasterService) {
        super();
        this.userMasterService = userMasterService;
    }

    @PostMapping("/register")
    public UserMaster register(@RequestBody UserMaster admin) {
        return userMasterService.registerUserMaster(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        ResponseDTO response = userMasterService.login(request);
        return ResponseEntity.ok(response);
    }


}
