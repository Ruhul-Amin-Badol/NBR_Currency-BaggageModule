package com.currency.currency_module.services;


import com.currency.currency_module.model.PaymentHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryService extends JpaRepository<PaymentHistory,Long> {

    

    
}
