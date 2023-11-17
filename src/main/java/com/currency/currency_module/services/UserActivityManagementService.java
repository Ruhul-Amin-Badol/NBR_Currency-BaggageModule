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
        userActivityManagementRepository.save(userActivityManagement);
    }

    //for user data show
    public List<UserActivityManagement> getAllUsers() {
        return userActivityManagementRepository.findAll();
    
    }

}
