package com.currency.currency_module.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
@Configuration
public class FirbaseCongig {

    public FirbaseCongig() {
      
        try {
      // Initialize Firebase Admin SDK
      InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("static/apikeyfirbase.json");

      if (serviceAccount == null) {
          throw new IllegalArgumentException("Firebase credentials file not found.");
      }

      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setStorageBucket("nbrbd-47f44.appspot.com")  // Set this to your Firebase Storage bucket URL
              .build();

      FirebaseApp.initializeApp(options);
  } catch (IOException e) {
      e.printStackTrace();
  } catch (Exception e) {
      e.printStackTrace();
  }

    }
    
}
