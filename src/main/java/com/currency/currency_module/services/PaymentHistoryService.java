package com.currency.currency_module.services;


import com.currency.currency_module.model.PaymentHistory;
import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.repository.PaymentHistoryRipository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryService {
@Autowired
PaymentHistoryRipository paymentHistoryRipository;

  public void insertPaymehistory (PaymentHistory paymentHistory) {
      paymentHistoryRipository.save(paymentHistory);
   }

    
}
