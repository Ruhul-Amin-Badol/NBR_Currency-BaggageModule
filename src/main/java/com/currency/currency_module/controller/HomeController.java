package com.currency.currency_module.controller;



import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.model.UserActivityManagement;
import com.currency.currency_module.services.AirportService;
import com.currency.currency_module.services.UserActivityManagementService;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
   UserActivityManagementService userActivityManagementService;
   @Autowired
   AirportInformation airportInformation;
    @Autowired
    AirportService airportService;
   
   
   @Autowired
   HttpSession httpSession;

    @GetMapping("/signin") 
    public String login() {
        return "login";
    }
    @GetMapping("/signinpvt") 
    public String loginpvt() {
        return "loginpvt";
    }
    @GetMapping("/ppm/signinppm") 
    public String loginppm() {
        return "loginppm";
    }
    @GetMapping("/currencyadmin/signincurrency") 
    public String logincurrency() {
        return "loginCurrency";
    }

      @GetMapping("/") 
    public String index(Model model) {
        model.addAttribute("allAirportList", airportService.getAllAirports());
        return "index_0";
    }


    @GetMapping("/index1") 
    public String index1(@RequestParam(required = false, defaultValue = "") String officeCode, Model model) {
        model.addAttribute("airport", airportService.findAirportByOfficeCode(officeCode));
        return "index1";
    }
    @GetMapping("/pvtdashboard") 
    public String pvtdashboard() {
        // model.addAttribute("airport", airportService.findAirportByOfficeCode(officeCode));
        return "pvtdashboard";
    }



    @GetMapping("/dashboard")
    public String dashboard(Principal principal,HttpSession session,Model model) {
        
        String usernameSession = principal.getName();
        UserActivityManagement  
        user= userActivityManagementService.findUserWithUserName(usernameSession);
        model.addAttribute("loggedpersonname",user.getFname());
        session.setAttribute("IsMatrix", user.getUserMatrix());
        session.setAttribute("IsBaggage", user.getBaggageModule());
        session.setAttribute("IsCurrency", user.getCurrencyModule());
        session.setAttribute("IspaymentRecord", user.getPaymentRecord());
        session.setAttribute("Isport", user.getPort());
        session.setAttribute("IspaymentHistory", user.getPaymentHistory());

        return "dashboard";
    }


    @GetMapping("/baggage-search")
    public String passengerBaggageSearch(Model model) {
        return "passenger_baggage_serach";
    }

    @PostMapping("/passenger-baggage-search")
    public String searchBaggage(@RequestParam("passport_number") String passportNumber ,Model model) {
        // Use JDBC to search for baggage based on passport number
        String sql = "SELECT * FROM baggage WHERE passport_number = ?";
        List<Map<String, Object>> baggageData = jdbcTemplate.queryForList(sql, passportNumber);

        // Add baggage data to the model to pass it to the view
        System.out.println("baggageData=====================================" + baggageData);

        // ModelAndView modelAndView = new ModelAndView("passenger_baggage_serach");
        // modelAndView.addObject("baggageData", baggageData);
        model.addAttribute("baggageData", baggageData);
        return "passenger_baggage_serach";
    }


    

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
    @GetMapping("/error")
    public String Error() {
        return "error";
    }
    @GetMapping("/finduser/{username}")
    @ResponseBody
    public UserActivityManagement findUserWithUserName(@PathVariable String username) {
        return userActivityManagementService.findUserWithUserName(username) ;
    }

//     @GetMapping("/profile")
//    public String userProfile(Principal principal) {
//     if (principal != null) {
//         String username = principal.getName(); 
//         System.out.println(username);// Get the username
//         // Fetch additional user data from your user repository or database
//         // ...
//     }
//     // Handle the case where no user is authenticated
//     return "redirect:/login";
// }

    
}
