package com.currency.currency_module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.currency.currency_module.model.AirportList;
import com.currency.currency_module.services.AirportService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;


import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


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
    private String createPort(@ModelAttribute @Valid AirportList airportList, BindingResult result, @RequestParam MultipartFile image) {
      
     // System.out.println("image.getOriginalFilename()========================="+image.getOriginalFilename());
        airportList.setImage(image.getOriginalFilename());
        AirportList uplodeImage = airportService.createAirport(airportList);

		if (uplodeImage != null) {
			try {

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + image.getOriginalFilename());
				System.out.println("path=========================================="+path);
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return "redirect:/add-airport";
    }
    


    
    
    
    

    @GetMapping("/port-edit")
    public String editPort(@RequestParam Long id, Model model) {
        //System.out.println("airport id=========================================================="+id);

        model.addAttribute("airport",airportService.findAirport(id));
        return "edit_airport"; // The name of your edit view
    }




    // @PostMapping("/update-port")
    
    // public String updatePort(AirportList airportList) {  
        
    //     airportService.updateAirport(airportList);
    //     return "redirect:/add-airport";
    // }

    @PostMapping("/update-port")
    private String updatePort(@ModelAttribute @Valid AirportList airportList, BindingResult result, @RequestParam MultipartFile image) {

        airportList.setImage(image.getOriginalFilename());
        AirportList uplodeImage = airportService.updateAirport(airportList);


		if (uplodeImage != null) {
			try {

                String saveDirectory = "./airports"; // specify the directory path
                File saveFile = new File(saveDirectory);

                if (!saveFile.exists()) {
                    saveFile.mkdirs(); // create the directory if it doesn't exist
                }

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + image.getOriginalFilename());
				
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String fileLocation = path.toString();

                System.out.println("=============================>=============");
                System.out.println(fileLocation);
                System.out.println("=============================>==============");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return "redirect:/add-airport";
    }




}