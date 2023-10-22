package com.currency.currency_module.controller;




import java.security.Principal;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




import com.currency.currency_module.AirportInformation;
import com.currency.currency_module.NumberToWords;
import com.currency.currency_module.model.BaggageCurrencyAdd;
import com.currency.currency_module.model.CurrencyDeclaration;
import com.currency.currency_module.repository.CurrencyAddRepository;
import com.currency.currency_module.repository.CurrencyDeclarationRepository;
import com.currency.currency_module.services.CurrencyServices;
import com.currency.currency_module.services.AirportService;

@Controller

@RequestMapping("/currencystart")
public class currencyController {
    @Autowired
    CurrencyServices currencyServices;
    @Autowired
    CurrencyAddRepository currencyAddRepository;
    @Autowired 
   CurrencyDeclarationRepository currencyDeclarationRepository;

    @Autowired 
   AirportService airportService;
   @Autowired
   AirportInformation airportInformation;
    NumberToWords numberToWords=new NumberToWords();

    @GetMapping("/show")
    public String index( Model model) {


        model.addAttribute("allAirportList", airportService.getAllAirports());
        return "currency";
    }

    
    @GetMapping("/currencyEdit")
    public String editCurrency(@RequestParam Long id, Model model) {
        // Retrieve the currencyDeclaration object from flash attributes
        System.out.println(id);


        model.addAttribute("allAirportList", airportService.getAllAirports());
        // Add the currencyDeclaration to the model for your edit view
        model.addAttribute("currencyDeclaration", currencyServices.findcurrency(id));
        return "currencyEdit"; // The name of your edit view
    }
    

    @PostMapping("/currencyinsert")
    public String insert(CurrencyDeclaration currencyDeclcuaration) {
    System.out.println("currencyDeclcuaration======================"+currencyDeclcuaration);
    Long id=currencyServices.currencyInsert(currencyDeclcuaration).getId();
    
      
        return "redirect:/currencystart/currencyEdit?id="+id;
    }

    @PostMapping("/currencyUpdate")
    public String updateCurrency( CurrencyDeclaration updatedCurrencyDeclaration, RedirectAttributes redirectAttributes) {
    // Perform the update operation using currencyServices
    currencyServices.currencyUpdate(updatedCurrencyDeclaration);
    Long id=updatedCurrencyDeclaration.getId();
 
      
    // Redirect to the edit page with a success message

    return "redirect:/currencystart/currencyEdit?id="+id;
}

    @ResponseBody
    @PostMapping("/addCurrency")
    public BaggageCurrencyAdd addCurrency(@RequestBody BaggageCurrencyAdd addCurrency){
       
        
        return currencyServices.addCurrency(addCurrency);
    }

    @GetMapping("/currencyview")
    public String currencyview(){
        return "currencyView";
    }

    @PostMapping("/currencyformStay")
    @ResponseBody
    public List<BaggageCurrencyAdd> currencyformStaty(@RequestParam Long currencyId){
      
        return currencyAddRepository.findAllByCurrencyId(currencyId);
    }

    @PostMapping("/finalsubmit")
    public String currencyFinalSubmit( CurrencyDeclaration updatedCurrencyDeclaration,Model model){
       currencyServices.currencyUpdate(updatedCurrencyDeclaration);
        

       //System.out.println(updatedCurrencyDeclaration.getPassportIssueDate());
        Long id=updatedCurrencyDeclaration.getId();
       // System.out.println(id);
       List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
      // System.out.println(listcurrency);
        model.addAttribute("Currency",updatedCurrencyDeclaration);
        model.addAttribute("Baggagecurrency",listcurrency);
        return "redirect:/currencystart/finalsubmiform?id="+id;
    }

    @GetMapping("/finalsubmiform")
    public String currencyFinalSubmitform( @RequestParam Long id,Model model){
     
       
       List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
       System.out.println(listcurrency);
        model.addAttribute("Currency",currencyServices.findcurrency(id));
        model.addAttribute("Baggagecurrency",listcurrency);
        return "currencyView";

    }
    @PostMapping("/delete")
    @ResponseBody
    public void deleTecurrency(@RequestBody BaggageCurrencyAdd baggageCurrencyAdd){
       // System.out.println();
        currencyAddRepository.deleteById(baggageCurrencyAdd.getId());
       // System.out.println("Success");
    }

    @GetMapping("/confirmgenaral")
   
    public String updatestatuscurrency(@RequestParam Long id, Model model){
        CurrencyDeclaration currencyDeclaration= currencyServices.findcurrency(id);
        currencyDeclaration.setStatus("unchecked");
        currencyDeclarationRepository.save(currencyDeclaration);
         model.addAttribute("Currency",currencyServices.findcurrency(id));
        

       return "currencyViewconfirm";
    }



    @GetMapping("/unapprovedcurrency")
    @ResponseBody
    public List<CurrencyDeclaration> unapprovedcurrency(){
       // System.out.println();
       
      return currencyServices.unapprovedcurrency();

    }





    @GetMapping("/showunapprovedcurrency")
    public String showunapprovedcurrency(Model model){
        //System.out.println();
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatus("unchecked");
         model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
      return "dashboarddatatable";

    }
    @GetMapping("/currencyadminedit")
    public String currencyadminedit(Model model){
        //System.out.println();
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findAll();
         model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
      return "currencyadminedit";
    }


