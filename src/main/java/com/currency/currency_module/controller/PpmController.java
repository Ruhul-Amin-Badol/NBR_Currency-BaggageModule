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


    @GetMapping("/pvt-dashboard")
    public String ppmDashboard(Model model) {
        return "ppm_dashboard";
    }

    @GetMapping("/ppm-entry")
    public String ppmtEntry(Model model) {
        return "ppm_entry";
    }


    @PostMapping("/insert-ppm")
    public String insertPpm(@ModelAttribute @Valid PpmInfo ppmInfo, BindingResult result, @RequestParam MultipartFile uploadFile) {
     //  ppmInfoService.insertPpm(ppmInfo);
        ppmInfo.setUploadFile(uploadFile.getOriginalFilename());
        PpmInfo uplodeImage = ppmInfoService.insertPpm(ppmInfo);
       // AirportList uplodeImage = airportService.createAirport(airportList);

		if (uplodeImage != null) {
			try {

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + uploadFile.getOriginalFilename());
				System.out.println("path=========================================="+path);
				Files.copy(uploadFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        return "redirect:/ppm/ppm-entry";
    }

    @GetMapping("/all-ppm")
    public String index( Model model) {

        model.addAttribute("allPpm", ppmInfoService.getAllPpmInfo());
        return "get_all_ppm";
    }


}
