package com.currency.currency_module.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.PaymentHistory;
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
    public String baggageById(Model model, @RequestParam("id") Integer id, @RequestParam("status") String page_route,Principal principal) {



        String username = principal.getName();  

        String userInfoSql= "SELECT * FROM user_activity_management WHERE username =?";
       Map<String, Object>userInfo= jdbcTemplate.queryForMap(userInfoSql, username);

       System.out.println("userInfo=================================="+userInfo);
        model.addAttribute("adminUserInfo", userInfo);


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

@ResponseBody
@PostMapping("/confirm-pay-by-admin")
public String confirmPaymentByAdmin(@RequestBody Map<String, Object> data) {
    System.out.println("data========================================"+data);
    try {
        Long baggage_id = Long.parseLong(data.get("baggage_id").toString());
        Double paid_amount = Double.parseDouble(data.get("paid_amount").toString());
        String payment_id = data.get("payment_id").toString();
        String payment_date = data.get("payment_date").toString();
        String calan_no = data.get("calan_no").toString();

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (paid_amount != null && paid_amount > 0) {
            jdbcTemplate.update(
                "INSERT INTO payment_history (baggage_id, paid_amount, payment_id, payment_date, transaction_id) VALUES (?, ?, ?, ?, ?)",
                baggage_id, paid_amount, payment_id, payment_date, calan_no
            );
            
            // Update other status or perform necessary actions

            return "Payment recorded successfully!";
        } else {
            return "Invalid paid amount!";
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "Error occurred while processing the payment!";
    }
}



// @GetMapping("/get-payment-history")
// public ResponseEntity<?> getPaymentHistory(@RequestParam Long baggage_id) {
//     try {
//        String sql = "SELECT * FROM payment_history WHERE baggage_id = ?";
//         List<Map<String, Object>> paymentHistoryList = jdbcTemplate.queryForList(sql, baggage_id);

//         return ResponseEntity.ok(paymentHistoryList);
//     } catch (Exception e) {
//         e.printStackTrace(); // Log the exception for debugging purposes
//         //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching payment history");
//         return null;
//     }
// }


@GetMapping("/get-payment-history")
public ResponseEntity<?> getPaymentHistory(@RequestParam Long baggage_id) {
    try {

        List<Map<String, Object>> paymentHistoryList = fetchPaymentHistoryFromDatabase(baggage_id);
        double totalPaidAmount = calculateTotalPaidAmount(paymentHistoryList);
        double totalTaxAmount  = calculateTotalTaxAmount(baggage_id);
        //String UpdateBaggagePaymentStatus=UpdateBaggagePaymentStatus(baggage_id);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("paymentHistoryList", paymentHistoryList);
        responseData.put("totalPaidAmount", totalPaidAmount);
        responseData.put("totalTaxAmount", totalTaxAmount);

        return ResponseEntity.ok(responseData);
    } catch (Exception e) {
        e.printStackTrace(); // Log the exception for debugging purposes
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching payment history");
        return null;
    }
}


    private List<Map<String, Object>> fetchPaymentHistoryFromDatabase(Long baggage_id) {

                String sql = "SELECT * FROM payment_history WHERE baggage_id = ?";
                List<Map<String, Object>> paymentHistoryList = jdbcTemplate.queryForList(sql, baggage_id);


                return paymentHistoryList;
    }


    private double calculateTotalTaxAmount(Long baggage_id) {
        String sql = "SELECT SUM(tax_amount) AS totalTaxAmount FROM baggage_product_add WHERE baggage_id = ?";
        
        try {
            Double totalTaxAmount = jdbcTemplate.queryForObject(sql, Double.class, baggage_id);
            
            return totalTaxAmount != null ? totalTaxAmount : 0.0; // If result is null, return 0
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0; // Return 0 in case of an error
        }
    }
// Calculate total paid amount
private double calculateTotalPaidAmount(List<Map<String, Object>> paymentHistoryList) {
    double totalPaidAmount = 0.0;
    for (Map<String, Object> payment : paymentHistoryList) {
        double paidAmount = (double) payment.get("paid_amount");
        totalPaidAmount += paidAmount;
    }
    return totalPaidAmount;
}
    // @PostMapping("/update-refund-amount")
    // public String updateRefundAmount(@RequestParam Long id,Double refundAmount) {

    //     System.out.println("refundAmount=================================="+refundAmount);
    //    String is_refund = "yes";
    //    String sqlBaggage = "UPDATE baggage SET paid_amount=?,is_refund=? WHERE id=?";
    //    jdbcTemplate.update(sqlBaggage,refundAmount,is_refund,id);
    //   // return "Refund Successfully";
    // }
    @ResponseBody
    @PostMapping("/update-refund-amount")
    public ResponseEntity<Map<String, Object>> updateRefundAmount(@RequestBody Map<String, Object> requestData) {
        Long baggageId = Long.parseLong(requestData.get("baggage_id").toString());
        Double refundAmount = Double.parseDouble(requestData.get("payableAmount").toString());
        String paymentId = requestData.get("payment_id").toString();
        LocalDateTime paymentDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDatePayment = paymentDate.format(formatter);
    
        System.out.println("=============refundAmount======================================"+refundAmount);
    
        String is_refund = "yes";
        double ref = 2.33;
    
        // Assuming some condition where refund is successful
        // For simplicity, I'm using a boolean flag 'success'
        boolean success = true;
    
        jdbcTemplate.update(
            "INSERT INTO payment_history (baggage_id, paid_amount, payment_id, payment_date,is_refund) VALUES (?, ?, ?, ?,?)",
            baggageId, refundAmount, paymentId, formattedDatePayment,is_refund);
    
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Refund Successfully" : "Refund Failed");
    
        return ResponseEntity.ok(response);
    }


//ipdate payment status
    // private  String UpdateBaggagePaymentStatus(Long baggage_id) {
        
    //     String status = "Unpaid";
    //     String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
    //     jdbcTemplate.update(sqlBaggage,status,baggage_id);
    //     return null;
    
    // }




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
            String sql3 = "SELECT * FROM baggage ORDER by id DESC";
            List<Map<String, Object>> baggageappshow = jdbcTemplate.queryForList(sql3);
        
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