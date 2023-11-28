package com.currency.currency_module.controller;

import java.security.Principal;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


 

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.BaggageCurrencyAdd;
import com.currency.currency_module.model.CurrencyDeclaration;
import com.currency.currency_module.repository.CurrencyAddRepository;
import com.currency.currency_module.repository.CurrencyDeclarationRepository;
import com.currency.currency_module.services.CurrencyServices;
import com.currency.currency_module.services.AirportService;

@Controller

@RequestMapping("/currencyadmin")
public class currencyAdminController {
    @Autowired
    CurrencyServices currencyServices;
    @Autowired
    CurrencyAddRepository currencyAddRepository;
    @Autowired 
    CurrencyDeclarationRepository currencyDeclarationRepository;

    @Autowired 
    AirportService airportService;
    @Autowired
    AirportInformation airportInformation;

    @GetMapping("/currencyDashboard")
    public String index1() {
        return "currencyDashboard";
    }
     @GetMapping("/currencyeditadmin")
    public String currencyadminedit(Model model){
        //System.out.println();
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findAll();
         model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
      return "currencyEditAdmin";
}

    @GetMapping("/adminunapprovedcurrency")
    public String showunapprovedcurrency(Model model){
        //System.out.println();
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatus("unchecked");
        model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
    return "currencyAdminUnapprove";

    }
}


   
