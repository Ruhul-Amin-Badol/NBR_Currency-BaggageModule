package com.currency.currency_module.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.currency.currency_module.model.OrganizationList;
import com.currency.currency_module.services.OrganizationService;

import jakarta.validation.Valid;


@Controller

@RequestMapping("/organization")
public class organizationController {
     @Autowired
     OrganizationService organizationService;


    @GetMapping("/add-organization")
    public String index(Model model){

        model.addAttribute("allOrganization", organizationService.getAllOrganization());
        return "add_organization";
    }

    @PostMapping("/create-organization")
    private String createPort(@ModelAttribute @Valid OrganizationList organizationList) {
      
     // System.out.println("image.getOriginalFilename()========================="+image.getOriginalFilename());
      
       organizationService.createOrganization(organizationList);
        return "redirect:/organization/add-organization";
    }
    

    // @GetMapping("/port-edit")
    // public String editPort(@RequestParam Long id, Model model) {
    //     //System.out.println("airport id=========================================================="+id);

    //     model.addAttribute("airport",airportService.findAirport(id));
    //     return "edit_airport"; // The name of your edit view
    // }



    // @PostMapping("/update-port")
    // private String updatePort(@ModelAttribute @Valid AirportList airportList, BindingResult result, @RequestParam MultipartFile image,Principal principal) {

    //     airportList.setImage(image.getOriginalFilename());
    //     AirportList uplodeImage = airportService.updateAirport(airportList,image,principal);

    //     return "redirect:/add-airport";
    // }

   
}
