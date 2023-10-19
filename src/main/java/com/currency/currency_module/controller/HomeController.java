package com.currency.currency_module.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.services.UserActivityManagementService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
   UserActivityManagementService userActivityManagementService;

    @GetMapping("/signin") 
    public String login() {
        return "login";
    }
      @GetMapping("/index1") 
    public String index1() {
        return "index1";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
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
