package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.models.UserModelJSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home")
public class HomeController
{
    @GetMapping("/")
    public String displayHome(HttpServletRequest request, Model model)
    {
        return "index.html";
    }
}
