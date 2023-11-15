package com.currency.currency_module.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.currency.currency_module.model.AirportList;


public interface AirportRepository extends JpaRepository<AirportList, Long> {
    Optional<AirportList> findById(Long id);
     Optional<AirportList> findByOfficeCode(String officeCode);

}


