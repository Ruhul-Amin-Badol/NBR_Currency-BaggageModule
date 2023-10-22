package com.currency.currency_module.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.currency.currency_module.model.CurrencyDeclaration;

@Controller
@RequestMapping("/baggagestart")
public class baggageController {
    @Autowired
    JdbcTemplate jdbcTemplate;
     @Autowired
   AirportInformation airportInformation;

    //------> From show and hide mode<------//
    @GetMapping("/show")
    public String baggageFrom(@RequestParam(required = false, defaultValue = "") String generatedId, Model model) {
        String sql1 = "SELECT item_name FROM baggage_item_info";
        List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1);
        model.addAttribute("productshow", productshow);


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

     //rejected baggage list by nitol
    @GetMapping("/rejected-baggage-list")
    public String rejectBaggage(Model model,Principal principal) {
        String airportname=airportInformation.getAirport(principal);

        // List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,"approved");
        // model.addAttribute("baggageshow", productshow)

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
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
                // You can use the generatedId as needed
                // System.out.println("Generated ID: " + generatedId);
            }

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

        String productName = (String) productInfo.get("productName");
        // String baggageID = (String) productInfo.get("baggageID");
        String unit = (String) productInfo.get("unit");
        String inchi = (String) productInfo.get("inchi");
        Integer quantity = Integer.parseInt((String) productInfo.get("quantity"));
        double perUnitValue = Double.parseDouble((String) productInfo.get("perUnitValue"));
        // double totalValue = Double.parseDouble((String) productInfo.get("totalValue"));
        double tax = Double.parseDouble((String) productInfo.get("tax"));
        double taxAmount = Double.parseDouble((String) productInfo.get("taxAmount"));

        String itemSql = "SELECT id FROM baggage_item_info WHERE item_name = ?";
        Integer itemId = jdbcTemplate.queryForObject(itemSql, Integer.class, productName);
        // Check if itemId is not null (i.e., productName exists in baggage_item_info)
        if (itemId != null) {
            // Define the SQL query to insert data into the baggage_product_add table
            String insertSql = "INSERT INTO baggage_product_add (baggage_id, item_id, other_item, unit_name, inchi, qty, value, tax_percentage, tax_amount, entry_by, entry_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  NOW())";

            // Create a KeyHolder to retrieve the generated key (the ID)
            KeyHolder keyHolder = new GeneratedKeyHolder();

            // Execute the SQL query with the extracted values and the KeyHolder
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, productInfo.get("baggageID"));
                ps.setObject(2, itemId);
                ps.setString(3, " ");
                ps.setString(4, unit);
                ps.setString(5, inchi);
                ps.setInt(6, quantity);
                ps.setDouble(7, perUnitValue);
                ps.setDouble(8, tax);
                ps.setDouble(9, taxAmount);
                ps.setInt(10, 1); // Replace with the actual entry_by value
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
        String itemSql = "SELECT unit_name, tax_percentage FROM baggage_item_info WHERE item_name = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(itemSql, productName);
            // Store the fetched data in the map
            productData.put("unit", result.get("unit_name"));
            productData.put("taxPercentage", result.get("tax_percentage"));
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

        model.addAttribute("showProduct", productshow);
        
          return "baggage_view_user_edit";
            // If the update is successful, you can perform any necessary actions here.
            // For example, you can retrieve the updated record and pass it to the view.
             // Redirect to the updated record

            // return "redirect:/reportEdit?generatedId="+id;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // You can customize this based on your error handling logic.
        }
      
    }



        @GetMapping("/confrimPage")
         public String confrimPage(
            @RequestParam Long id, // Add a parameter for the unique identifier (id)
            Model model) {
       
       String sql= "SELECT * FROM baggage WHERE id =?";
          Map<String, Object>requestParameters= jdbcTemplate.queryForMap(sql, id);
            model.addAttribute("reportShow", requestParameters);

          String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
          List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);

          model.addAttribute("showProduct", productshow);
           
        return "confirmPage";
     
    }


