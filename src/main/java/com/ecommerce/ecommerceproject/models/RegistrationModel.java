package com.ecommerce.ecommerceproject.models;

public class RegistrationModel
{

    private String userEmail;

    private String username;

    private String password;
    private String confirmPassword;

    private String companyName;



    @Override
    public String toString()
    {
        return "Registration Model";
    }

    public RegistrationModel()
    {
        super();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
