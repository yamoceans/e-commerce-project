package com.ecommerce.ecommerceproject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@SpringBootApplication
public class EcommerceProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EcommerceProjectApplication.class, args);
    }
}
