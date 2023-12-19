package com.currency.currency_module.services;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.repository.PvtInfoRepository;



@Service
public class PvtinfoService {

   @Autowired 
   PvtInfoRepository pvtInfoRepository;
   
   //Privilage CURD Oparation

   public PvtInfo insertPvt (PvtInfo pvtInfo,Principal principal) {
    pvtInfo.setEntryBy(principal.getName());
    return pvtInfoRepository.save(pvtInfo);
   }


   public List<PvtInfo> getAllPvtInfo(String organigation,Principal principal) {
    if(organigation.equalsIgnoreCase("all")){
        return pvtInfoRepository.findAll();
    }else{
        return pvtInfoRepository.findAllByOrgNameAndEntryBy(organigation,principal.getName());
    }
    
}

   public PvtInfo getPvtInfoEdit(Long id) {
    return pvtInfoRepository.findById(id).orElse(null);
 }

 public void updatePvt(PvtInfo updatedPvtInfo) {
    if (updatedPvtInfo.getId() != null) {
        // Retrieve the existing PvtInfo from the database
        PvtInfo existingPvtInfo = getPvtInfoEdit(updatedPvtInfo.getId());

    // Check if the existingPvtInfo is not null
    if (existingPvtInfo != null) {
        // Update the fields of the existingPvtInfo with the new values
        existingPvtInfo.setBillEntryNo(updatedPvtInfo.getBillEntryNo());
        existingPvtInfo.setBillEntryDate(updatedPvtInfo.getBillEntryDate());
        existingPvtInfo.setOrgName(updatedPvtInfo.getOrgName());
        existingPvtInfo.setPassBookNo(updatedPvtInfo.getPassBookNo());
        existingPvtInfo.setCpbValidity(updatedPvtInfo.getCpbValidity());
        existingPvtInfo.setCpbHolder(updatedPvtInfo.getCpbHolder());
        existingPvtInfo.setVehicleRegNo(updatedPvtInfo.getVehicleRegNo());
        existingPvtInfo.setVehicleName(updatedPvtInfo.getVehicleName());
        existingPvtInfo.setCustomHosue(updatedPvtInfo.getCustomHosue());
        existingPvtInfo.setRemarks(updatedPvtInfo.getRemarks());
        existingPvtInfo.setCc(updatedPvtInfo.getCc());
        existingPvtInfo.setEngNo(updatedPvtInfo.getEngNo());
        existingPvtInfo.setChasisNo(updatedPvtInfo.getChasisNo());
        existingPvtInfo.setYrOfManf(updatedPvtInfo.getYrOfManf());

        pvtInfoRepository.save(existingPvtInfo);
    }
}

 }

    public void deletePvt(Long id) {
        pvtInfoRepository.deleteById(id);
}

//dashboard count

    public Long privilageCount(){
        return pvtInfoRepository.count();
    }

}
