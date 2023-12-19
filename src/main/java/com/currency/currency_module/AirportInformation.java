package com.currency.currency_module;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.OrganizationList;
import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.repository.OrganizationRepository;
import com.currency.currency_module.services.UserActivityManagementService;

@Component
public class AirportInformation {
    @Autowired
    private UserActivityManagementService userActivityManagementService;
    @Autowired
    private OrganizationRepository organizationRepository;

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
    public String getOrganization(Principal principal ){
       // System.out.println("principal============================================"+principal);
        if(principal != null){
        String usernameSession = principal.getName();
        //System.out.println("usernameSession============================================"+usernameSession);
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        OrganizationList organization=organizationRepository.findById(Long.parseLong(user.getOrganizationId())).orElseThrow(()->new ResourceNotFound("User not found"));
        
        
        return organization.getOriganizationName();
    }else{
        return "nothing";
    }
    
    }

    
}
