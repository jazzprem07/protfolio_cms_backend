/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.JobExperienceDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.JobExperience;
import com.portfolioCMS.repository.profile.ExperienceRepo;
import com.portfolioCMS.service.profile.IExperienceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService implements IExperienceService {
    private  final ExperienceRepo repo;

    public ExperienceService(ExperienceRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid JobExperienceDTO req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }

        try {
            JobExperience entity = new JobExperience();
            entity.setCompanyName(req.getCompanyName().trim());
            entity.setPosition(req.getPosition());
            entity.setStartDate(req.getStartDate());
            entity.setEndDate(req.getEndDate());
            entity.setDescription(req.getDescription().trim());


            JobExperience saved = repo.save(entity);

            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<JobExperienceDTO> dtoList = repo.findAll()
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
            JobExperience data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            JobExperienceDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, JobExperienceDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        JobExperience data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        JobExperience updated = repo.save(data);

        return new ResponseDTO(true, "Updated successfully", mapToDTO(updated));
    }

    private void updateFields(JobExperience data, JobExperienceDTO req) {

        Optional.ofNullable(req.getCompanyName()).ifPresent(data::setCompanyName);
        Optional.ofNullable(req.getPosition()).ifPresent(data::setPosition);
        Optional.ofNullable(req.getStartDate()).ifPresent(data::setStartDate);
        Optional.ofNullable(req.getEndDate()).ifPresent(data::setEndDate);
        Optional.ofNullable(req.getDescription()).ifPresent(data::setDescription);
    }

    private JobExperienceDTO mapToDTO(JobExperience data) {
        return  new JobExperienceDTO(
                data.getId(),data.getCompanyName(),data.getPosition(),data.getStartDate(),data.getEndDate(), data.getDescription()
        );

    }
}
