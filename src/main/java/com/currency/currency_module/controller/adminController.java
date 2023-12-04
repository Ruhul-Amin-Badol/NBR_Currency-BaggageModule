package com.currency.currency_module.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currency.currency_module.AirportInformation;

import com.currency.currency_module.services.AirportService;

@Component
@Controller
@RequestMapping("/baggageshow")
public class adminController {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    AirportInformation airportInformation;
    @Autowired
    AirportService airportService;
    
    @GetMapping("/baggagetotal")
    public String baggagetotal( Model model ,Principal principal) {
        String airportname=airportInformation.getAirport(principal);
        if(airportname.equalsIgnoreCase("all")){
        String sql1 = "SELECT * FROM baggage ORDER BY id DESC";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("baggageshow", baggageshow);
        }
        else{

        String sql1 = "SELECT * FROM baggage WHERE office_code = ? ORDER BY id DESC";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1,airportname);
        model.addAttribute("baggageshow", baggageshow);
        }

    
        return "baggageTotalApplication";
       
    }


    @GetMapping("/unapprovedbaggagetotal")
    public String unapprovedbaggagetotal( Model model,Principal principal) {
        String officeCode=airportInformation.getAirport(principal);
      
          if(officeCode.equalsIgnoreCase("all")){
            String sql = "SELECT * FROM baggage WHERE status = 'unapproved' ORDER BY id DESC";
            List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql);
            model.addAttribute("baggageshow", baggageshow);
          }
          else{
             String sql = "SELECT * FROM baggage WHERE status = 'unapproved' AND office_code=? ORDER BY id DESC";
             List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql,officeCode);
            model.addAttribute("baggageshow", baggageshow);
          }
       
        return "unapproved_baggage_list";
    }

    @GetMapping("/baggagetotalid")
    public String baggageById(Model model, @RequestParam("id") Integer id, @RequestParam("status") String page_route) {

        String sql = "SELECT * FROM baggage WHERE id = ?";
        Map<String, Object> baggageView = jdbcTemplate.queryForMap(sql,id);
        model.addAttribute("baggageView", baggageView);
        System.out.println(baggageView);    

        String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
        model.addAttribute("showProduct", productshow);
        model.addAttribute("page_route", page_route);
        
        return "baggageApprovalform"; // Replace with the actual template name
    }


    @GetMapping("/payment-review")
    public String paymentReview(@RequestParam Long id) {
       String status = "Review";
       String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
       jdbcTemplate.update(sqlBaggage,status,id);

       
       return "redirect:/baggageshow/baggagetotalid?id="+id;
    }
    
    //Application Edit Controller 

        @GetMapping("/baggageApplicationEdit")
        @ResponseBody
        public List<Map<String, Object>> baggageApplicationEdit(){
            String sql1 = "SELECT * FROM baggage";
            List<Map<String, Object>> baggageappshow = jdbcTemplate.queryForList(sql1);
        
            return baggageappshow;
        }

        @GetMapping("/baggageApplicationShow")
     
        public String baggageApplicationShow(){ 
            return "baggageApplicationEdit";
        }


        //For Baggage Report
        
        @GetMapping("/baggageReport")
     
        public String baggageReport(Model model ){ 
            model.addAttribute("allAirportList", airportService.getAllAirports());

            String sql1 = "SELECT item_name FROM baggage_item_info order by id DESC";
            List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1);
            model.addAttribute("productshow", productshow);

            String sql2 ="SELECT * FROM baggage JOIN baggage_product_add ON baggage.id = baggage_product_add.baggage_id JOIN baggage_item_info ON baggage_item_info. id = baggage_product_add.item_id order by baggage_product_add.id DESC";
            List<Map<String, Object>> product = jdbcTemplate.queryForList(sql2);
            model.addAttribute("product", product);
            
            return "baggageReport";
        }


}