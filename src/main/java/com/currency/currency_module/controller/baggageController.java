package com.currency.currency_module.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.currency.currency_module.AirportInformation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import com.currency.currency_module.services.EmailService;

import com.currency.currency_module.services.PdfGenerationService;

import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;


@Controller
@RequestMapping("/baggagestart")
public class baggageController {
    @Autowired
    JdbcTemplate jdbcTemplate;
     @Autowired
   AirportInformation airportInformation;
   @Autowired
   private JavaMailSender mailSender;
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private EmailService emailService;

   @Autowired
private TemplateEngine templateEngine;

    @GetMapping("/baggageRule")
    public String showBaggageRule() {
        return "baggageRule";
    }
    //------> From show and hide mode<------//
    @GetMapping("/show")
    public String baggageFrom(@RequestParam(required = false, defaultValue = "") String generatedId,@RequestParam(required = false, defaultValue = "") String officeCode, Model model) {
        String sql1 = "SELECT item_name FROM baggage_item_info";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("productshow", productshow);



        String allAirport_sql = "SELECT * FROM airport_list";
        List<Map<String, Object>> allAirportList = jdbcTemplate.queryForList(allAirport_sql);


        String airport_sql = "SELECT * FROM airport_list WHERE office_code = ?";
       List<Map<String, Object>> airportByOfficeCode = jdbcTemplate.queryForList(airport_sql, officeCode);
       System.out.println("airportByOfficeCode================================"+airportByOfficeCode);
      model.addAttribute("airportByOfficeCodes", airportByOfficeCode);



        model.addAttribute("allAirportList", allAirportList);
        if (!generatedId.isEmpty()) {
            String sql = "SELECT * FROM baggage WHERE id = ?";
            Map<String, Object> baggageDetails = jdbcTemplate.queryForMap(sql, generatedId);
            


            model.addAttribute("InsertMode", false);
            model.addAttribute("editMode", true);
            model.addAttribute("ID", generatedId);

            model.addAttribute("baggageDetails", baggageDetails);
             
        } else {
            model.addAttribute("editMode", false);
            model.addAttribute("InsertMode", true);
        }

        return "baggage";
    }