    @GetMapping("/unapprove-currency")
    public String unapproveCurrency(Model model,Principal principal){
        String totolunapprove= airportInformation.getAirport(principal);
        if(totolunapprove.equalsIgnoreCase("all")){
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatus("unchecked");      
        model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
        }else{
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatusAndEntryPoint("unchecked",totolunapprove);      
        model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
        }
        return "currency_unapprove";
    }

    @GetMapping("/approve-currency")
    public String approveCurrency(Model model, Principal principal){
        String totalapprove= airportInformation.getAirport(principal);
        if(totalapprove.equalsIgnoreCase("all")){
            List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatus("checked");
            model.addAttribute("approveCurrency",listCurrencyDeclaration);
        }else{
            List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatusAndEntryPoint("checked",totalapprove);
            model.addAttribute("approveCurrency",listCurrencyDeclaration);
        }
        
        return "currency_approve";
    }
    @GetMapping("/reject-currency")
    public String rejectCurrency(Model model,Principal principal){
        String totalreject= airportInformation.getAirport(principal);
        if(totalreject.equalsIgnoreCase("all")){
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatus("rejected");
        model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
        }else{
        List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findByStatusAndEntryPoint("rejected",totalreject);
        model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
        }
        return "currency_reject";
    }

    @GetMapping("/total-currency-application")
    public String totalCurrency(Model model, Principal principal){
        String totalairport=airportInformation.getAirport(principal);
        System.out.println(totalairport);
        if(totalairport.equalsIgnoreCase("all")){
            List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findAll();
             model.addAttribute("unapproveCurrency",listCurrencyDeclaration);
        }else{
             List<CurrencyDeclaration> listCurrencyDeclaration = currencyDeclarationRepository.findAllByEntryPoint(totalairport);
              model.addAttribute("unapproveCurrency",listCurrencyDeclaration); 
        }
        return "currency_total";
    }
    

    @GetMapping("/showapprovedcurrencyform")
    public String showapprovedcurrencyform( @RequestParam Long id,Model model){
        CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
         List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
         model.addAttribute("Currency",currencydata);
        model.addAttribute("Baggagecurrency",listcurrency);
       
      return "currencyConfirmPage";

    }


        @GetMapping("/show-currency-details")
    public String showCurrencyDetails( @RequestParam Long id,Model model){
       


        CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
         List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
         model.addAttribute("Currency",currencydata);
        model.addAttribute("Baggagecurrency",listcurrency);
       
      return "currencyApprovalPage";


    }

 @GetMapping("/show-unapprove-currency-details")
    public String showUnApproveCurrencyDetails( @RequestParam Long id,Model model){
        CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
        List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
        model.addAttribute("Currency",currencydata);
        model.addAttribute("Baggagecurrency",listcurrency);
       
      return "currencyApprovalPage";
    }

    @PostMapping("/currenc_approve_update")
    public String currencApproveUpdate( CurrencyDeclaration updatedApproveCurrencyDeclaration, Principal principal) {
        String usernameSession=principal.getName();
    currencyServices.approveCurrencyUpdate(updatedApproveCurrencyDeclaration,usernameSession);


    // Redirect to the edit page with a success message
    //redirectAttributes.addFlashAttribute("currencyDeclaration", updatedCurrencyDeclaration);
    return "redirect:/currencystart/unapprove-currency";
}

    @PostMapping("/currency_unapprove_update")
    public String currencyUnapproveUpdate( CurrencyDeclaration updatedUnapproveCurrencyDeclaration,Principal principal) {
        // Perform the update operation using currencyServices
         String usernameSession=principal.getName();
        currencyServices.unapproveCurrencyUpdate(updatedUnapproveCurrencyDeclaration,usernameSession);

        return "redirect:/currencystart/unapprove-currency";
    }




    

    @GetMapping("/generalform")
    public String fahim(@RequestParam(required = false, defaultValue = "") Long id,Model model){
        //  CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
        //  List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
        //  model.addAttribute("Currency",currencydata);
        // model.addAttribute("Baggagecurrency",listcurrency);
        return "generalform";
    }


    ///unchecked Status Count
    @GetMapping("/uncheckedstatuscount")
    @ResponseBody
    public long uncheckedstatuscount(Principal principal){
         
        String airportname=airportInformation.getAirport(principal);
        if(airportname.equalsIgnoreCase("all")){
             return currencyDeclarationRepository.countByStatus("unchecked");
        }else{
            return currencyDeclarationRepository.countByStatusAndEntryPoint("unchecked",airportname);
        }

       


    }

    @GetMapping("/checkedstatuscount")
    @ResponseBody
    public long checkedstatuscount(Principal principal){

        String airportname=airportInformation.getAirport(principal);
        if(airportname.equalsIgnoreCase("all")){
            return currencyDeclarationRepository.countByStatus("checked");
        }else{
            return currencyDeclarationRepository.countByStatusAndEntryPoint("checked",airportname);
        }

    }

    @GetMapping("/rejectedstatuscount")
    @ResponseBody
    public long rejectedstatuscount(Principal principal){
       String airportreject=airportInformation.getAirport(principal);
       if(airportreject.equalsIgnoreCase("all")){
        return currencyDeclarationRepository.countByStatus("rejected");
       }else{
        return currencyDeclarationRepository.countByStatusAndEntryPoint("rejected",airportreject);
       }
       
        

    }
    @GetMapping("/allstatuscount")
    @ResponseBody
    public long allstatuscount(Principal principal){
        String totalairport=airportInformation.getAirport(principal);
        if(totalairport.equalsIgnoreCase("all")){
            return currencyDeclarationRepository.count();
        }else{
            return currencyDeclarationRepository.countByEntryPoint(totalairport);
        }
        

    }



}
