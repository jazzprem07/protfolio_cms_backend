/**
 * @author Prem kumar
 * @date 3/16/26
 * Copyright © 2026
 */
package com.portfolioCMS.repository.admin;

import com.portfolioCMS.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster,Long>{
    Optional<UserMaster> findByUsername(String username);

}

