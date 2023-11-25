package com.currency.currency_module.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.PpmInfo;
import com.currency.currency_module.repository.PpmInfoRepository;



@Service
public class PpmInfoService {
       @Autowired 
   PpmInfoRepository ppmInfoRepository;






   public PpmInfo insertPpm (PpmInfo ppmInfo) {
    return ppmInfoRepository.save(ppmInfo);
   }


   public List<PpmInfo> getAllPpmInfo() {
    return ppmInfoRepository.findAll();
   }


}
