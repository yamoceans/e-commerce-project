package com.ecommerce.ecommerceproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AboutController
{
    @GetMapping("about")
    public String displayAbout(HttpServletRequest request, Model model)
    {
        return "about.html";
    }
}

