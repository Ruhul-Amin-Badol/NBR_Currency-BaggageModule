package com.currency.currency_module.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
            public String index(Model model){
                 model.addAttribute("usercount", userActivityManagementService.countalluser());
                 model.addAttribute("activeusercount", userActivityManagementService.countallactiveuser());
                 model.addAttribute("inactiveusercount", userActivityManagementService.countallinactiveuser());
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
                System.out.println(user);
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

             @GetMapping("/rolledit/{userId}")
            public String rolleditUser(@PathVariable Long userId, Model model) {
                UserActivityManagement user = userActivityManagementService.getUserById(userId);
                model.addAttribute("user", user);
                List<AirportList> allAirportList = airportService.getAllAirports();
                model.addAttribute("allAirportList", allAirportList);
                return "rollManageEdit"; 
    }
    
            @GetMapping("/rolldeleteuser/{userId}")
            public String rolldeleteUser(@PathVariable Long userId) {
                userActivityManagementService.deleteUserById(userId);
                return "redirect:/usermatrix/rollManage";
            }

            @PostMapping("/rollupdateuser/{userId}")
            public String rollupdateUser(@PathVariable Long userId, @ModelAttribute UserActivityManagement user) {
                // Set the user ID from the path variable
                user.setUserId(userId);

                userActivityManagementService.saverollUserActivityManagement(user);
                return "redirect:/usermatrix/rollmanage";
    }

            //Roll manage dashboard

            @GetMapping("/allusershow")
            public String allusershow(Model model){
                List<UserActivityManagement> userList = userActivityManagementService.getAllUsers();
                model.addAttribute("userList", userList);
                return "allUserShow";
                }

            @GetMapping("/allactiveuser")
            public String allactiveusershow(Model model){
                List<UserActivityManagement> activeuserList = userActivityManagementService.findAllActiveuser();
                model.addAttribute("activeuser", activeuserList);
                return "allActiveUserShow";
                }

            @GetMapping("/allinactiveuser")
            public String findAllinActiveuser(Model model){
                List<UserActivityManagement> activeuserList = userActivityManagementService.findAllinActiveuser();
                model.addAttribute("inactiveuser", activeuserList);
                return "allInActiveUserShow";
                }

                

}
