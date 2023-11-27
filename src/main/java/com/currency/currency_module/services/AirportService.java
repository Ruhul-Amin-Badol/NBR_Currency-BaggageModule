package com.currency.currency_module.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;

import com.currency.currency_module.repository.AirportRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.AirportList;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;


import java.util.List;


@Service
public class AirportService {
   @Autowired 
   AirportRepository airportRepository;


  public AirportService() {

    try {
      // Initialize Firebase Admin SDK
      InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("static/apikeyfirbase.json");

      if (serviceAccount == null) {
          throw new IllegalArgumentException("Firebase credentials file not found.");
      }

      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setStorageBucket("nbrbd-47f44.appspot.com")  // Set this to your Firebase Storage bucket URL
              .build();

      FirebaseApp.initializeApp(options);
  } catch (IOException e) {
      e.printStackTrace();
  } catch (Exception e) {
      e.printStackTrace();
  }
  }




   public List<AirportList> getAllAirports() {
        return airportRepository.findAll();
    }

    public AirportList createAirport(AirportList createAirport,MultipartFile image) {

                try {
            // Get a reference to the storage service
            var storage = StorageClient.getInstance().bucket();

            // Generate a unique filename for the uploaded file
            
            String fileName ="airports/"+createAirport.getAirPortNames();

            // Upload the file to Firebase Storage
            storage.create(fileName, image.getInputStream(), image.getContentType());

            String downloadUrl = storage.get(fileName).signUrl(73000, java.util.concurrent.TimeUnit.DAYS).toString();
            createAirport.setImage(downloadUrl);
             
             return airportRepository.save(createAirport);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
        }
     
    }

    public AirportList findAirport(long id) {
      return airportRepository.findById(id).orElseThrow(()->new ResourceNotFound("User not found"));
    }

    public AirportList findAirportByOfficeCode(String officeCode) {
      return airportRepository.findByOfficeCode(officeCode).orElseThrow(()->new ResourceNotFound("User not found"));
    }



    public AirportList updateAirport(AirportList airportList,MultipartFile image, Principal principal){

    

           try {
            // Get a reference to the storage service
            var storage = StorageClient.getInstance().bucket();

            // Generate a unique filename for the uploaded file
            String usernameSession = principal.getName();
            String fileName ="airports/"+airportList.getAirPortNames();

            // Upload the file to Firebase Storage
            storage.create(fileName, image.getInputStream(), image.getContentType());

            String downloadUrl = storage.get(fileName).signUrl(73000, java.util.concurrent.TimeUnit.DAYS).toString();

      AirportList existingAirport = airportRepository.findById(airportList.getId()).orElseThrow(()->new ResourceNotFound("User not found"));
     if (existingAirport != null) {

        existingAirport.setAirPortNames(airportList.getAirPortNames());
        existingAirport.setOfficeCode(airportList.getOfficeCode());
        existingAirport.setBankBranchName(airportList.getBankBranchName());
        existingAirport.setBankBranchCode(airportList.getBankBranchCode());
        existingAirport.setImage(downloadUrl);
         AirportList updatedAirport = airportRepository.save(existingAirport);
         return updatedAirport;
     } else {
         return null;
     }

           



            // Return the URL of the uploaded file
            // return "https://storage.googleapis.com/your-firebase-storage-bucket-url/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
        }









      
    }
}