    @GetMapping("/edit-baggage")
    public String adminBaggageUpdate(@RequestParam(required = false, defaultValue = "") String generatedId, Model model){

        String sql1 = "SELECT item_name FROM baggage_item_info";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("productshow", productshow);

        String allAirport_sql = "SELECT * FROM airport_list";
        List<Map<String, Object>> allAirportList = jdbcTemplate.queryForList(allAirport_sql);

        String paymentSql = "SELECT * FROM payment_history WHERE baggage_id = ?";
        List<Map<String, Object>> paymentHistoryInfo = jdbcTemplate.queryForList(paymentSql, generatedId);
        System.out.println("paymentHistoryInfo======================================="+paymentHistoryInfo+generatedId);

        if (!paymentHistoryInfo.isEmpty()) {
           // Map<String, Object> firstRow = paymentHistoryInfo.get(0);
            //Double paidAmount = (Double) firstRow.get("paid_amount");

            Double totalPaidAmount = 0.0;
            for (Map<String,Object> paymentHistory : paymentHistoryInfo) {
               Double paidAmount = (Double) paymentHistory.get("paid_amount");
               System.out.println("paidAmount======================================="+paidAmount);
            totalPaidAmount= totalPaidAmount+paidAmount;


            }
            model.addAttribute("totalPaidAmount", totalPaidAmount);
            System.out.println("paymentHistoryInfo======================================="+totalPaidAmount);


            // Now, you can use the paidAmount value
        } else {
            Double totalPaidAmount=0.0;
            model.addAttribute("totalPaidAmount", totalPaidAmount);

            // Handle the case where no rows were found for the given id
        }
       




        
        model.addAttribute("allAirportList", allAirportList);
        if (!generatedId.isEmpty()) {
            String sql = "SELECT * FROM baggage WHERE id = ?";
            Map<String, Object> baggageDetails = jdbcTemplate.queryForMap(sql, generatedId);
            


            model.addAttribute("InsertMode", false);
            model.addAttribute("editMode", true);
            model.addAttribute("ID", generatedId);

            model.addAttribute("baggageDetails", baggageDetails);
             
        } else {
            model.addAttribute("editMode", false);
            model.addAttribute("InsertMode", true);
        }

        return "baggage_admin";
    }
     //rejected baggage list by nitol
    @GetMapping("/rejected-baggage-list")
    public String rejectBaggage(Model model,Principal principal) {
        String airportname=airportInformation.getAirport(principal);

        if(airportname.equalsIgnoreCase("all")){
             String sql1 = "SELECT * FROM baggage where status=?";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1,"approved");
        model.addAttribute("baggageshow", baggageshow);
        }
        else{
        String sql1 = "SELECT * FROM baggage where status=? AND entry_point=?";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1,"approved",airportname);
        model.addAttribute("baggageshow", baggageshow);
        }

        return "baggage_reject";
    }

    //approve baggage list by nitol

    @GetMapping("/approve-baggage-list")
    public String approveBaggage( Model model,Principal principal) {
        String airportname=airportInformation.getAirport(principal);

        // List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,"approved");
        // model.addAttribute("baggageshow", productshow);
        if(airportname.equalsIgnoreCase("all")){
             String sql1 = "SELECT * FROM baggage where status=?";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1,"approved");
        model.addAttribute("baggageshow", baggageshow);
        }
        else{
        String sql1 = "SELECT * FROM baggage where status=? AND entry_point=?";
        List<Map<String, Object>> baggageshow = jdbcTemplate.queryForList(sql1,"approved",airportname);
        model.addAttribute("baggageshow", baggageshow);
        }

        return "baggage_approve";
    }


    // -----> baggage insert <-----//
    @PostMapping("/baggageInsert")
    public String insertBaggage(
            @RequestParam String entryPoint,
            @RequestParam String passengerName,
            @RequestParam String passportNumber,
            @RequestParam String passportValidityDate,
            @RequestParam String nationality,
            @RequestParam String otherNationalityInput,
            @RequestParam String countryFrom,
            @RequestParam String dateOfArrival,
            @RequestParam String flightNo,
            // @RequestParam String countryCode,
            @RequestParam String mobileNo,
            @RequestParam String email,
            @RequestParam int accompaniedBaggageCount,
            @RequestParam int unaccompaniedBaggageCount,
        
            
            Model model) {


 
// paymentId,
        String sql = "INSERT INTO baggage (entry_point, passenger_name, passport_number, passport_validity_date,nationality, previous_country, dateofarrival, flight_no, mobile_no, email, accom_no, unaccom_no,meat_products,foreign_currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        long generatedId = -1;
        // Use a try-with-resources block to ensure the resources are properly closed
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters
            preparedStatement.setString(1, entryPoint);
            preparedStatement.setString(2, passengerName);
            preparedStatement.setString(3, passportNumber);
            preparedStatement.setString(4, passportValidityDate);

            if ("Other".equalsIgnoreCase(nationality)) {
                preparedStatement.setString(5, otherNationalityInput);
            } else {
                preparedStatement.setString(5, nationality);
            }
            preparedStatement.setString(6, countryFrom);
            preparedStatement.setString(7, dateOfArrival);
            preparedStatement.setString(8, flightNo);
            // preparedStatement.setString(9, countryCode);
            preparedStatement.setString(9, mobileNo);
            preparedStatement.setString(10, email);
            preparedStatement.setInt(11, accompaniedBaggageCount);
            preparedStatement.setInt(12, unaccompaniedBaggageCount);
            preparedStatement.setString(13, " ");
            preparedStatement.setString(14, " ");

            // Execute the insert statement
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            //System.out.println("generatedKeys================================================"+generatedKeys);
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
                // You can use the generatedId as needed
                System.out.println("Generated ID: " + generatedId);
            }




            //find latest baggage id
            String sql_baggage = "SELECT * FROM baggage WHERE id = ?";
            Map<String, Object> mostRecentBaggageList = jdbcTemplate.queryForMap(sql_baggage, generatedId);
            Integer autoincrementId = (Integer) mostRecentBaggageList.get("id");
            String airportName = (String)mostRecentBaggageList.get("entry_point");

            String sql_airport = "SELECT * FROM airport_list WHERE air_port_names = ?";
            Map<String, Object> airportDetails = jdbcTemplate.queryForMap(sql_airport, airportName);
            String officeCode = (String) airportDetails.get("office_code");
            int incrementId = autoincrementId.intValue();
            String autoincrementIdAsString = String.format("%07d", incrementId);
            SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
            String currentYear = yearFormat.format(new Date());
            String paymentId = officeCode + currentYear + autoincrementIdAsString;
            System.out.println("=========================="+paymentId);
            String sqlBaggage = "UPDATE baggage SET payment_id=? WHERE id=?";
            jdbcTemplate.update(sqlBaggage,paymentId,generatedId);
            

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during the database operation
        }

        return "redirect:/baggagestart/show?generatedId=" + generatedId;
    }

    // --->baggage update<----//
    @PostMapping("/baggageUpdate")
    public String updateBaggage(
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            @RequestParam String entryPoint,
            @RequestParam String passengerName,
            @RequestParam String passportNumber,
            @RequestParam String passportValidityDate,
            @RequestParam String nationality,
            @RequestParam String otherNationalityInput,
            @RequestParam String countryFrom,
            @RequestParam String dateOfArrival,
            @RequestParam String flightNo,
            @RequestParam String mobileNo,
            @RequestParam String email,
            @RequestParam int accompaniedBaggageCount,
            @RequestParam int unaccompaniedBaggageCount,
            @RequestParam String idMeat,
            @RequestParam String idCurrency,

            Model model) {
        String sql = "UPDATE baggage SET entry_point=?, passenger_name=?, passport_number=?, passport_validity_date=?, nationality=?, previous_country=?, dateofarrival=?, flight_no=?, mobile_no=?, email=?, accom_no=?, unaccom_no=?,meat_products=?,foreign_currency=? WHERE id=?";
           
        String otherNationality ="";
         if ("Other".equalsIgnoreCase(nationality)) {
                 otherNationality =  otherNationalityInput;
            } else {
                 otherNationality = nationality;
            }


        try {
            jdbcTemplate.update(
                    sql,
                    entryPoint,
                    passengerName,
                    passportNumber,
                    passportValidityDate,
                    otherNationality,
                    countryFrom,
                    dateOfArrival,
                    flightNo,
                    mobileNo,
                    email,
                    accompaniedBaggageCount,
                    unaccompaniedBaggageCount,
                    idMeat,
                    idCurrency,
                    id // Set the id parameter for the WHERE clause
            );
       
         
                return "redirect:/baggagestart/show?generatedId=" + id;
             
            // If the update is successful, you can perform any necessary actions here.
            // For example, you can retrieve the updated record and pass it to the view.
             // Redirect to the updated record

            // return "redirect:/reportEdit?generatedId="+id;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // You can customize this based on your error handling logic.
        }
    }


    //------> product insert <------//
    @PostMapping("/productInfo")
    @ResponseBody

    public int ProductInfo(@RequestBody Map<String, Object> productInfo) {
        String additiona_pay = ((String) productInfo.get("additional_payment"));

        String productName = (String) productInfo.get("productName");
        String otherItem = (String) productInfo.get("otherItem");
        // String baggageID = (String) productInfo.get("baggageID");
        String unit = (String) productInfo.get("unit");
        String inchi = (String) productInfo.get("inchi");


        Integer quantity = Integer.parseInt((String) productInfo.get("quantity"));
        double perUnitValue = Double.parseDouble((String) productInfo.get("perUnitValue"));
        // double totalValue = Double.parseDouble((String) productInfo.get("totalValue"));
        double tax = Double.parseDouble((String) productInfo.get("tax"));
        double taxAmount = Double.parseDouble((String) productInfo.get("taxAmount"));
        double add_pay = 0;
        if (!additiona_pay.isEmpty()){
            add_pay=Double.parseDouble((String) productInfo.get("additional_payment"));
        }
          double additional_payment = add_pay;
       


        String itemSql = "SELECT id FROM baggage_item_info WHERE item_name = ?";
        Integer itemId = jdbcTemplate.queryForObject(itemSql, Integer.class, productName);


        String baggageSql = "SELECT id, payment_id FROM baggage WHERE id = ?";
        Map<String, Object> baggageInfo = jdbcTemplate.queryForMap(baggageSql, productInfo.get("baggageID"));
        String paymentId = (String) baggageInfo.get("payment_id");

       








        System.out.println("paymentId==========================="+paymentId);
        // Check if itemId is not null (i.e., productName exists in baggage_item_info)
        if (itemId != null) {
            // Define the SQL query to insert data into the baggage_product_add table
            String insertSql = "INSERT INTO baggage_product_add (baggage_id, item_id, other_item, unit_name, inchi, qty, value, tax_percentage, tax_amount,additional_payment, payment_id, entry_by, entry_at) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  NOW())";

            // Create a KeyHolder to retrieve the generated key (the ID)
            KeyHolder keyHolder = new GeneratedKeyHolder();

            // Execute the SQL query with the extracted values and the KeyHolder
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

                ps.setObject(1, productInfo.get("baggageID"));
                ps.setObject(2, itemId);
                ps.setString(3, otherItem);
                ps.setString(4, unit);
                ps.setString(5, inchi);
                ps.setInt(6, quantity);
                ps.setDouble(7, perUnitValue);
                ps.setDouble(8, tax);
                ps.setDouble(9, taxAmount);
                ps.setDouble(10, additional_payment);
                ps.setString(11, paymentId);

                ps.setInt(12, 1); // Replace with the actual entry_by value
                return ps;
            }, keyHolder);

            // Retrieve the generated ID
            Number generatedId = keyHolder.getKey();
            int generatedIdInt = generatedId.intValue(); // Convert to integer if needed

            System.out.println("Generated ID: " + generatedIdInt);

            // Rest of your code
            return generatedIdInt;
        } else {
            // Handle the case where productName does not exist in baggage_item_info
            System.out.println("Product not found in baggage_item_info");
            return -1;
        }

    }
    // Delete product in ajax table
    @PostMapping("/productDelete")
    @ResponseBody
    public void productDelete(@RequestBody Map<String, Object> deleteId) {
        Integer baggageID = (Integer) deleteId.get("idToDelete");
        if (baggageID != null) {
            try {
                String sql = "DELETE FROM baggage_product_add WHERE id = ?";
                jdbcTemplate.update(sql, baggageID);
            } catch (Exception e) {
                System.err.println("Error deleting product: " + e.getMessage());
            }
        } else {
            System.err.println("Invalid ID provided.");
        }
    }

    @PostMapping("/getProductData")
    @ResponseBody
    public Map<String, Object> productField(@RequestParam String productString) {
        String productName = productString.replace("+", " ");
        // Initialize a map to store the product data
        Map<String, Object> productData = new HashMap<>();

        // Execute the SQL query to fetch unit_name and tax_percentage based on
        // item_name
        String itemSql = "SELECT * FROM baggage_item_info WHERE item_name = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(itemSql, productName);
            // Store the fetched data in the map
            productData.put("unit", result.get("unit_name"));
            productData.put("taxPercentage", result.get("tax_percentage"));

            productData.put("cd", result.get("cd"));
            productData.put("rd", result.get("rd"));
            productData.put("sd", result.get("sd"));
            productData.put("vat", result.get("vat"));
            productData.put("ait", result.get("ait"));
            productData.put("at", result.get("at"));
            productData.put("additional_payment", result.get("additional_payment"));
            productData.put("duty_free", result.get("duty_free"));


        } catch (EmptyResultDataAccessException e) {
            // Handle the case where no matching product is found
            productData.put("error", "Product not found");
        }

        // Return the product data as JSON
        return productData;
    }

    //-------->Report Page show<---------//
    @GetMapping("/reportEdit")
    public String reportShow(){
       return "baggage_view_user_edit";
    }

    //------->Reload ajax table stay entry value<---------// 
    @PostMapping("/valueStay")
    @ResponseBody
    public  List<Map<String, Object>> valueStay(@RequestParam int baggageId){
        System.out.println(baggageId);
        String sql="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql, baggageId);
        
        
       return productshow;
    }




        // --->Final submit  update value set <----//
    @PostMapping("/finalsubmit")
    public String finalsubmitBaggage(
        
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            @RequestParam String entryPoint,
            @RequestParam String passengerName,
            @RequestParam String passportNumber,
            @RequestParam String passportValidityDate,
            @RequestParam String nationality,
            @RequestParam String otherNationalityInput,
            @RequestParam String countryFrom,
            @RequestParam String dateOfArrival,
            @RequestParam String flightNo,
            @RequestParam String mobileNo,
            @RequestParam String email,
            @RequestParam int accompaniedBaggageCount,
            @RequestParam int unaccompaniedBaggageCount,
            @RequestParam String idMeat,
            @RequestParam String idCurrency,

            Model model) {
        String sql = "UPDATE baggage SET entry_point=?, passenger_name=?, passport_number=?, passport_validity_date=?, nationality=?, previous_country=?, dateofarrival=?, flight_no=?, mobile_no=?, email=?, accom_no=?, unaccom_no=?,meat_products=?,foreign_currency=?,status='unapproved' WHERE id=?";
           
        String otherNationality ="";
         if ("Other".equalsIgnoreCase(nationality)) {
                 otherNationality =  otherNationalityInput;
            } else {
                 otherNationality = nationality;
            }


        try {
            jdbcTemplate.update(
                    sql,
                    
                    entryPoint,
                    passengerName,
                    passportNumber,
                    passportValidityDate,
                    otherNationality,
                    countryFrom,
                    dateOfArrival,
                    flightNo,
                    mobileNo,
                    email,
                    accompaniedBaggageCount,
                    unaccompaniedBaggageCount,
                    idMeat,
                    idCurrency,
                    id // Set the id parameter for the WHERE clause
            );
         
                  
          //report show object pass    
        Map<String, Object> requestParameters = new HashMap<>();
        requestParameters.put("ID", id);

        requestParameters.put("entry_point", entryPoint);
        requestParameters.put("passenger_name", passengerName);
        requestParameters.put("passport_number", passportNumber);
        requestParameters.put("passport_validity_date", passportValidityDate);
        requestParameters.put("nationality", otherNationality);
        requestParameters.put("previous_country", countryFrom);
        requestParameters.put("dateofarrival", dateOfArrival);
        requestParameters.put("flight_no", flightNo);
        requestParameters.put("mobile_no", mobileNo);
        requestParameters.put("email", email);
        requestParameters.put("accom_no", accompaniedBaggageCount);
        requestParameters.put("unaccom_no", unaccompaniedBaggageCount);
        requestParameters.put("meat_products", idMeat);
        requestParameters.put("foreign_currency", idCurrency); 
// 
        model.addAttribute("reportShow", requestParameters);

        String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);

        String sql_baggage = "SELECT * FROM baggage WHERE id = ?";
        Map<String, Object> mostRecentBaggageList = jdbcTemplate.queryForMap(sql_baggage, id);

        String sqlPayHistory = "SELECT * FROM payment_history WHERE baggage_id = ?";
        try {
            List<Map<String, Object>> paymentHistoryById = jdbcTemplate.queryForList(sqlPayHistory, id);
            // Query returned data, handle it here
            model.addAttribute("paymentHistoryById", paymentHistoryById);


            System.out.println("paymentHistoryById =================================="+ paymentHistoryById);
        } catch (EmptyResultDataAccessException ex) {
            // Query didn't return any data, handle it here
            model.addAttribute("emptyPaymentHistory", true);
             System.out.println("emptyPaymentHistory ================================  return not data==");
        }


        model.addAttribute("mostRecentBaggageList", mostRecentBaggageList);
        model.addAttribute("showProduct", productshow);
        


          return "baggage_view_user_edit";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // You can customize this based on your error handling logic.
        }
      
    }

        // --->Final submit  update value set <----//
    @PostMapping("/finalsubmitAdmin")
    public String finalsubmitBaggageForAdmin(
        
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            @RequestParam String entryPoint,
            @RequestParam String passengerName,
            @RequestParam String passportNumber,
            @RequestParam String passportValidityDate,
            @RequestParam String nationality,
            @RequestParam String otherNationalityInput,
            @RequestParam String countryFrom,
            @RequestParam String dateOfArrival,
            @RequestParam String flightNo,
            @RequestParam String mobileNo,
            @RequestParam String email,
            @RequestParam int accompaniedBaggageCount,
            @RequestParam int unaccompaniedBaggageCount,
            @RequestParam String idMeat,
            @RequestParam String idCurrency,

            Model model) {
        String sql = "UPDATE baggage SET entry_point=?, passenger_name=?, passport_number=?, passport_validity_date=?, nationality=?, previous_country=?, dateofarrival=?, flight_no=?, mobile_no=?, email=?, accom_no=?, unaccom_no=?,meat_products=?,foreign_currency=?,status='unapproved' WHERE id=?";
           
        String otherNationality ="";
         if ("Other".equalsIgnoreCase(nationality)) {
                 otherNationality =  otherNationalityInput;
            } else {
                 otherNationality = nationality;
            }


        try {
            jdbcTemplate.update(
                    sql,

                    entryPoint,
                    passengerName,
                    passportNumber,
                    passportValidityDate,
                    otherNationality,
                    countryFrom,
                    dateOfArrival,
                    flightNo,
                    mobileNo,
                    email,
                    accompaniedBaggageCount,
                    unaccompaniedBaggageCount,
                    idMeat,
                    idCurrency,
                    id // Set the id parameter for the WHERE clause
            );
         
                  
          //report show object pass    
        Map<String, Object> requestParameters = new HashMap<>();
        requestParameters.put("ID", id);
        requestParameters.put("entry_point", entryPoint);
        requestParameters.put("passenger_name", passengerName);
        requestParameters.put("passport_number", passportNumber);
        requestParameters.put("passport_validity_date", passportValidityDate);
        requestParameters.put("nationality", otherNationality);
        requestParameters.put("previous_country", countryFrom);
        requestParameters.put("dateofarrival", dateOfArrival);
        requestParameters.put("flight_no", flightNo);
        requestParameters.put("mobile_no", mobileNo);
        requestParameters.put("email", email);
        requestParameters.put("accom_no", accompaniedBaggageCount);
        requestParameters.put("unaccom_no", unaccompaniedBaggageCount);
        requestParameters.put("meat_products", idMeat);
        requestParameters.put("foreign_currency", idCurrency); 
        model.addAttribute("reportShow", requestParameters);

        String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);




        String paymentSql = "SELECT * FROM payment_history WHERE baggage_id = ?";
        List<Map<String, Object>> paymentHistoryInfo = jdbcTemplate.queryForList(paymentSql, id);

        if (!paymentHistoryInfo.isEmpty()) {
           
            Double totalPaidAmount = 0.0;
            for (Map<String,Object> paymentHistory : paymentHistoryInfo) {
               Double paidAmount = (Double) paymentHistory.get("paid_amount");
               
            totalPaidAmount= totalPaidAmount+paidAmount;


            }
            model.addAttribute("totalPaidAmount", totalPaidAmount);
            System.out.println("paymentHistoryInfo======================================="+totalPaidAmount);


            // Now, you can use the paidAmount value
        } else {
            Double totalPaidAmount=0.0;
            model.addAttribute("totalPaidAmount", totalPaidAmount);

            // Handle the case where no rows were found for the given id
        }


        model.addAttribute("showProduct", productshow);

          return "baggage_view_user_edit_admin";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // You can customize this based on your error handling logic.
        } 
      
    }

        @GetMapping("/confrimPage")
         public String confrimPage(
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            Model model,Principal principal) {
            String baggageSql= "SELECT * FROM baggage WHERE id =?";
            
            Map<String, Object>requestParameters= jdbcTemplate.queryForMap(baggageSql, id);
            String emailId = (String) requestParameters.get("email");
            System.out.println("=============================%%%%%%%%%%%%%%%%"+emailId);
            model.addAttribute("reportShow", requestParameters);

            String paymentStatus = "Processing";
            String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
            jdbcTemplate.update(sqlBaggage,paymentStatus,id);


            String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
            List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
            Double totalTaxAmount = 0.0;
            for (Map<String, Object> row : productshow) {
                String taxAmount = (String) row.get("tax_amount");
                System.out.println("=========================================================="+taxAmount);
                if (taxAmount != null) {
                    totalTaxAmount += Double.parseDouble(taxAmount);
                }
                 System.out.println("====totalTaxAmount======================================================"+totalTaxAmount);
            }
            String payment_id= (String)requestParameters.get("payment_id");
            Integer baggage_id= (Integer)requestParameters.get("id");
            LocalDateTime currentDateTime = LocalDateTime.now();

            //System.out.println("currentDateTime=============================="+currentDateTime);
            try (Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO payment_history (baggage_id,paid_amount, payment_id,payment_date) VALUES (?,?,?,?)"
            )) {
            //System.out.println("totalTaxAmount=============================================="+totalTaxAmount);
           preparedStatement.setInt(1, baggage_id);
           preparedStatement.setDouble(2, totalTaxAmount);
           preparedStatement.setString(3, payment_id);
           preparedStatement.setTimestamp(4, Timestamp.valueOf(currentDateTime));

           preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String link="/baggagestart/confrimPage?id="+id;
           


            Context context = new Context();
            context.setVariable("passengerName", requestParameters.get("passenger_name"));
            context.setVariable("totalTaxAmount", totalTaxAmount);
            context.setVariable("link", link);

           // String emailContent = templateEngine.process("email-template", context);




            // String text = "dkfnldskfjlaskjdfn";

            // String link="/baggagestart/confrimPage?id="+id;
            // String gmail = (String) requestParameters.get("email");
           // Context context = new Context();
           // context.setVariable("passengerName", requestParameters.get("passenger_name"));
          //  context.setVariable("totalTaxAmount", totalTaxAmount);
          //  context.setVariable("link", link);
           // String emailContent = templateEngine.process("email-template", context);
            // SimpleMailMessage message = new SimpleMailMessage();
            // message.setFrom("nbroffice71@gmail.com");
            // message.setTo(gmail);
            // //message.setText(emailContent);
            // message.setText(
            //     "Hello Mr/Mrs,"+ requestParameters.get("passenger_name")+
            //     ", You are successfully submitted your baggage information."
            //     +" You paid "+totalTaxAmount+" only"+
            //     "Click <a href='" + link + "'>here</a> to get Details."
                
            //     );

            // message.setSubject("NBR Baggage Declaration");
            // mailSender.send(message);     
            try {
               // Double totalPaidAmount = 0.0;
                String gmail = (String) requestParameters.get("email");
            
                String baggage_Sql = "SELECT * FROM baggage WHERE id =?";
                Map<String, Object> baggageQuery = jdbcTemplate.queryForMap(baggage_Sql, id);
            

            emailService.sendEmailWithAttachment(emailId, "NBR Baggage Declaration", "Body", pdfData, "nbr_baggage_application.pdf");
           // return ResponseEntity.ok("Email sent successfully!");
         // return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
           // return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }


                String baggageProductAddJoin = "SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
                List<Map<String, Object>> allProductQuery = jdbcTemplate.queryForList(baggageProductAddJoin, id);
            
                List<String> includedFields = Arrays.asList("passenger_name","entry_point","flight_no","passport_number");
              //  List<String> includedFields = Arrays.asList("id","item_id","payment_id"); // Replace with your actual field names
                List<Object> rowData = new ArrayList<>(allProductQuery);
                rowData.add(baggageQuery);
            
                byte[] pdfData = pdfGenerationService.generatePdf(rowData, includedFields,totalTaxAmount);
            
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("inline", "NBR_baggage_declaration.pdf");
            
                emailService.sendEmailWithAttachment(gmail, "NBR Baggage Declaration", "Body", pdfData, "nbr_baggage_application.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
            

        


            // SimpleMailMessage message = new SimpleMailMessage();
            // message.setFrom("nbroffice71@gmail.com");
            // message.setTo(gmail);
            // //message.setText(emailContent);
            // message.setText(
            //     "Hello Mr/Mrs,"+ requestParameters.get("passenger_name")+
            //     ", You are successfully submitted your baggage information."
            //     +" You paid "+totalTaxAmount+" only"+
            //     "Click <a href='" + link + "'>here</a> to get Details."
                
            //     );

            // message.setSubject("NBR Baggage Declaration");
            // mailSender.send(message);     
            model.addAttribute("showProduct", productshow);
            
            return "confirmPage";
     
    }


    
    
    
    @PostMapping("/confirm-pay-by-admin")
        public String confrimPayByAdmin(@RequestParam int id, @RequestParam Double payableAmount,Model model){

            String baggageSql= "SELECT * FROM baggage WHERE id =?";
            Map<String, Object>requestParameters= jdbcTemplate.queryForMap(baggageSql, id);
            model.addAttribute("reportShow", requestParameters);



            String formattedAmount = String.format("%.2f", payableAmount);
            double paidAmount = Double.parseDouble(formattedAmount);


            String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
            List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
            Double totalTaxAmount = 0.0;
            for (Map<String, Object> row : productshow) {
                String taxAmount = (String) row.get("tax_amount");

                if (taxAmount != null) {
                    totalTaxAmount += Double.parseDouble(taxAmount);
                }
            }
            String mailBody ="";
            String link="/baggagestart/confrimPage?id="+id;

            LocalDateTime currentDateTime = LocalDateTime.now();
            String payment_id= (String)requestParameters.get("payment_id");

            if (payableAmount != null && !payableAmount.equals(0.0)){
                try (Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO payment_history (baggage_id,paid_amount, payment_id,payment_date) VALUES (?,?,?,?)"
                )) {
                    preparedStatement.setLong(1, id);
                    preparedStatement.setDouble(2, paidAmount);
                    preparedStatement.setString(3, payment_id);
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(currentDateTime));

                preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                    e.printStackTrace();
                    }

                   mailBody ="Hello Mr/Mrs,"+ requestParameters.get("passenger_name")+
                ", You are successfully submitted your baggage information."
                +" You paid "+paidAmount+" only"+"Click <a href='" + link + "'>here</a> to get Details." ;

            }else{
                 mailBody ="Thank you for you baggage payment" ;
            }
            
            String paymentStatus = "Paid";
            String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
            jdbcTemplate.update(sqlBaggage,paymentStatus,id);

            

            String gmail = (String) requestParameters.get("email");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nbroffice71@gmail.com");
            message.setTo(gmail);
            message.setText(mailBody);

            message.setSubject("NBR Baggage Declaration");
            mailSender.send(message);   


            model.addAttribute("reportShow", requestParameters);  
            model.addAttribute("showProduct", productshow);

             return "confirmPageAdmin";
         }






        @GetMapping("/payment-not-at-this-time")
         public String paymentNotAtThisTime(
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            Model model,Principal principal) {
            String sql= "SELECT * FROM baggage WHERE id =?";
            Map<String, Object>requestParameters= jdbcTemplate.queryForMap(sql, id);
            model.addAttribute("reportShow", requestParameters);

            String status = "Unpaid";
            String sqlBaggage = "UPDATE baggage SET payment_status=? WHERE id=?";
            jdbcTemplate.update(sqlBaggage,status,id);


            String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
            List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);
            int totalTaxAmount = 0;
            for (Map<String, Object> row : productshow) {
                String taxAmount = (String) row.get("tax_amount");
                System.out.println("=========================================================="+taxAmount);
                if (taxAmount != null) {
                    totalTaxAmount += Double.parseDouble(taxAmount);
                }
            }
            String gmail = (String) requestParameters.get("email");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nbroffice71@gmail.com");
            message.setTo(gmail);
            message.setText(
                "Hello Mr/Mrs,"+ requestParameters.get("passenger_name")+
                ", Your Total Due Amount is."
                +totalTaxAmount+" only"
                
                );
            message.setSubject("NBR Baggage Declaration");
            mailSender.send(message);     
            model.addAttribute("showProduct", productshow);
            
            return "confirmPage";
     
    }


