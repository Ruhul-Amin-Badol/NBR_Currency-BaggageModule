package com.currency.currency_module.controller;

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
    
}
