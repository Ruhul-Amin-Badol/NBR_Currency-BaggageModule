package com.currency.currency_module.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.currency.currency_module.model.OrganizationList;




public interface OrganizationRepository  extends JpaRepository<OrganizationList, Long>{
    Optional<OrganizationList> findById(Long id);
}
