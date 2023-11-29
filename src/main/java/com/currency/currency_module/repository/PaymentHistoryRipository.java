package com.currency.currency_module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.currency_module.model.PaymentHistory;

public interface PaymentHistoryRipository extends JpaRepository<PaymentHistory,Long>  {

    
}