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



   public Integer ppmCount(Integer applicationType){
    return ppmInfoRepository.countByApplicationType(applicationType);
}


   public PpmInfo insertPpm (PpmInfo ppmInfo) {
    return ppmInfoRepository.save(ppmInfo);
   }


   public List<PpmInfo> getAllPpmInfo() {
    return ppmInfoRepository.findAll();
   }


    public List<PpmInfo> passbookReport(Integer applicationType) {
        return ppmInfoRepository.findByApplicationType(applicationType);
   }

    public List<PpmInfo> carTransferRepost(Integer applicationType) {
        return ppmInfoRepository.findByApplicationType(applicationType);
   }

    public List<PpmInfo> carSaleRepost(Integer applicationType) {
        return ppmInfoRepository.findByApplicationType(applicationType);
   }



}