//     @PostMapping("/finalsubmitadmin")
//     public String finalsubmitBaggageAdmin(
//            @RequestBody Map<String, Object> baggagedataadmin, 
//             Model model) {
//        System.out.println(baggagedataadmin);


//         String sql = "UPDATE baggage SET entry_point=?, passenger_name=?, passport_number=?, passport_validity_date=?, nationality=?, previous_country=?, dateofarrival=?, flight_no=?, mobile_no=?, email=?, accom_no=?, unaccom_no=?,meat_products=?,foreign_currency=? WHERE id=?";
           
//         String otherNationality ="";
//          if ("Other".equalsIgnoreCase(nationality)) {
//                  otherNationality =  otherNationalityInput;
//             } else {
//                  otherNationality = nationality;
//             }


//         try {
//             jdbcTemplate.update(
//                     sql,
//                     entryPoint,
//                     passengerName,
//                     passportNumber,
//                     passportValidityDate,
//                     otherNationality,
//                     countryFrom,
//                     dateOfArrival,
//                     flightNo,
//                     mobileNo,
//                     email,
//                     accompaniedBaggageCount,
//                     unaccompaniedBaggageCount,
//                     idMeat,
//                     idCurrency,
//                     id // Set the id parameter for the WHERE clause
//             );
         
                  
//           //report show object pass    
//         Map<String, Object> requestParameters = new HashMap<>();
//         requestParameters.put("ID", id);
//         requestParameters.put("entry_point", entryPoint);
//         requestParameters.put("passenger_name", passengerName);
//         requestParameters.put("passport_number", passportNumber);
//         requestParameters.put("passport_validity_date", passportValidityDate);
//         requestParameters.put("nationality", otherNationality);
//         requestParameters.put("previous_country", countryFrom);
//         requestParameters.put("dateofarrival", dateOfArrival);
//         requestParameters.put("flight_no", flightNo);
//         requestParameters.put("mobile_no", mobileNo);
//         requestParameters.put("email", email);
//         requestParameters.put("accom_no", accompaniedBaggageCount);
//         requestParameters.put("unaccom_no", unaccompaniedBaggageCount);
//         requestParameters.put("meat_products", idMeat);
//         requestParameters.put("foreign_currency", idCurrency); 
// // 
//         model.addAttribute("reportShow", requestParameters);

//         String sql1="SELECT * FROM baggage_product_add  JOIN  baggage_item_info ON  baggage_item_info.id= baggage_product_add.item_id WHERE baggage_id=?";
//         List<Map<String, Object>> productshow = jdbcTemplate.queryForList(sql1,id);

//         model.addAttribute("showProduct", productshow);
        
//           return "baggage_view_user_edit";
//             // If the update is successful, you can perform any necessary actions here.
//             // For example, you can retrieve the updated record and pass it to the view.
//              // Redirect to the updated record

//             // return "redirect:/reportEdit?generatedId="+id;
            
//         } catch (Exception e) {
//             e.printStackTrace();
//             return "error"; // You can customize this based on your error handling logic.
//         }
      
//     }




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
    public String currencApproveUpdate( @RequestParam int id,@RequestParam String status,@RequestParam String confNote, Principal principal) {

        String username=principal.getName();
        String sql = "UPDATE baggage SET status=?,entry_by=? WHERE id=?";
        jdbcTemplate.update(sql,status,username,id);
    // Perform the update operation using currencyServices
    //System.out.println("===============================================currenc_approve_reject_update");
        String usernameSession=principal.getName();
    


    // Redirect to the edit page with a success message
    //redirectAttributes.addFlashAttribute("currencyDeclaration", updatedCurrencyDeclaration);
    return "redirect:/baggagestart/approve-baggage-list";
}

//for baggage reject list 
    @PostMapping("/baggage_reject_update")
    public String currencRejectUpdate( @RequestParam int id,@RequestParam String status,@RequestParam String confNote, Principal principal) {

        String username=principal.getName();
        String sql = "UPDATE baggage SET status=?,entry_by=? WHERE id=?";
        jdbcTemplate.update(sql,status,username,id);

        String usernameSession=principal.getName();
    


  
    return "redirect:/baggagestart/rejected-baggage-list";
}






}
