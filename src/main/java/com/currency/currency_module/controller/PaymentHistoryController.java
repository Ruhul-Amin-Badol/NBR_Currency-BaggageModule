package com.currency.currency_module.controller;

import java.util.List;
import java.util.Map;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.currency.currency_module.AirportInformation;

@Controller
public class PaymentHistoryController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AirportInformation airportInformation;

    @GetMapping("/payment-record")
    public String paymentRecord(Model model) {
        String sql1 = "SELECT * FROM baggage";
        List<Map<String, Object>> baggageappshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("baggageappshow", baggageappshow); // Add the data to the model
        return "payment_record"; // Return the name of your Thymeleaf template
    }


    @GetMapping("/payment-history")
    public String paymentHistory(Model model, @RequestParam("id") Integer id) {
        String sql = "SELECT * FROM baggage WHERE id = ?";
        Map<String, Object> baggageView = jdbcTemplate.queryForMap(sql,id);
        model.addAttribute("baggageView", baggageView);
        System.out.println(baggageView);    

        String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
        model.addAttribute("reportShow", productshow);   
       return "payment_history"; // Replace with the actual template name
    }


    @GetMapping("/payment-total-history")
    public String paymentToalHistory(Model model, Principal principal){
        String airportOfficeCode=airportInformation.getAirport(principal);

        if(airportOfficeCode.equalsIgnoreCase("all")){
            String sql = "SELECT * FROM payment_history";
          List<Map<String, Object>> tranjectionHistory =  jdbcTemplate.queryForList(sql);
          model.addAttribute("tranjectionHistories", tranjectionHistory);
        }else{
          String sql = "SELECT * FROM payment_history where office_code = ?";
          List<Map<String, Object>> tranjectionHistory =  jdbcTemplate.queryForList(sql, airportOfficeCode);
          model.addAttribute("tranjectionHistories", tranjectionHistory);
        }

         // Add the data to the model
        return "payment_history_total"; // Return the name of your Thymeleaf template
    }
    
}
