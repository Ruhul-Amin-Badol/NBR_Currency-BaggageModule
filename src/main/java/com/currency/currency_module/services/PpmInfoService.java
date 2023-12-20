package com.currency.currency_module.services;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.PpmInfo;
import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.repository.PpmInfoRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;


@Service
public class PpmInfoService {



    @Autowired 
   PpmInfoRepository ppmInfoRepository;



   public Integer ppmCount(Integer applicationType, String username){
    return ppmInfoRepository.countByApplicationTypeAndEntryBy(applicationType,username);
}




   public PpmInfo insertPpm (PpmInfo ppmInfo,MultipartFile image,Principal principal) {
            try {
            // Get a reference to the storage service
            var storage = StorageClient.getInstance().bucket();

            // Generate a unique filename for the uploaded file
            
            String fileName ="ppfuploads/ppmdoc"+ppmInfo.getId();

            // Upload the file to Firebase Storage
            storage.create(fileName, image.getInputStream(), image.getContentType());

            String downloadUrl = storage.get(fileName).signUrl(73000, java.util.concurrent.TimeUnit.DAYS).toString();
            ppmInfo.setEntryBy(principal.getName());
            ppmInfo.setUploadFile(downloadUrl);
             
             
             return ppmInfoRepository.save(ppmInfo);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
            
        }


   }


   public List<PpmInfo> getAllPpmInfo(String organigation,Principal principal) {

    if(organigation.equalsIgnoreCase("all")){
        return ppmInfoRepository.findAll();
    }else{
         return ppmInfoRepository.findAllByOrganizationNameAndEntryBy(organigation,principal.getName());
    }
    
   }


    public List<PpmInfo> passbookReport(Integer applicationType,String username) {
      

        return ppmInfoRepository.findByApplicationTypeAndEntryBy(applicationType,username);
   }

    public List<PpmInfo> carTransferRepost(Integer applicationType,String username) {
        return ppmInfoRepository.findByApplicationTypeAndEntryBy(applicationType,username);
   }

    public List<PpmInfo> carSaleRepost(Integer applicationType,String username) {
        return ppmInfoRepository.findByApplicationTypeAndEntryBy(applicationType,username);
   }

   public PpmInfo viewDetailsById(Long id) {
    return ppmInfoRepository.findById(id).orElse(null);
 }



}
