package com.currency.currency_module.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.repository.AirportRepository;
import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.AirportList;


import java.util.List;


@Service
public class AirportService {
   @Autowired 
   AirportRepository airportRepository;


   public List<AirportList> getAllAirports() {
        return airportRepository.findAll();
    }

    public AirportList createAirport(AirportList createAirport) {
      return airportRepository.save(createAirport);
    }

    public AirportList findAirport(long id) {
      return airportRepository.findById(id).orElseThrow(()->new ResourceNotFound("User not found"));
    }

    public AirportList findAirportByOfficeCode(String officeCode) {
      return airportRepository.findByOfficeCode(officeCode).orElseThrow(()->new ResourceNotFound("User not found"));
    }



    public AirportList updateAirport(AirportList airportList){
      System.out.println("airport ======================="+airportList);
     AirportList existingAirport = airportRepository.findById(airportList.getId()).orElseThrow(()->new ResourceNotFound("User not found"));
     if (existingAirport != null) {

      System.out.println("airport ======================="+airportList+"=====================================================");
        existingAirport.setAirPortNames(airportList.getAirPortNames());
        existingAirport.setOfficeCode(airportList.getOfficeCode());
        existingAirport.setBankBranchName(airportList.getBankBranchName());
        existingAirport.setBankBranchCode(airportList.getBankBranchCode());
        existingAirport.setImage(airportList.getImage());
         AirportList updatedAirport = airportRepository.save(existingAirport);
         return updatedAirport;
     } else {
         return null;
     }


      
    }
}
