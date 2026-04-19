/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.service.profile.impl;

import com.portfolioCMS.dto.CertificateDTO;
import com.portfolioCMS.dto.CertificateDTO;
import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.entity.profile.Certificate;
import com.portfolioCMS.entity.profile.Certificate;
import com.portfolioCMS.repository.profile.CertificateRepo;
import com.portfolioCMS.repository.profile.CertificateRepo;
import com.portfolioCMS.service.profile.ICertificateService;
import com.portfolioCMS.service.profile.ICertificateService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService implements ICertificateService {
    private  final CertificateRepo repo;

    public CertificateService(CertificateRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public ResponseDTO create(@Valid CertificateDTO req) {
        if (req == null) {
            return new ResponseDTO(false, "Request cannot be null", null);
        }
       
        try {
            Certificate entity = new Certificate();
            entity.setTitle(req.getTitle().trim());
            entity.setIssuer(req.getIssuer().trim());
            entity.setIssueDate(req.getIssueDate());
            entity.setCertificateUrl(req.getCertificateUrl().trim());


            Certificate saved = repo.save(entity);

            return new ResponseDTO(true, "Saved Successfully", saved);

        } catch (Exception e) {
            return new ResponseDTO(false, "Error while saving: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseDTO getAll() {
        try {
            List<CertificateDTO> dtoList = repo.findAll()
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
            Certificate data = repo.findById((long) id)
                    .orElseThrow(() -> new RuntimeException("Data not found with ID: " + id));

            CertificateDTO dto = mapToDTO(data);

            return new ResponseDTO(true, "Data fetched successfully", dto);

        } catch (RuntimeException ex) {
            return new ResponseDTO(false, ex.getMessage(), null);

        } catch (Exception ex) {
            return new ResponseDTO(false, "Internal server error", null);
        }
    }

    @Override
    public ResponseDTO update(Long id, CertificateDTO req) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        if (req == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Certificate data = repo.findById(id)
                .orElseThrow(() -> new  RuntimeException("Data not found with ID: " + id));

        updateFields(data, req);

        Certificate updated = repo.save(data);

        return new ResponseDTO(true, "Updated successfully", mapToDTO(updated));
    }

    private void updateFields(Certificate data, CertificateDTO req) {

        Optional.ofNullable(req.getTitle()).ifPresent(data::setTitle);
        Optional.ofNullable(req.getIssuer()).ifPresent(data::setIssuer);
        Optional.ofNullable(req.getCertificateUrl()).ifPresent(data::setCertificateUrl);
        Optional.ofNullable(req.getIssueDate()).ifPresent(data::setIssueDate);
    }

    private CertificateDTO mapToDTO(Certificate data) {
        return  new CertificateDTO(
                data.getId(), data.getTitle(), data.getIssuer(), data.getIssueDate(), data.getCertificateUrl()
        );

    }
}
