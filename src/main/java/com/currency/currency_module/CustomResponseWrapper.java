package com.currency.currency_module;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CustomResponseWrapper extends HttpServletResponseWrapper {

    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    // Implement methods to modify the response as needed
}