//Count for Baggage with 
//This code is implemented by fahim
@GetMapping("/countApprovedBaggage")
@ResponseBody
public int countApprovedBaggage(Principal principal) {
    String airportname=airportInformation.getAirport(principal);
   if(airportname.equalsIgnoreCase("all")){
       String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'approved'";
    return jdbcTemplate.queryForObject(sql, Integer.class);
   }else{
     String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'approved' AND entry_point=?";
    return jdbcTemplate.queryForObject(sql, Integer.class,airportname);
   }
   
}
@GetMapping("/countunApprovedBaggage")
@ResponseBody
public int countunApprovedBaggage(Principal principal) {
   
       String airportname=airportInformation.getAirport(principal);
   if(airportname.equalsIgnoreCase("all")){
       String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'unapproved'";
    return jdbcTemplate.queryForObject(sql, Integer.class);
   }else{
     String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'unapproved' AND entry_point=?";
    return jdbcTemplate.queryForObject(sql, Integer.class,airportname);
   }
}
@GetMapping("/countrejectedBaggage")
@ResponseBody
public int countrejectedBaggage(Principal principal) {
    

  String airportname=airportInformation.getAirport(principal);
   if(airportname.equalsIgnoreCase("all")){
       String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'rejected'";
    return jdbcTemplate.queryForObject(sql, Integer.class);
   }else{
     String sql = "SELECT COUNT(*) FROM baggage WHERE status = 'rejected' AND entry_point=?";
    return jdbcTemplate.queryForObject(sql, Integer.class,airportname);
   }
}

