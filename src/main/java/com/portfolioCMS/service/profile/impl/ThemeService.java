/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.ThemeSettingsDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.ThemeSettings;
import com.portfolioCMS.repository.profile.ThemeRepo;
import com.portfolioCMS.service.profile.IThemeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService implements IThemeService {
    private  final ThemeRepo repo;

    public ThemeService(ThemeRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid ThemeSettingsDTO  req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }
        if (repo.existsByThemeName(req.getThemeName())) {
            return new ResponseDTO(false, "Theme already exists", null);
        }
        try {
            ThemeSettings entity = new ThemeSettings();
            entity.setThemeName(req.getThemeName().trim());
            entity.setPrimaryColor(req.getPrimaryColor().trim());
            entity.setSecondaryColor(req.getSecondaryColor());
            entity.setMode(req.getMode());

            ThemeSettings saved = repo.save(entity);

            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<ThemeSettingsDTO> dtoList = repo.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .toList();

            if (dtoList.isEmpty()) {
                return new ResponseDTO(false, "Data Not found", dtoList);
            }

            return new ResponseDTO(true, "Data fetched successfully", dtoList);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error fetching : " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getById(int id) {
        if (id <= 0) {
            return new ResponseDTO(false, "Invalid ID", null);
        }
        try {
            ThemeSettings data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            ThemeSettingsDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, ThemeSettingsDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        ThemeSettings data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        ThemeSettings updated = repo.save(data);

        return new ResponseDTO(true, "Updated successfully", mapToDTO(updated));
    }

    private void updateFields(ThemeSettings data, ThemeSettingsDTO req) {

        Optional.ofNullable(req.getThemeName()).ifPresent(data::setThemeName);
        Optional.ofNullable(req.getPrimaryColor()).ifPresent(data::setPrimaryColor);
        Optional.ofNullable(req.getSecondaryColor()).ifPresent(data::setSecondaryColor);
        Optional.ofNullable(req.getMode()).ifPresent(data::setMode);
    }

    private ThemeSettingsDTO mapToDTO(ThemeSettings data) {
        return  new ThemeSettingsDTO(
                data.getId(), data.getThemeName(),data.getPrimaryColor(),data.getSecondaryColor(),data.getMode()
        );

    }
}
