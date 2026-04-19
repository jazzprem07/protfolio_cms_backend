/**
 * @author Prem kumar
 * @date 4/4/26
 * Copyright © 2026
 */
package com.portfolioCMS.repository.profile;

import com.portfolioCMS.entity.profile.Qualification;
import com.portfolioCMS.entity.profile.Workshop;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopRepo extends JpaRepository<Workshop,Long> {

}
