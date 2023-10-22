package com.currency.currency_module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.currency_module.model.CurrencyDeclaration;


public interface CurrencyDeclarationRepository extends JpaRepository<CurrencyDeclaration,Long> {
    
    List<CurrencyDeclaration> findByStatus(String status);


    int countByStatus(String string);


    long countByStatusAndEntryPoint(String string, String airportname);


    long countByEntryPoint(String totalairport);


    List<CurrencyDeclaration> findAllByEntryPoint(String totalairport);


    List<CurrencyDeclaration> findByStatusAndEntryPoint(String string, String totalapprove);

    
}

