package com.currency.currency_module.services;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.BaggageCurrencyAdd;
import com.currency.currency_module.model.CurrencyDeclaration;
import com.currency.currency_module.repository.CurrencyAddRepository;
import com.currency.currency_module.repository.CurrencyDeclarationRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CurrencyServices {
   @Autowired 
   CurrencyDeclarationRepository currencyDeclarationRepository;
   @Autowired
   CurrencyAddRepository currencyAddRepository;

  //   @Autowired
  //  private JavaMailSender mailSender;
    @Autowired
    private PdfGenerationService pdfGenerationService;

    @Autowired
    private EmailService emailService;
    
   public CurrencyDeclaration  currencyInsert(CurrencyDeclaration currencyDeclcuaration){
    // currencyDeclcuaration.setContactNo(" ");
    if(currencyDeclcuaration.getNationality().equals("Other")){
        currencyDeclcuaration.setNationality(currencyDeclcuaration.getOtherNationality());
    }
    currencyDeclcuaration.setStatus("manual");
    return currencyDeclarationRepository.save(currencyDeclcuaration);

   }

    public void currencyUpdate(CurrencyDeclaration updatedCurrencyDeclaration,String airportName) {
      System.out.println(updatedCurrencyDeclaration.getId());
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data
         if(updatedCurrencyDeclaration.getNationality().equals("Other")){
        updatedCurrencyDeclaration.setNationality(updatedCurrencyDeclaration.getOtherNationality());
           }
        existingCurrencyDeclaration.setPassengerName(updatedCurrencyDeclaration.getPassengerName());
        existingCurrencyDeclaration.setPassportNumber(updatedCurrencyDeclaration.getPassportNumber());
        existingCurrencyDeclaration.setPassportIssueDate(updatedCurrencyDeclaration.getPassportIssueDate());
        existingCurrencyDeclaration.setPassportIssuePlace(updatedCurrencyDeclaration.getPassportIssuePlace());
        existingCurrencyDeclaration.setNationality(updatedCurrencyDeclaration.getNationality());
        existingCurrencyDeclaration.setProfession(updatedCurrencyDeclaration.getProfession());
        existingCurrencyDeclaration.setContactNo(updatedCurrencyDeclaration.getContactNo());
        existingCurrencyDeclaration.setEmail(updatedCurrencyDeclaration.getEmail());
        existingCurrencyDeclaration.setAddressInBangladesh(updatedCurrencyDeclaration.getAddressInBangladesh());
        existingCurrencyDeclaration.setDateOfArrival(updatedCurrencyDeclaration.getDateOfArrival());
        existingCurrencyDeclaration.setOtherProfession(updatedCurrencyDeclaration.getOtherProfession());
        existingCurrencyDeclaration.setFlightNo(updatedCurrencyDeclaration.getFlightNo());
        existingCurrencyDeclaration.setPreviousCountry(updatedCurrencyDeclaration.getPreviousCountry());
        existingCurrencyDeclaration.setStayTimeAbroad(updatedCurrencyDeclaration.getStayTimeAbroad());
        existingCurrencyDeclaration.setOfficeCode(updatedCurrencyDeclaration.getOfficeCode());
        existingCurrencyDeclaration.setEntryPoint(airportName);
        existingCurrencyDeclaration.setStatus("processing");
    
        // Save the updated entity back to the database
        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }


    //for final submit   @PostMapping("/finalsubmit")
    public void currencyUpdate(CurrencyDeclaration updatedCurrencyDeclaration) {
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data
         if(updatedCurrencyDeclaration.getNationality().equals("Other")){
        updatedCurrencyDeclaration.setNationality(updatedCurrencyDeclaration.getOtherNationality());
           }
        existingCurrencyDeclaration.setPassengerName(updatedCurrencyDeclaration.getPassengerName());
        existingCurrencyDeclaration.setPassportNumber(updatedCurrencyDeclaration.getPassportNumber());
        existingCurrencyDeclaration.setPassportIssueDate(updatedCurrencyDeclaration.getPassportIssueDate());
        existingCurrencyDeclaration.setPassportIssuePlace(updatedCurrencyDeclaration.getPassportIssuePlace());
        existingCurrencyDeclaration.setNationality(updatedCurrencyDeclaration.getNationality());
        existingCurrencyDeclaration.setProfession(updatedCurrencyDeclaration.getProfession());
        existingCurrencyDeclaration.setContactNo(updatedCurrencyDeclaration.getContactNo());
        existingCurrencyDeclaration.setEmail(updatedCurrencyDeclaration.getEmail());
        existingCurrencyDeclaration.setAddressInBangladesh(updatedCurrencyDeclaration.getAddressInBangladesh());
        existingCurrencyDeclaration.setDateOfArrival(updatedCurrencyDeclaration.getDateOfArrival());
        existingCurrencyDeclaration.setOtherProfession(updatedCurrencyDeclaration.getOtherProfession());
        existingCurrencyDeclaration.setFlightNo(updatedCurrencyDeclaration.getFlightNo());
        existingCurrencyDeclaration.setPreviousCountry(updatedCurrencyDeclaration.getPreviousCountry());
        existingCurrencyDeclaration.setStayTimeAbroad(updatedCurrencyDeclaration.getStayTimeAbroad());
        existingCurrencyDeclaration.setOfficeCode(updatedCurrencyDeclaration.getOfficeCode());
        // existingCurrencyDeclaration.setEntryPoint(airportName);
        existingCurrencyDeclaration.setStatus("processing");
    
        // Save the updated entity back to the database
        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }


    

    //for For update invoice and status   @PostMapping("/currencystart/currencyConfirmInvoice")

    public void currencyStatusInvoiceUpdate(CurrencyDeclaration updatedCurrencyDeclaration,String invoiceId) {
      System.out.println(updatedCurrencyDeclaration.getId());
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data
         if(updatedCurrencyDeclaration.getNationality().equals("Other")){
        updatedCurrencyDeclaration.setNationality(updatedCurrencyDeclaration.getOtherNationality());
           }
        existingCurrencyDeclaration.setInvoice(invoiceId);
        existingCurrencyDeclaration.setStatus("processing");
    
        // Save the updated entity back to the database
        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }




    
    
    public void  approveCurrencyUpdate(CurrencyDeclaration updatedapproveCurrencyDeclaration,String usernameSession,MultipartFile pdffile) throws IOException {
      System.out.println("===================================="+updatedapproveCurrencyDeclaration.getId());
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedapproveCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data

        // Long currencyId = updatedapproveCurrencyDeclaration.getId();
        // SimpleDateFormat dateFormatForInvoice = new SimpleDateFormat("ddMMYY");
        // String invoiceDate = dateFormatForInvoice.format(new Date());
  
        // String autoincrementIdAsString = String.format("%07d", currencyId);
        // String invoiceId = invoiceDate + autoincrementIdAsString;
        // existingCurrencyDeclaration.setInvoice(invoiceId);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
        String approveDate = dateFormat.format(new Date());


        existingCurrencyDeclaration.setEntryBy(usernameSession);

        existingCurrencyDeclaration.setConfNote(updatedapproveCurrencyDeclaration.getConfNote());
        existingCurrencyDeclaration.setStatus("checked");
        existingCurrencyDeclaration.setApproveDate(approveDate);


    
        // Save the updated entity back to the database
       
      //  List<BaggageCurrencyAdd> baggagecurrecylist=currencyAddRepository.findAllByCurrencyId(existingCurrencyDeclaration.getId());
      
      //  byte[] pdfData = pdfGenerationService.generatePdfCurrency(existingCurrencyDeclaration,baggagecurrecylist,usernameSession);
                   byte[] pdfData = pdffile.getBytes();
                     HttpHeaders headers = new HttpHeaders();
                 headers.setContentType(MediaType.APPLICATION_PDF);
                 headers.setContentDispositionFormData("inline", "NBR_Currency_declaration.pdf");

                 emailService.sendEmailWithAttachment(existingCurrencyDeclaration.getEmail(), "NBR Currency Declaration", "Body", pdfData, "nbr_Currency_application.pdf");

        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }//

    public void unapproveCurrencyUpdate(CurrencyDeclaration updatedUnapproveCurrencyDeclaration,String usernameSession) {
      System.out.println("===================================="+updatedUnapproveCurrencyDeclaration.getId());
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedUnapproveCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data
        
        existingCurrencyDeclaration.setConfNote(updatedUnapproveCurrencyDeclaration.getConfNote());
        existingCurrencyDeclaration.setEntryBy(usernameSession);
        existingCurrencyDeclaration.setStatus("rejected");
    
        // Save the updated entity back to the database
        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }
    
    public BaggageCurrencyAdd addCurrency(BaggageCurrencyAdd addCurrency) {
      return currencyAddRepository.save(addCurrency);
    }

   public List<BaggageCurrencyAdd> baggagecurrecylist(Long id) {
    
      return currencyAddRepository.findAllByCurrencyId(id);
   }

   public Optional<CurrencyDeclaration> currencyDeclarationById(Long id) {
    
      return currencyDeclarationRepository.findById(id);
   }


  public List<CurrencyDeclaration> unapprovedcurrency() {
    return currencyDeclarationRepository.findByStatus("unchecked");
  }

public CurrencyDeclaration findcurrency(Long id) {
  return currencyDeclarationRepository.findById(id).orElseThrow(()->new ResourceNotFound("User not found"));
}
// @Transactional
// public void updateStatusToProcessing(Long id) {
//   // Fetch the entity by its ID
//   CurrencyDeclaration currency = currencyDeclarationRepository.findById(id).orElse(null);

//   if (currency != null) {
//     // Update the "status" column value to "processing"
//     currency.setStatus("processing");
//     currencyDeclarationRepository.save(currency);
//   }
// }

// @Transactional
// public void updateCurrencyStatusToUnchecked(Long id) {
//     CurrencyDeclaration currency = currencyDeclarationRepository.findById(id).orElse(null);

//     if (currency != null) {
//         // Update the "status" column value to "unchecked"
//         currency.setStatus("unchecked");
//         currencyDeclarationRepository.save(currency);
// }
// }

}
