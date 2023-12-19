package com.currency.currency_module.controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.AirportList;
import com.currency.currency_module.model.PpmInfo;

import com.currency.currency_module.services.PpmInfoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ppm")
public class PpmController {
    @Autowired
    PpmInfoService ppmInfoService;
    @Autowired
    PpmInfo ppmInfo;

   @Autowired
   AirportInformation airportInformation;


    @GetMapping("/ppm-dashboard")
    public String ppmDashboard(Model model,Principal principal) {
        Integer passbook = 1;
        Integer carTransfer = 2;
        Integer carSale = 3;

        String username =airportInformation.getUsername(principal);


        model.addAttribute("passbookCount", ppmInfoService.ppmCount(passbook,username));
        model.addAttribute("carTransferCount", ppmInfoService.ppmCount(carTransfer,username));
        model.addAttribute("carSaleCount", ppmInfoService.ppmCount(carSale,username));


        return "ppm_dashboard";
    }

    @GetMapping("/ppm-entry")
    public String ppmtEntry(Model model,Principal principal) {

        String organization =airportInformation.getOrganization(principal);
        model.addAttribute("organization", organization);


        return "ppm_entry";
    }

    @PostMapping("/insert-ppm")
    public String insertPpm(@ModelAttribute @Valid PpmInfo ppmInfo, BindingResult result, @RequestParam MultipartFile uploadFile ,Principal principal) {
     //  ppmInfoService.insertPpm(ppmInfo);
        ppmInfo.setUploadFile(uploadFile.getOriginalFilename());
        PpmInfo uplodeImage = ppmInfoService.insertPpm(ppmInfo,uploadFile,principal);

        return "redirect:/ppm/ppm-entry";
    }

    @GetMapping("/all-ppm")
    public String index( Model model, Principal principal) {
        String organization =airportInformation.getOrganization(principal);
        System.out.println("organization==============================================="+organization);
        model.addAttribute("allPpm", ppmInfoService.getAllPpmInfo(organization,principal));
        return "get_all_ppm";
    }


    @GetMapping("custom-passbook-application")
    public String customPassbookApplication( Model model,Principal principal) {
        String username =airportInformation.getUsername(principal);
        Integer passbook =1;
        model.addAttribute("allPpm", ppmInfoService.passbookReport(passbook,username));
        return "custom_passbook_application_report";
    }

    @GetMapping("/car-transfer-application")
    public String carTransferApplication( Model model,Principal principal) {
          Integer carTransfer =2;
        String username =airportInformation.getUsername(principal);
        model.addAttribute("allPpm", ppmInfoService.carTransferRepost(carTransfer,username));
        return "car_transfer_application_report";
    }


    @GetMapping("/car-sale-application")
    public String carSaleApplication( Model model,Principal principal) {
          Integer carSale =3;
           String username =airportInformation.getUsername(principal);
        model.addAttribute("allPpm", ppmInfoService.carSaleRepost(carSale,username));
        return "car_sale_application_report";
    }


}
