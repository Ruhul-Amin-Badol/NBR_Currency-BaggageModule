package com.currency.currency_module.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.repository.PvtInfoRepository;



@Service
public class PvtinfoService {

   @Autowired 
   PvtInfoRepository pvtInfoRepository;


   public PvtInfo insertPvt (PvtInfo pvtInfo) {
    return pvtInfoRepository.save(pvtInfo);
   }


   public List<PvtInfo> getAllPvtInfo() {
    return pvtInfoRepository.findAll();
}
    
}
