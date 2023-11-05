package com.currency.currency_module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.currency.currency_module.model.AirportList;
import com.currency.currency_module.services.AirportService;
import org.springframework.ui.Model;

@Controller
public class airportController {
    
    @Autowired
    AirportService airportService;


    @GetMapping("/add-airport")
    public String index(Model model){

        model.addAttribute("allAirportList", airportService.getAllAirports());
        return "add_airport";
    }


    @PostMapping("/create-port")
    public String createPort(AirportList airportList) {  
        airportService.createAirport(airportList); 
        return "redirect:/add-airport";
    }

    @GetMapping("/port-edit")
    public String editPort(@RequestParam Long id, Model model) {
        //System.out.println("airport id=========================================================="+id);

        model.addAttribute("airport",airportService.findAirport(id));
        return "edit_airport"; // The name of your edit view
    }

    @PostMapping("/update-port")
    public String updatePort(AirportList airportList) {  
        
        airportService.updateAirport(airportList);
        return "redirect:/add-airport";
    }





}
