package com.currency.currency_module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/usermatrix")
public class userMatrixController {
    
    @GetMapping("/dashboard")
        public String index(){

            return "userMatrixDashboard";
        }
    @GetMapping("/rollcreate")
            public String rollCreate(){

                return "rollCreate";
            }
     @GetMapping("/adduser")
            public String addUser(){

                return "addUser";
            }
}
