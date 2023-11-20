package com.currency.currency_module.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.services.AirportService;
import com.currency.currency_module.services.UserActivityManagementService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pvt")
public class privilegeaController {
    @Autowired
   UserActivityManagementService userActivityManagementService;
   @Autowired
   AirportInformation airportInformation;
    @Autowired
    AirportService airportService;
   
   
   @Autowired
   HttpSession httpSession;

    @GetMapping("/signinpvt") 
    public String loginpvt() {
        return "loginpvt";
    }



    // @GetMapping("/pvtdashboard") 
    // public String pvtdashboard() {
        
    //     return "pvtdashboard";
    // }
    
}
