package com.currency.currency_module.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.currency.currency_module.model.PvtInfo;
import com.currency.currency_module.services.CurrencyServices;
import com.currency.currency_module.services.PvtinfoService;

@Controller
@RequestMapping("/pvt")
public class pvtController {
    
    @Autowired
    PvtinfoService pvtinfoService;
    @Autowired
    PvtInfo pvtInfo;
    
    @GetMapping("/pvt-dashboard")
    public String pvrtDashboard(Model model) {
        return "pvt_dashboard";
    }

    @GetMapping("/pvt-entry")
    public String pvrtEntry(Model model) {
        return "pvt_entry";
    }

    @PostMapping("/insert-pvt")
    public String insertPvt(PvtInfo pvtInfo) {
       pvtinfoService.insertPvt(pvtInfo);
        return "redirect:/pvt/pvt-entry";
    }

    @GetMapping("/all-pvt")
    public String index( Model model) {
        model.addAttribute("allPvt", pvtinfoService.getAllPvtInfo());
        return "get_all_pvt";
    }




}
