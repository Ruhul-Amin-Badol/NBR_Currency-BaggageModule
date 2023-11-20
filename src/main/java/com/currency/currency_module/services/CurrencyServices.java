package com.currency.currency_module.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
   public CurrencyDeclaration  currencyInsert(CurrencyDeclaration currencyDeclcuaration){
    // currencyDeclcuaration.setContactNo(" ");
    if(currencyDeclcuaration.getNationality().equals("Other")){
        currencyDeclcuaration.setNationality(currencyDeclcuaration.getOtherNationality());
    }
    currencyDeclcuaration.setStatus("manual");
    return currencyDeclarationRepository.save(currencyDeclcuaration);

   }

    public void currencyUpdate(CurrencyDeclaration updatedCurrencyDeclaration) {
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
        existingCurrencyDeclaration.setFlightNo(updatedCurrencyDeclaration.getFlightNo());
        existingCurrencyDeclaration.setPreviousCountry(updatedCurrencyDeclaration.getPreviousCountry());
        existingCurrencyDeclaration.setStayTimeAbroad(updatedCurrencyDeclaration.getStayTimeAbroad());
        existingCurrencyDeclaration.setStatus("processing");
    
        // Save the updated entity back to the database
        currencyDeclarationRepository.save(existingCurrencyDeclaration);
    }

    public void  approveCurrencyUpdate(CurrencyDeclaration updatedapproveCurrencyDeclaration,String usernameSession) {
      System.out.println("===================================="+updatedapproveCurrencyDeclaration.getId());
        // Retrieve the existing currency declaration by its ID or any unique identifier
        CurrencyDeclaration existingCurrencyDeclaration = currencyDeclarationRepository.findById(updatedapproveCurrencyDeclaration.getId())
                .orElseThrow(() -> new EntityNotFoundException("Currency Declaration not found"));
    
        // Update the properties of the existing entity with the updated data
        existingCurrencyDeclaration.setEntryBy(usernameSession);

        existingCurrencyDeclaration.setConfNote(updatedapproveCurrencyDeclaration.getConfNote());
        existingCurrencyDeclaration.setStatus("checked");
    
        // Save the updated entity back to the database
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
