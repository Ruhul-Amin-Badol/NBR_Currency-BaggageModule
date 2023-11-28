package com.currency.currency_module.services;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.PpmInfo;
import com.currency.currency_module.repository.PpmInfoRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import org.springframework.web.multipart.MultipartFile;



@Service
public class PpmInfoService {



    @Autowired 
   PpmInfoRepository ppmInfoRepository;



   public Integer ppmCount(Integer applicationType){
    return ppmInfoRepository.countByApplicationType(applicationType);
}


   public PpmInfo insertPpm (PpmInfo ppmInfo,MultipartFile image) {

                          try {
            // Get a reference to the storage service
            var storage = StorageClient.getInstance().bucket();

            // Generate a unique filename for the uploaded file
            
            String fileName ="ppfuploads/ppmdoc"+ppmInfo.getId();

            // Upload the file to Firebase Storage
            storage.create(fileName, image.getInputStream(), image.getContentType());

            String downloadUrl = storage.get(fileName).signUrl(73000, java.util.concurrent.TimeUnit.DAYS).toString();
            ppmInfo.setUploadFile(downloadUrl);
             
             
             return ppmInfoRepository.save(ppmInfo);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
            
        }


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
