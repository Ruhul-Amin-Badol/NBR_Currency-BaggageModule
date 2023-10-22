package com.currency.currency_module.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.currency.currency_module.model.AirportList;



public interface AirportRepository extends JpaRepository<AirportList, Long> {
}


