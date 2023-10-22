package com.currency.currency_module;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.repository.UserActivityManagementRepository;
import com.currency.currency_module.services.UserActivityManagementService;

import jakarta.servlet.http.HttpSession;

@Service
public class AirportInformation {
    
       @Autowired
       private  UserActivityManagementService userActivityManagementService;




    public  String getAirport(Principal principal) {
  
        String usernameSession=principal.getName();
      
        UserActivityManagement user=userActivityManagementService.findUserWithUserName(usernameSession);
        

        return user.getAirportList().getAirPortNames();
    }


}
