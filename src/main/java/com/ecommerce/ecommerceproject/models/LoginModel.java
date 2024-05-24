package com.ecommerce.ecommerceproject.models;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginModel
{

    private static String username;
    private static String password;
    private static String role;
    private static String OTP;

    public LoginModel()
    {
        super();
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        LoginModel.role = role;
    }

    public String getOTP()
    {
        return OTP;
    }

    public void setOTP(String OTP)
    {
        this.OTP = OTP;
    }
}
