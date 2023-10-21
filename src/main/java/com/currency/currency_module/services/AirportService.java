package com.currency.currency_module.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.currency_module.repository.AirportRepository;
import com.currency.currency_module.model.AirportList;
import java.util.List;


@Service
public class AirportService {
   @Autowired 
   AirportRepository airportRepository;


   public List<AirportList> getAllAirports() {
    return airportRepository.findAll();
}
}
