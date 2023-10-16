package com.currency.currency_module.controller;




import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.currency.currency_module.model.BaggageCurrencyAdd;
import com.currency.currency_module.model.CurrencyDeclaration;
import com.currency.currency_module.repository.CurrencyAddRepository;
import com.currency.currency_module.services.CurrencyServices;


@Controller

@RequestMapping("/currencystart")
public class currencyController {
    @Autowired
    CurrencyServices currencyServices;
    @Autowired
    CurrencyAddRepository currencyAddRepository;

    @GetMapping("/show")
    public String index() {
        return "currency";
    }

    
    @GetMapping("/currencyEdit")
    public String editCurrency(@RequestParam Long id, Model model) {
        // Retrieve the currencyDeclaration object from flash attributes
        System.out.println(id);
        
        // Add the currencyDeclaration to the model for your edit view
        model.addAttribute("currencyDeclaration", currencyServices.findcurrency(id));
        return "currencyEdit"; // The name of your edit view
    }
    

    @PostMapping("/currencyinsert")
    public String insert(CurrencyDeclaration currencyDeclcuaration) {
          
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
       
        System.out.println(addCurrency.getCurrencyName());
        System.out.println(currencyServices.addCurrency(addCurrency).getId());
        
        return currencyServices.addCurrency(addCurrency);
    }

    @GetMapping("/currencyview")
    public String currencyview(){
        return "currencyView";
    }

    @PostMapping("/finalsubmit")
    public String currencyFinalSubmit( CurrencyDeclaration updatedCurrencyDeclaration,Model model){
       currencyServices.currencyUpdate(updatedCurrencyDeclaration);
       System.out.println(updatedCurrencyDeclaration.getPassportIssueDate());
        Long id=updatedCurrencyDeclaration.getId();
        System.out.println(id);
       List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
       System.out.println(listcurrency);
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
        System.out.println();
        currencyAddRepository.deleteById(baggageCurrencyAdd.getId());
        System.out.println("Success");
    }

    @GetMapping("/unapprovedcurrency")
    @ResponseBody
    public List<CurrencyDeclaration> unapprovedcurrency(){
        System.out.println();
       
      return currencyServices.unapprovedcurrency();

    }

    @GetMapping("/showunapprovedcurrency")
    public String showunapprovedcurrency(){
        System.out.println();
       
      return "dashboarddatatable";

    }
    
    @GetMapping("/showapprovedcurrencyform")
    public String showapprovedcurrencyform( @RequestParam Long id,Model model){
        CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
         List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
         model.addAttribute("Currency",currencydata);
        model.addAttribute("Baggagecurrency",listcurrency);

       
      return "currencyConfirmPage";

    }


    @GetMapping("/confirmgenaral")
    public String showconfirmgenaral( @RequestParam Long id,Model model){
        //  CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
        //  List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
        //  model.addAttribute("Currency",currencydata);
        //  model.addAttribute("Baggagecurrency",listcurrency);

       
      return "fahim";

    }

    

    @GetMapping("/generalform")
    public String fahim(@RequestParam(required = false, defaultValue = "") Long id,Model model){
        //  CurrencyDeclaration currencydata=currencyServices.findcurrency(id);
        //  List<BaggageCurrencyAdd> listcurrency= currencyServices.baggagecurrecylist(id);
        //  model.addAttribute("Currency",currencydata);
        // model.addAttribute("Baggagecurrency",listcurrency);
        return "generalform";
    }


}
