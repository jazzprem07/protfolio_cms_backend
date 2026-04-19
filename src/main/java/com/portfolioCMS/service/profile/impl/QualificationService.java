/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.ProfileDTO;
import com.portfolioCMS.dto.QualificationDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Profile;
import com.portfolioCMS.entity.profile.Qualification;
import com.portfolioCMS.repository.profile.QualificationRepo;
import com.portfolioCMS.service.profile.IQualificationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualificationService implements IQualificationService {
    private  final QualificationRepo repo;

    public QualificationService(QualificationRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid QualificationDTO req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }
        if (repo.existsByDegree(req.getDegree())) {
            return new ResponseDTO(false, "Qualification already exists", null);
        }
        try {
            Qualification entity = new Qualification();
            entity.setDegree(req.getDegree().trim());
            entity.setInstitution(req.getInstitution());
            entity.setYearOfJoining(req.getYearOfJoining());
            entity.setYearOfPassing(req.getYearOfPassing());
            entity.setGrade(req.getGrade());

            Qualification saved = repo.save(entity);

            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<QualificationDTO> dtoList = repo.findAll()
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
            Qualification data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            QualificationDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, QualificationDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Qualification data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        Qualification updated = repo.save(data);

        return new ResponseDTO(true, "Updated successfully", mapToDTO(updated));
    }

    private void updateFields(Qualification data, QualificationDTO req) {

        Optional.ofNullable(req.getDegree()).ifPresent(data::setDegree);
        Optional.ofNullable(req.getInstitution()).ifPresent(data::setInstitution);
        Optional.ofNullable(req.getYearOfPassing()).ifPresent(data::setYearOfPassing);
        Optional.ofNullable(req.getYearOfJoining()).ifPresent(data::setYearOfJoining);
        Optional.ofNullable(req.getGrade()).ifPresent(data::setGrade);
    }

    private QualificationDTO mapToDTO(Qualification data) {
        return  new QualificationDTO(
                data.getId(), data.getDegree(), data.getInstitution(), data.getYearOfPassing(), data.getYearOfJoining(), data.getGrade()
        );

    }
}
