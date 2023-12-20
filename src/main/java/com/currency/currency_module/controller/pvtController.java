package com.currency.currency_module.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.services.AirportService;
import com.currency.currency_module.services.CurrencyServices;
import com.currency.currency_module.services.PvtinfoService;
import com.currency.currency_module.services.UserActivityManagementService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pvt")
public class pvtController {
    
    @Autowired
    PvtinfoService pvtinfoService;
    @Autowired
    PvtInfo pvtInfo;
     @Autowired
   UserActivityManagementService userActivityManagementService;
   @Autowired
   AirportInformation airportInformation;
    @Autowired
    AirportService airportService;
   
   
   @Autowired
   HttpSession httpSession;
    
    @GetMapping("/pvt-dashboard")
    public String pvrtDashboard(Model model ,Principal principal) {
        String organization =airportInformation.getOrganization(principal);
        System.out.println("aaaaaaaaaaaaaaaaaiiiiiiiii==================="+organization);
        model.addAttribute("allcount", pvtinfoService.privilageCount());
        return "pvt_dashboard";
    }

    @GetMapping("/pvt-entry")
    public String pvrtEntry(Model model,Principal principal) {
        String organization =airportInformation.getOrganization(principal);
         model.addAttribute("organization", organization);
        return "pvt_entry";
    }
 
    @PostMapping("/insert-pvt")
    public String insertPvt(PvtInfo pvtInfo ,Principal principal) {
       pvtinfoService.insertPvt(pvtInfo,principal);
        return "redirect:/pvt/pvt-entry";
    }

    @GetMapping("/all-pvt")
    public String index( Model model, Principal principal ) {
          String organization =airportInformation.getOrganization(principal);
        model.addAttribute("allPvt", pvtinfoService.getAllPvtInfo(organization,principal));

        return "get_all_pvt";
    }

    @GetMapping("/edit-pvt")
    public String editPvtForm(@RequestParam("id") Long id, Model model) {
        PvtInfo pvtInfo = pvtinfoService.getPvtInfoEdit(id);
        model.addAttribute("pvtInfo", pvtInfo);
        return "pvtEdit";
    }

    @PostMapping("/update-pvt")
    public String updatePvt(@ModelAttribute("pvtInfo") PvtInfo pvtInfo,Long id) {
        pvtinfoService.updatePvt(pvtInfo);
        return "redirect:/pvt/all-pvt";
    }

    @GetMapping("/delete-pvt")
    public String deletePvt(@RequestParam Long id) { 
        pvtinfoService.deletePvt(id);
        return "redirect:/pvt/all-pvt";  
    }

    @GetMapping("/view-details")
    public String viewDetails(Model model,@RequestParam Long id) { 
        model.addAttribute("pvtById", pvtinfoService.getPvtInfoEdit(id));
        return "pvt_view_details";  

    }


}
