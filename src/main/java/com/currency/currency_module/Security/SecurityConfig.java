package com.currency.currency_module.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired 
    UserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use NoOpPasswordEncoder to store and compare passwords in plain text
        return NoOpPasswordEncoder.getInstance();
    }



      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
      }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //      AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    // authenticationManagerBuilder.userDetailsService(userDetailsSe*rvice);
    // AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

                    httpSecurity.csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/index1").permitAll()
                    // .requestMatchers(HttpMethod.POST, "/baggagestart/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/valueStay").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/delete").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/productDelete").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/getProductData").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/show").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/baggageInsert").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/productInfo").permitAll()
                    .requestMatchers(HttpMethod.POST, "baggagestart/finalsubmit").permitAll()
                    .requestMatchers(HttpMethod.GET, "baggagestart/finalsubmit").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/confrimPage").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/baggageUpdate").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/finalsubmitadmin").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/baggageRule").permitAll()
                    
                     

                    .requestMatchers(HttpMethod.GET, "/currencystart/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/home").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/currencyUpdate").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/currencyformStay").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/finalsubmiform").permitAll()
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(HttpMethod.GET, "/profile").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/finalsubmit").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/showapprovedcurrencyform").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/showapprovedcurrencyform").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggageshow/baggagetotal").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/unapprovedcurrency").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/show").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/currencyinsert").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/delete").permitAll()
                    .requestMatchers(HttpMethod.GET, "/currencystart/showconfirmgenaral").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/confirmgenaral").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggageshow/baggagetotalid").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggageshow/baggageApplicationEdit").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggageshow/baggageApplicationShow").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/confrimPage").permitAll()
                    .requestMatchers(HttpMethod.POST, "/baggagestart/makePaymentRequest/{id}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/makePaymentRequest2/{accessToken}").permitAll()
                    .requestMatchers(HttpMethod.GET, "/baggagestart/takePaymentRequest/{id}/*").permitAll()


                    .requestMatchers(HttpMethod.POST, "/usermatrix/userinsert").permitAll()
                    .requestMatchers(HttpMethod.GET, "/usermatrix/rollcreate").permitAll()


                  

                    .requestMatchers(HttpMethod.POST, "/currencystart/addCurrency").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/finalsubmit").permitAll()

                    .requestMatchers(HttpMethod.GET, "/currencystart/currencyEdit").permitAll()
                    .requestMatchers(HttpMethod.POST, "/currencystart/showunapprovedcurrency").permitAll()
                    .requestMatchers("/img/**", "/css/**", "/js/**", "/WEB-INF/views/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/dashboard", true)
                    .permitAll()
                    .and()
                    .rememberMe();
                   

                    
                return httpSecurity.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService(){

    //     UserDetails ramesh = User.builder()
    //             .username("Fahim")
    //             .password(passwordEncoder().encode("1234"))
    //             .roles("USER")
    //             .build();

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("admin"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(ramesh, admin);
    // }
}