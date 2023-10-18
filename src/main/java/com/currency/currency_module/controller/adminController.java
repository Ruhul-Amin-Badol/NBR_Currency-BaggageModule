package com.currency.currency_module.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/baggageshow")
public class adminController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    
    @GetMapping("/baggagetotal")
    public String baggagetotal( Model model) {
        String sql1 = "SELECT * FROM baggage";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("baggageshow", baggageshow);
       
        
        return "baggageTotalApplication";
       

    }


    @GetMapping("/unapprovedbaggagetotal")
    public String unapprovedbaggagetotal( Model model) {
        String sql = "SELECT * FROM baggage WHERE status = 'unapproved'";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql);
        model.addAttribute("baggageshow", baggageshow);
       
        
        return "unapproved_baggage_list";
       

    }

    @GetMapping("/baggagetotalid")
    public String baggageById(Model model, @RequestParam("id") Integer id) {

        String sql = "SELECT * FROM baggage WHERE id = ?";
        Map<String, Object> baggageView = jdbcTemplate.queryForMap(sql,id);
        model.addAttribute("baggageView", baggageView);
        System.out.println(baggageView);    

        String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);

        model.addAttribute("showProduct", productshow);
        
    
        return "baggageApprovalform"; // Replace with the actual template name
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

        


}