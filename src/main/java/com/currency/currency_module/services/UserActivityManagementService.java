package com.currency.currency_module.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.repository.UserActivityManagementRepository;

@Service
public class UserActivityManagementService {
    @Autowired
    UserActivityManagementRepository userActivityManagementRepository;

    public UserActivityManagement findUserWithUserName(String username) {
        UserActivityManagement userinfo=userActivityManagementRepository.findByUsername(username).orElseThrow(()->new ResourceNotFound("User not found"));
        return userinfo;

    }
    //for user data insert
    public void saveUserActivityManagement(UserActivityManagement userActivityManagement) {
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

    public void deleteUserById(Long userId) {
        if (!userActivityManagementRepository.existsById(userId)) {
            throw new ResourceNotFound("User not found with id: " + userId);
        }
        userActivityManagementRepository.deleteById(userId);
    }
    

}
