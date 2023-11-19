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
@RequestMapping("/")
public class HomeController {
    @Autowired
   UserActivityManagementService userActivityManagementService;
   @Autowired
   AirportInformation airportInformation;
    @Autowired
    AirportService airportService;
   
   
   @Autowired
   HttpSession httpSession;

    @GetMapping("/signin") 
    public String login() {
        return "login";
    }


      @GetMapping("/") 
    public String index(Model model) {
        model.addAttribute("allAirportList", airportService.getAllAirports());
        return "index_0";
    }


    @GetMapping("/index1") 
    public String index1(@RequestParam(required = false, defaultValue = "") String officeCode, Model model) {
        model.addAttribute("airport", airportService.findAirportByOfficeCode(officeCode));
        return "index1";
    }



    @GetMapping("/dashboard")
    public String dashboard(Principal principal,HttpSession session) {
        String usernameSession = principal.getName();
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        
        System.out.println("Session created with matrix value: " + session.getAttribute("matrix"));
        return "dashboard";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
    @GetMapping("/finduser/{username}")
    @ResponseBody
    public UserActivityManagement findUserWithUserName(@PathVariable String username) {
        return userActivityManagementService.findUserWithUserName(username) ;
    }

//     @GetMapping("/profile")
//    public String userProfile(Principal principal) {
//     if (principal != null) {
//         String username = principal.getName(); 
//         System.out.println(username);// Get the username
//         // Fetch additional user data from your user repository or database
//         // ...
//     }
//     // Handle the case where no user is authenticated
//     return "redirect:/login";
// }

    
}
