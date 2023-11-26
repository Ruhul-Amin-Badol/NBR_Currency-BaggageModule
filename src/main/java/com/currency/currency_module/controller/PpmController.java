package com.currency.currency_module.controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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


    @GetMapping("/ppm-dashboard")
    public String ppmDashboard(Model model) {
        Integer passbook = 1;
        Integer carTransfer = 2;
        Integer carSale = 3;
        model.addAttribute("passbookCount", ppmInfoService.ppmCount(passbook));
        model.addAttribute("carTransferCount", ppmInfoService.ppmCount(carTransfer));
        model.addAttribute("carSaleCount", ppmInfoService.ppmCount(carSale));
        return "ppm_dashboard";
    }

    // @GetMapping("/ppm-dashboard")
    // public String pvrtDashboard(Model model) {
    //     model.addAttribute("allcount", ppminfoService.privilageCount());
    //     return "ppm_dashboard";
    // }





    @GetMapping("/ppm-entry")
    public String ppmtEntry(Model model) {
        return "ppm_entry";
    }


    @PostMapping("/insert-ppm")
    public String insertPpm(@ModelAttribute @Valid PpmInfo ppmInfo, BindingResult result, @RequestParam MultipartFile uploadFile) {
     //  ppmInfoService.insertPpm(ppmInfo);
        ppmInfo.setUploadFile(uploadFile.getOriginalFilename());
        PpmInfo uplodeImage = ppmInfoService.insertPpm(ppmInfo,uploadFile);
       // AirportList uplodeImage = airportService.createAirport(airportList);

		// if (uplodeImage != null) {
		// 	try {

		// 		File saveFile = new ClassPathResource("static/img").getFile();

		// 		Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + uploadFile.getOriginalFilename());
		// 		System.out.println("path=========================================="+path);
		// 		Files.copy(uploadFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		// 	} catch (Exception e) {
		// 		e.printStackTrace();
		// 	}
		// }

        return "redirect:/ppm/ppm-entry";
    }

    @GetMapping("/all-ppm")
    public String index( Model model) {

        model.addAttribute("allPpm", ppmInfoService.getAllPpmInfo());
        return "get_all_ppm";
    }


    @GetMapping("custom-passbook-application")
    public String customPassbookApplication( Model model) {
        Integer passbook =1;
        model.addAttribute("allPpm", ppmInfoService.passbookReport(passbook));
        return "custom_passbook_application_report";
    }

    @GetMapping("/car-transfer-application")
    public String carTransferApplication( Model model) {
          Integer carTransfer =2;

        model.addAttribute("allPpm", ppmInfoService.carTransferRepost(carTransfer));
        return "car_transfer_application_report";
    }


    @GetMapping("/car-sale-application")
    public String carSaleApplication( Model model) {
          Integer carSale =3;
        model.addAttribute("allPpm", ppmInfoService.carSaleRepost(carSale));
        return "car_sale_application_report";
    }


}
