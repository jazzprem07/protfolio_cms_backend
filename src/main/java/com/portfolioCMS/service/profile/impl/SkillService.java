/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.SkillDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Skill;
import com.portfolioCMS.repository.profile.SkillRepo;
import com.portfolioCMS.service.profile.ISkillService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService implements ISkillService {
    private  final SkillRepo repo;

    public SkillService(SkillRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid SkillDTO req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }

        try {
            Skill entity = new Skill();
            entity.setSkillName(req.getSkillName().trim());
            entity.setSkillLevel(req.getSkillLevel());

            Skill saved = repo.save(entity);

            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<SkillDTO> dtoList = repo.findAll()
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
            Skill data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            SkillDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, SkillDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Skill data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        Skill updated = repo.save(data);

        return new ResponseDTO(true, "updated successfully", mapToDTO(updated));
    }

    private void updateFields(Skill data, SkillDTO req) {

        Optional.ofNullable(req.getSkillName()).ifPresent(data::setSkillName);
        Optional.ofNullable(req.getSkillLevel()).ifPresent(data::setSkillLevel);

    }
    private SkillDTO mapToDTO(Skill data) {
        return  new SkillDTO(
                data.getId(), data.getSkillName(),data.getSkillLevel()
        );

    }
}
