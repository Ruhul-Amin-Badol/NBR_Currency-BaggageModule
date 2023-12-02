package com.currency.currency_module;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.services.UserActivityManagementService;

@Component
public class AirportInformation {
    @Autowired
    private UserActivityManagementService userActivityManagementService;

    public String getAirport(Principal principal ){
        if(principal != null){
        String usernameSession = principal.getName();
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        return user.getAirportList().getOfficeCode();
    }else{
        return "nothing";
    }
    
    }
    public String getUsername(Principal principal ){
    if(principal != null){
        String usernameSession = principal.getName();
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        return usernameSession;
    }else{
        return "nothing";
    }
    }


    public String getEntryPoint(Principal principal ){
        if(principal != null){
        String usernameSession = principal.getName();
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        return user.getAirportList().getAirPortNames();
    }else{
        return "nothing";
    }
    
    }

    
}
