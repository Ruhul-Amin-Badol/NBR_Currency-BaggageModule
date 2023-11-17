package com.currency.currency_module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.services.AirportService;
import com.currency.currency_module.services.UserActivityManagementService;

@Controller

@RequestMapping("/usermatrix")
public class userMatrixController {
      @Autowired
    AirportService airportService;
      @Autowired
    private UserActivityManagementService userActivityManagementService;

    @GetMapping("/dashboard")
        public String index(){

            return "userMatrixDashboard";
        }
    @GetMapping("/rollcreate")
            public String rollCreate(Model model){
                List<UserActivityManagement> userList = userActivityManagementService.getAllUsers();
                model.addAttribute("userList", userList);
                return "rollCreate";
            }
     @GetMapping("/adduser")
            public String addUser(Model model){
                model.addAttribute("allAirportList", airportService.getAllAirports());
                return "addUser";
            }

    @PostMapping("/userinsert")
            public String userInsert(UserActivityManagement userActivityManagement){
                


                userActivityManagementService.saveUserActivityManagement(userActivityManagement);
                return "rollCreate";
            }
}
