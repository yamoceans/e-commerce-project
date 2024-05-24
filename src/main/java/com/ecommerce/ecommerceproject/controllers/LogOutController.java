package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.models.LoginModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogOutController
{
    @GetMapping("logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response, Model model)
    {

        Cookie[] cookies = request.getCookies();

        for(int i = 0; i < cookies.length; i++)
        {
            if(cookies[i].getName().equals("SESSIONID"))
            {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
                System.out.println("Session: " + cookies[i].getValue());
                DBUtil.removeCurrentSession(cookies[i].getValue());
            }
        }

        return "logout.html";
    }

}
