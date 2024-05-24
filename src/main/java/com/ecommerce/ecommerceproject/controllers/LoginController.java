package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.SecUtil;
import com.ecommerce.ecommerceproject.models.LoginModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController
{
    @GetMapping("/")
    public String displayLoginForm(HttpServletRequest request, Model model)
    {

        if (!SecUtil.isLoginLocked)
        {
            model.addAttribute("loginModel", new LoginModel());
            return "login.html";
        }
        else
        {
            return "loginLocked.html";
        }

    }
    @PostMapping("/TFA")
    public String TFA(LoginModel loginModel, Model model, HttpServletRequest request)
    {
        if(DBUtil.authenticateUser(loginModel.getUsername(), loginModel.getPassword()))
        {
            model.addAttribute("loginModel", loginModel);
            System.out.println("Logged in");
            SecUtil.sendTwoFactorAuth(DBUtil.getUserEmailWithUsername(loginModel.getUsername()));
            System.out.println("UserModel OTP: " + SecUtil.OTP);
            SecUtil.timerOTP();
            return "TFA.html";
        }
        else
        {
            System.out.println("Unable to Log in");
            SecUtil.loginAttemptCounter++;
            model.addAttribute("errorMessage", "Incorrect password or username");

            if(SecUtil.loginAttemptCounter >= 5)
            {
                SecUtil.isLoginLocked = true;
                SecUtil.timerLockedLogin();
                return "loginLocked.html";
            }

            return "login.html";
        }
    }

    @PostMapping("/ProcessLogin")
    public String processLogin(HttpServletResponse response, HttpServletRequest request, LoginModel loginModel, Model model, HttpSession session)
    {
        int seconds = 60;
        int minutes = 30;
        int sessionAge = seconds * minutes;

        if(loginModel.getOTP().equals(SecUtil.OTP))
        {
            System.out.println(loginModel.getUsername());
            Cookie sessionCookie = new Cookie("SESSIONID", DBUtil.createSession(loginModel.getUsername(), minutes));
            sessionCookie.setMaxAge(sessionAge);
            sessionCookie.setPath("/");
            sessionCookie.setSecure(true);
            sessionCookie.setHttpOnly(true);

            response.addCookie(sessionCookie);

            DBUtil.clearExpiredUserSessions(DBUtil.getUserIDFromUsername(loginModel.getUsername()));

            model.addAttribute("loginModel", loginModel);

            System.out.println("Welcome to the Shop");
            SecUtil.clearOTP();

            model.addAttribute("username", loginModel.getUsername());
            model.addAttribute("password", loginModel.getPassword());

            loginModel.setUsername("");
            loginModel.setPassword("");

            return "welcome.html";
        }
        else
        {
            System.out.println("Incorrect OTP");
            return "TFA.html";
        }
    }

    @PostMapping("/TFA=New_Passcode_Sent")
    public String resendOTP(HttpServletRequest request, LoginModel loginModel, Model model)
    {
        SecUtil.sendTwoFactorAuth(DBUtil.getUserEmailWithUsername(loginModel.getUsername()));
        return "TFA.html";
    }

}
