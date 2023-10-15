package com.currency.currency_module.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.currency.currency_module.model.CustomUserDetails;
import com.currency.currency_module.model.UserActivityManagement;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserActivityManagementService userActivityManagementService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from your service or repository
        UserActivityManagement user = userActivityManagementService.findUserWithUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return the user details
        return new CustomUserDetails(user);
    }
}

