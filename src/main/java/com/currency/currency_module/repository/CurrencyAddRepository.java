package com.currency.currency_module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.currency_module.model.BaggageCurrencyAdd;


public interface CurrencyAddRepository extends JpaRepository<BaggageCurrencyAdd ,Long> {

    BaggageCurrencyAdd findByCurrencyId(Long id);

    List<BaggageCurrencyAdd> findAllByCurrencyId(Long id);

    
}
