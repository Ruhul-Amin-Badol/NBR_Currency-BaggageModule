package com.currency.currency_module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.model.AirportList;
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

        //Roll create start

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
                return "redirect:/usermatrix/rollcreate";
            }

    @GetMapping("/edituser/{userId}")
            public String editUser(@PathVariable Long userId, Model model) {
                UserActivityManagement user = userActivityManagementService.getUserById(userId);
                model.addAttribute("user", user);
                List<AirportList> allAirportList = airportService.getAllAirports();
                model.addAttribute("allAirportList", allAirportList);
                return "editUser"; 
    }
            @PostMapping("/updateuser/{userId}")
            public String updateUser(@PathVariable Long userId, @ModelAttribute UserActivityManagement user) {
                // Set the user ID from the path variable
                user.setUserId(userId);

                userActivityManagementService.saveUserActivityManagement(user);
                return "redirect:/usermatrix/rollcreate";
    }

            @GetMapping("/deleteuser/{userId}")
            public String deleteUser(@PathVariable Long userId) {
                userActivityManagementService.deleteUserById(userId);
                return "redirect:/usermatrix/rollcreate";
            }


            //Roll manage start

              @GetMapping("/rollmanage")
            public String rollManage(Model model) {
                 List<UserActivityManagement> userList = userActivityManagementService.getAllUsers();
                 model.addAttribute("userList", userList);
                return "rollManage";
            }
}
