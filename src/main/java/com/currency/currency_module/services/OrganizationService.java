package com.currency.currency_module.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;

import com.currency.currency_module.repository.OrganizationRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.currency.currency_module.ResourceNotFound.ResourceNotFound;
import com.currency.currency_module.model.OrganizationList;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;




@Service
public class OrganizationService {
    @Autowired 
    OrganizationRepository organizationRepository;
    
    public List<OrganizationList> getAllOrganization() {
         return organizationRepository.findAll();
     }


    public OrganizationList createOrganization(OrganizationList organizationList) {
        return organizationRepository.save(organizationList);
    }

}
