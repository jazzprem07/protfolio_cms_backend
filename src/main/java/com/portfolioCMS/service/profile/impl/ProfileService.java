/**
 * @author Prem kumar
 * @date 3/17/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.ProfileDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Profile;
import com.portfolioCMS.repository.profile.ProfileRepo;
import com.portfolioCMS.service.profile.IProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService implements IProfileService {
    private final ProfileRepo repo;

    public ProfileService(ProfileRepo repo) {
        this.repo = repo;
    }

    @Override
    public ResponseDTO createProfile(Profile req) {
        return new ResponseDTO(true, "Saved Successfully", repo.save(req));
    }

    @Override
    public ResponseDTO getAllProfiles() {
        try {
            List<ProfileDTO> dtoList = repo.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .toList();

            if (dtoList.isEmpty()) {
                return new ResponseDTO(false, "No profiles found", dtoList);
            }

            return new ResponseDTO(true, "Data fetched successfully", dtoList);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error fetching profiles: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getProfileById(int id) {
        if (id <= 0) {
            return new ResponseDTO(false, "Invalid profile ID", null);
        }
        try {
            Profile profile = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Profile not found with ID: " + id));

            ProfileDTO dto = mapToDTO(profile);

            return new ResponseDTO(true, "Profile fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO updateProfile(Long id, ProfileDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid profile ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Profile profile = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Profile not found with ID: " + id));

        updateProfileFields(profile, req);

        Profile updated = repo.save(profile);

        return new ResponseDTO(true, "Profile updated successfully", mapToDTO(updated));
    }

    private ProfileDTO mapToDTO(Profile profile) {
        return new ProfileDTO(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getAddress(),
                profile.getPhone(),
                profile.getProfileImage(),
                profile.getSummary(),
                profile.getTitle()
        );
    }

    private void updateProfileFields(Profile profile, ProfileDTO req) {

        Optional.ofNullable(req.getName()).ifPresent(profile::setName);
        Optional.ofNullable(req.getEmail()).ifPresent(profile::setEmail);
        Optional.ofNullable(req.getAddress()).ifPresent(profile::setAddress);
        Optional.ofNullable(req.getPhone()).ifPresent(profile::setPhone);
        Optional.ofNullable(req.getProfileImage()).ifPresent(profile::setProfileImage);
        Optional.ofNullable(req.getSummary()).ifPresent(profile::setSummary);
        Optional.ofNullable(req.getTitle()).ifPresent(profile::setTitle);
    }
}
