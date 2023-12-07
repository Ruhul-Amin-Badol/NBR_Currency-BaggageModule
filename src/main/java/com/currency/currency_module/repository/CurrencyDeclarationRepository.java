package com.currency.currency_module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.currency.currency_module.model.CurrencyDeclaration;


public interface CurrencyDeclarationRepository extends JpaRepository<CurrencyDeclaration,Long> {
    
    List<CurrencyDeclaration> findByStatus(String status);


    int countByStatus(String string);


    long countByStatusAndEntryPoint(String string, String airportname);


    long countByEntryPoint(String totalairport);


    List<CurrencyDeclaration> findAllByEntryPoint(String totalairport);
    //List<CurrencyDeclaration> findById(String id);


    List<CurrencyDeclaration> findByStatusAndEntryPoint(String string, String totalapprove);

    List<CurrencyDeclaration> findAllByOrderByIdDesc();


    List<CurrencyDeclaration> findAllByEntryPointOrderByIdDesc(String officeCode);


    List<CurrencyDeclaration> findByStatusOrderByIdDesc(String string);


    List<CurrencyDeclaration> findByStatusAndEntryPointOrderByIdDesc(String string, String officeCode);


    @Query("SELECT COUNT(DISTINCT c.invoice) FROM CurrencyDeclaration c")
    long countDistinctInvoices();


    // @Query("SELECT COUNT(DISTINCT c.invoice) FROM CurrencyDeclaration c")
    // long countDistinctInvoices();
    
}

