package com.currency.currency_module.controller;

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
        model.addAttribute("allcount", pvtinfoService.privilageCount());
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




}
