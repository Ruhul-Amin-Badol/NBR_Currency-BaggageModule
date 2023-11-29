package com.currency.currency_module.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

import com.currency.currency_module.model.PaymentHistory;
import com.currency.currency_module.services.EmailService;
import com.currency.currency_module.services.PaymentHistoryService;
import com.currency.currency_module.services.PdfGenerationService;

@Controller
@RequestMapping("/api")
public class BankAutoRequestController {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PaymentHistoryService paymentHistoryService;
   @Autowired
   private JavaMailSender mailSender;
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/DataUpdate")
    @ResponseBody
    public  Map<String, Object> BankDataUpdate(@RequestBody Map<String, Object> requestBody){
        Map<String, Object> credential = (Map<String, Object>) requestBody.get("credential");
        Map<String, Object> data = (Map<String, Object>) requestBody.get("data");

        String username = (String) credential.get("username");
        String password = (String) credential.get("password");

        String sessionToken = (String) data.get("session_token");
        String transactionId = (String) data.get("transactionid");
        String invoiceNo = (String) data.get("invoiceno");
        String invoiceDate = (String) data.get("invoicedate");

if(username.equalsIgnoreCase("nbr205") && password.equalsIgnoreCase("nbr206@!#$%")){
           
             

            System.out.println("===============/insert-payment-history-record============");
            String baggageSql= "SELECT * FROM baggage WHERE payment_id =?";
            
            Map<String, Object>requestParameters= jdbcTemplate.queryForMap(baggageSql, invoiceNo);
            String emailId = (String) requestParameters.get("email");
            long id = (Long) requestParameters.get("id");
            

            // String paymentStatus = "Processing";
            // if(!status.equals("success")){
            //     paymentStatus=status;
            // }
            // String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
            // jdbcTemplate.update(sqlBaggage,paymentStatus,id);


            String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
            List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
            Double totalTaxAmount = 0.0;
            for (Map<String, Object> row : productshow) {
                String taxAmount = (String) row.get("tax_amount");

                if (taxAmount != null) {
                    totalTaxAmount += Double.parseDouble(taxAmount);
                }
               
            }
            String payment_id= (String)requestParameters.get("payment_id");
            Integer baggage_id= (Integer)requestParameters.get("id");
            String officeCode= (String)requestParameters.get("office_code");
            LocalDateTime currentDateTime = LocalDateTime.now();



            PaymentHistory paymentHistory=new PaymentHistory();
            
            paymentHistory.setSessionToken(sessionToken);
            paymentHistory.setStatus("");
            paymentHistory.setMsg("");
            paymentHistory.setTransactionId(transactionId);
            paymentHistory.setTransactionDate(invoiceDate);
            paymentHistory.setInvoiceNo(invoiceNo);
            paymentHistory.setInvoiceDate(invoiceDate);
            paymentHistory.setBrCode("");
            paymentHistory.setApplicantName("");
            paymentHistory.setApplicantContactNo("");
            paymentHistory.setTotalAmount("");
            paymentHistory.setPaymentStatus("");
            paymentHistory.setPayMode("");
            paymentHistory.setPayAmount("");
            paymentHistory.setVat("");
            paymentHistory.setCommission("");
            paymentHistory.setScrollNo("");
            paymentHistory.setBaggageId(id);
            paymentHistory.setPaymentId(invoiceNo);
            paymentHistory.setOfficeCode(officeCode);
           
            paymentHistoryService.insertPaymehistory(paymentHistory);


            String link="/baggagestart/confrimPage?id="+id;
           
            Context context = new Context();
            context.setVariable("passengerName", requestParameters.get("passenger_name"));
            context.setVariable("totalTaxAmount", totalTaxAmount);
            context.setVariable("link", link);  
            try {
                // Double totalPaidAmount = 0.0;
                 String gmail = (String) requestParameters.get("email");
             
                 String baggage_Sql = "SELECT * FROM baggage WHERE id =?";
                 Map<String, Object> baggageQuery = jdbcTemplate.queryForMap(baggage_Sql, id);
             
 
                 String baggageProductAddJoin = "SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
                 List<Map<String, Object>> allProductQuery = jdbcTemplate.queryForList(baggageProductAddJoin, id);
             
                 List<String> includedFields = Arrays.asList("passenger_name","entry_point","flight_no","passport_number");
               //  List<String> includedFields = Arrays.asList("id","item_id","payment_id"); // Replace with your actual field names
                 List<Object> rowData = new ArrayList<>(allProductQuery);
                 rowData.add(baggageQuery);
             
                 byte[] pdfData = pdfGenerationService.generatePdf(allProductQuery,rowData, includedFields,totalTaxAmount,id);
             
                 HttpHeaders headers = new HttpHeaders();
                 headers.setContentType(MediaType.APPLICATION_PDF);
                 headers.setContentDispositionFormData("inline", "NBR_baggage_declaration.pdf");
             
                 emailService.sendEmailWithAttachment(gmail, "NBR Baggage Declaration", "Body", pdfData, "nbr_baggage_application.pdf");
             } catch (IOException e) {
                 e.printStackTrace();
             }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status",200);
        responseData.put("msg","Success");
        responseData.put("transactionid",transactionId);
        return responseData;

 

             }else{
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status",500);
        responseData.put("msg","");
        responseData.put("transactionid","");
        return responseData;
             }
      
      
    }
}
