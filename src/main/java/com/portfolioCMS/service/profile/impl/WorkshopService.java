/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.WorkshopDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Workshop;
import com.portfolioCMS.repository.profile.WorkshopRepo;
import com.portfolioCMS.service.profile.IWorkshopService;
import com.portfolioCMS.service.profile.IWorkshopService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService implements IWorkshopService {
    private  final WorkshopRepo repo;

    public WorkshopService(WorkshopRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid WorkshopDTO req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }

        try {
            Workshop entity = new Workshop();
            entity.setTitle(req.getTitle().trim());
            entity.setOrganization(req.getOrganization().trim());
            entity.setDate(req.getDate());
            entity.setDescription(req.getDescription().trim());

            Workshop saved = repo.save(entity);
            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<WorkshopDTO> dtoList = repo.findAll()
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
            Workshop data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            WorkshopDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, WorkshopDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Workshop data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        Workshop updated = repo.save(data);

        return new ResponseDTO(true, "Updated successfully", mapToDTO(updated));
    }

    private void updateFields(Workshop data, WorkshopDTO req) {

        Optional.ofNullable(req.getTitle()).ifPresent(data::setTitle);
        Optional.ofNullable(req.getOrganization()).ifPresent(data::setOrganization);
        Optional.ofNullable(req.getDate()).ifPresent(data::setDate);
        Optional.ofNullable(req.getDescription()).ifPresent(data::setDescription);
    }

    private WorkshopDTO mapToDTO(Workshop data) {
        return  new WorkshopDTO(
                data.getId(), data.getTitle(), data.getOrganization(), data.getDate(), data.getDescription()
        );

    }
}
