package com.currency.currency_module.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.currency_module.model.UserActivityManagement;

public interface UserActivityManagementRepository extends JpaRepository<UserActivityManagement,Long>  {

    Optional<UserActivityManagement> findByUsername(String username);

    Long countByStatus(String status);

    List<UserActivityManagement> findAllByStatus(String status);

 
    
}