@GetMapping("/countAllBaggage")
@ResponseBody
public int countAllBaggage(Principal principal) {
    
//    int yyy= 
//    System.out.println(yyy);
String airportname=airportInformation.getAirport(principal);
if(airportname.equalsIgnoreCase("all")){
    String sql = "SELECT COUNT(*) FROM baggage";
 return jdbcTemplate.queryForObject(sql, Integer.class);
}else{
  String sql = "SELECT COUNT(*) FROM baggage WHERE  entry_point=?";
 return jdbcTemplate.queryForObject(sql, Integer.class,airportname);
}
    
}




//for  baggage approve update 
@PostMapping("/baggage_approve_update")
public String currencApproveUpdate(@RequestParam int id, @RequestParam String status, @RequestParam String confNote, @RequestParam String page_route, Principal principal) {
    String paymentStatus = "Paid";
    String username = principal.getName();
    String sql = "UPDATE baggage SET status=?, payment_status=?, entry_by=? WHERE id=?";
    jdbcTemplate.update(sql, status, paymentStatus, username, id);
   // String usernameSession = principal.getName();
        if ("total_baggage".equals(page_route)) {
            return "redirect:/baggageshow/baggagetotal";
        } else if ("approve".equals(page_route)) {
            return "redirect:/baggagestart/approve-baggage-list";
        } else if ("unapproved".equals(page_route)) {
            return "redirect:/baggageshow/unapprovedbaggagetotal";
        }else if ("reject".equals(page_route)) {
            return "redirect:/baggagestart/rejected-baggage-list";
        }
        // Handle the case where page_route doesn't match any of the conditions.
       return "redirect:/baggageshow/baggagetotal";
}

    //for baggage reject list 
    @PostMapping("/baggage_reject_update")
    public String currencRejectUpdate(@RequestParam int id, @RequestParam String status, @RequestParam String confNote, @RequestParam String page_route, Principal principal) {

        String username = principal.getName();
        String sql = "UPDATE baggage SET status=?, entry_by=?, confNote=? WHERE id=?";
        jdbcTemplate.update(sql, status, username, confNote, id);
        String usernameSession = principal.getName();
        if ("total_baggage".equals(page_route)) {
            return "redirect:/baggageshow/baggagetotal";
        } else if ("approve".equals(page_route)) {
            return "redirect:/baggagestart/approve-baggage-list";
        } else if ("unapproved".equals(page_route)) {
            return "redirect:/baggageshow/unapprovedbaggagetotal";
        }else if ("reject".equals(page_route)) {
            return "redirect:/baggagestart/rejected-baggage-list";
        }
        // Handle the case where page_route doesn't match any of the conditions.
       return "redirect:/baggageshow/baggagetotal";
    }



//for baggage on examination
    @PostMapping("/baggage_on_examination_update")
    public String baggageOnExaminationUpdate( @RequestParam int id,@RequestParam String status,@RequestParam String confNote, @RequestParam String page_route, Principal principal) {

        String username=principal.getName();
        String sql = "UPDATE baggage SET status=?,entry_by=?, confNote=? WHERE id=?";
        jdbcTemplate.update(sql,status,username,confNote,id);
        String usernameSession=principal.getName();

        if ("total_baggage".equals(page_route)) {
            return "redirect:/baggageshow/baggagetotal";
        } else if ("approve".equals(page_route)) {
            return "redirect:/baggagestart/approve-baggage-list";
        } else if ("unapproved".equals(page_route)) {
            return "redirect:/baggageshow/unapprovedbaggagetotal";
        }else if ("reject".equals(page_route)) {
            return "redirect:/baggagestart/rejected-baggage-list";
        }
        // Handle the case where page_route doesn't match any of the conditions.
       return "redirect:/baggageshow/baggagetotal";
}








}
