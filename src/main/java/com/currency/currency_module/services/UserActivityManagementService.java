package com.currency.currency_module.services;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.repository.UserActivityManagementRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class UserActivityManagementService {

    @Autowired
    UserActivityManagementRepository userActivityManagementRepository;

    public UserActivityManagement findUserWithUserName(String username) {
        
        UserActivityManagement userinfo=userActivityManagementRepository.findByUsername(username).orElseThrow(()->new ResourceNotFound("User not found"));
        return userinfo;

    }
    //for user data insert
    public void saveUserActivityManagement(UserActivityManagement userActivityManagement,MultipartFile image) {

                String imageName = image.getOriginalFilename();
                String imageType = image.getContentType();
                String fileExtension = imageType.substring(imageType.lastIndexOf("/") + 1);
                String imageurl="http://13.232.110.60/"+userActivityManagement.getUsername()+fileExtension;
        // Check if the user has an ID
        if (userActivityManagement.getUserId() != null) {
            UserActivityManagement existingUser = userActivityManagementRepository.findById(userActivityManagement.getUserId())
                    .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userActivityManagement.getUserId()));
            // Update the existing user with new data
            existingUser.setUsername(userActivityManagement.getUsername());
            existingUser.setFname(userActivityManagement.getFname());
            existingUser.setPassword(userActivityManagement.getPassword());
            existingUser.setDesignation(userActivityManagement.getDesignation());
            existingUser.setStatus(userActivityManagement.getStatus());
            existingUser.setLevel(userActivityManagement.getLevel());
            existingUser.setEntryDate(userActivityManagement.getEntryDate());
            existingUser.setExpireDate(userActivityManagement.getExpireDate());
            existingUser.setEmployeeId(userActivityManagement.getEmployeeId());
            existingUser.setAirportList(userActivityManagement.getAirportList());

            try {
            
                String saveDirectory = "./signatures"; // specify the directory path
                File saveFile = new File(saveDirectory);

                if (!saveFile.exists()) {
                    saveFile.mkdirs(); // create the directory if it doesn't exist
                }

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + userActivityManagement.getUsername()+fileExtension);
				
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String fileLocation = path.toString();

                System.out.println("=============================>=============");
                System.out.println(fileLocation);
                System.out.println("=============================>==============");
                userActivityManagement.setSignature(imageurl);
             
            

			} catch (Exception e) {
				e.printStackTrace();
			}

           existingUser.setSignature(userActivityManagement.getSignature());
            

            userActivityManagementRepository.save(existingUser);
        } else {

   

        //                    try {
        //     // Get a reference to the storage service
        //     var storage = StorageClient.getInstance().bucket();

        //     // Generate a unique filename for the uploaded file
            
        //     String fileName ="signatures/"+userActivityManagement.getUsername();

        //     // Upload the file to Firebase Storage
        //     storage.create(fileName, image.getInputStream(), image.getContentType());

        //     String downloadUrl = storage.get(fileName).signUrl(73000, java.util.concurrent.TimeUnit.DAYS).toString();
        //     userActivityManagement.setSignature(downloadUrl);
             
        //      userActivityManagementRepository.save(userActivityManagement);

        // } catch (IOException e) {
        //     e.printStackTrace();
        //     // Handle the exception
            
        // }

        			try {
            
                String saveDirectory = "./signatures"; // specify the directory path
                File saveFile = new File(saveDirectory);

                if (!saveFile.exists()) {
                    saveFile.mkdirs(); // create the directory if it doesn't exist
                }

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + userActivityManagement.getUsername()+fileExtension);
				
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String fileLocation = path.toString();

                System.out.println("=============================>=============");
                System.out.println(fileLocation);
                System.out.println("=============================>==============");
                userActivityManagement.setSignature(imageurl);
                userActivityManagementRepository.save(userActivityManagement);

			} catch (Exception e) {
				e.printStackTrace();
			}

            
        }
    }

    //roll manage update service

    public void saverollUserActivityManagement(UserActivityManagement userActivityManagement) {
        // Check if the user has an ID
        if (userActivityManagement.getUserId() != null) {
            UserActivityManagement existingUser = userActivityManagementRepository.findById(userActivityManagement.getUserId())
                    .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userActivityManagement.getUserId()));
            // Update the existing user with new data
            existingUser.setUsername(userActivityManagement.getUsername());
            existingUser.setFname(userActivityManagement.getFname());
            existingUser.setPassword(userActivityManagement.getPassword());
            existingUser.setDesignation(userActivityManagement.getDesignation());
            existingUser.setStatus(userActivityManagement.getStatus());
            existingUser.setLevel(userActivityManagement.getLevel());
            existingUser.setEntryDate(userActivityManagement.getEntryDate());
            existingUser.setExpireDate(userActivityManagement.getExpireDate());
            existingUser.setEmployeeId(userActivityManagement.getEmployeeId());
            existingUser.setAirportList(userActivityManagement.getAirportList());
            existingUser.setUserMatrix(userActivityManagement.getUserMatrix());
            existingUser.setBaggageModule(userActivityManagement.getBaggageModule());
            existingUser.setCurrencyModule(userActivityManagement.getCurrencyModule());
            existingUser.setPaymentRecord(userActivityManagement.getPaymentRecord());
            existingUser.setPort(userActivityManagement.getPort());
            existingUser.setPaymentHistory(userActivityManagement.getPaymentHistory());
            

            userActivityManagementRepository.save(existingUser);
        } else {
            userActivityManagementRepository.save(userActivityManagement);
        }
    }


    //for user data show
    public List<UserActivityManagement> getAllUsers() {
        return userActivityManagementRepository.findAll();
    
    }
    public UserActivityManagement getUserById(Long userId) {
        return userActivityManagementRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userId));
    }
    public UserActivityManagement getUserByUserName(String userName) {
        return userActivityManagementRepository.findByUsername(userName)
        .orElseThrow(() -> new ResourceNotFound("User not found with id: " + userName));
    }
    public void deleteUserById(Long userId) {
        if (!userActivityManagementRepository.existsById(userId)) {
            throw new ResourceNotFound("User not found with id: " + userId);
        }
        userActivityManagementRepository.deleteById(userId);
    }
    
     public Long countalluser() {
        return userActivityManagementRepository.count();
    }
     public Long countallactiveuser() {
        return userActivityManagementRepository.countByStatus("Active");
    }

     public Long countallinactiveuser() {
        return userActivityManagementRepository.countByStatus("Inactive");
    }
     public List<UserActivityManagement> findAllActiveuser() {
        return userActivityManagementRepository.findAllByStatus("Active");
    }
     public List<UserActivityManagement> findAllinActiveuser() {
        return userActivityManagementRepository.findAllByStatus("Inactive");
    }

}
