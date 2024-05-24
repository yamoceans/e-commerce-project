package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.EmailService;
import com.ecommerce.ecommerceproject.SecUtil;
import com.ecommerce.ecommerceproject.models.LoginModel;
import com.ecommerce.ecommerceproject.models.RegistrationModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/registration")
public class RegistrationController
{

    //String passwordPolicy = SecUtil.setPolicy(true, false, true, true, false, 12, 1, 0, 1);


    @GetMapping("/")
    public String displayRegistration(HttpServletRequest request, Model model)
    {
        model.addAttribute("passwordPolicy", SecUtil.setPolicy(true, true, true, true, false, 12, 1, 1, 1));
        model.addAttribute("registrationModel", new RegistrationModel());
        return "registration.html";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(HttpServletRequest request, RegistrationModel registrationModel, Model model)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String passwordPolicy = SecUtil.setPolicy(true, true, true, true, false, 12, 1, 1, 1);

        //String passwordPolicy = SecUtil.setPolicy(false, false, false, true, false, 2, 0, 0, 0);

        model.addAttribute("registrationModel", registrationModel);

        System.out.println(registrationModel.getPassword());

        if(DBUtil.doesUserExist(registrationModel.getUsername()))
        {
            model.addAttribute("passwordPolicy", passwordPolicy);
            model.addAttribute("errorMessage",
                    "Error: User already exists");
            return "registration.html";
        }
        else if(DBUtil.isEmailTaken(registrationModel.getUserEmail()))
        {
            model.addAttribute("passwordPolicy", passwordPolicy);
            model.addAttribute("errorMessage",
                    "Error: Email is already taken");
            return "registration.html";
        }
        else if(!registrationModel.getPassword().equals(registrationModel.getConfirmPassword()))
        {
            model.addAttribute("passwordPolicy", passwordPolicy);
            model.addAttribute("errorMessage",
                    "Error: Please make sure that password and confirm password match");
            return "registration.html";

        }
        else if(SecUtil.isValidPassword(registrationModel.getPassword()))
        {
            DBUtil.addUser(registrationModel.getUsername(), registrationModel.getPassword(), registrationModel.getUserEmail(), registrationModel.getCompanyName(), "customer");

            EmailService.sendMail("theoceansfamily@gmail.com", "mfydlapaoezabadu", registrationModel.getUserEmail(), "Registration Confirmation",
                    "Welcome to CentCoop-Commerce\n" +
                            "Your account has been successfully created!\n" +
                            "Kind regards\n" +
                            "CentCoop.");


            model.addAttribute("registrationModel", registrationModel);

            return "registrationResult.html";
        }
        else
        {
            model.addAttribute("passwordPolicy", passwordPolicy);
            model.addAttribute("errorMessage",
                    "Error: check your credentials and try again");

            if(DBUtil.useWholeCommonPwd(registrationModel.getPassword()))
            {
                model.addAttribute("errorDatabaseMessage",
                        "Found common password word: " + DBUtil.lastFoundPwd);
            }

            return "registration.html";
        }
    }
}
