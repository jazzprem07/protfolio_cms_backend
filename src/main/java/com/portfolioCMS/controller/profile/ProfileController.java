/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.controller.profile;

import com.portfolioCMS.dto.ProfileDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Profile;
import com.portfolioCMS.service.profile.IProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final IProfileService service;

    public ProfileController(IProfileService service) {
        super();
        this.service = service;
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDTO> createProfile(@Valid @RequestBody Profile req){
        ResponseDTO res = service.createProfile(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("getall")
    public ResponseEntity<?> getAllProfiles(){
        ResponseDTO res = service.getAllProfiles();
        return ResponseEntity.ok(res);
    }

    @GetMapping("getprofile/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable int id){
        ResponseDTO res = service.getProfileById(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping("updateprofile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id,@Valid @RequestBody ProfileDTO req){
        ResponseDTO res = service.updateProfile(id,req);
        return ResponseEntity.ok(res);
    }
}
