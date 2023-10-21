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
        String usernameSession = principal.getName();

        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);

        return user.getAirportList().getAirPortNames();

    }
}
