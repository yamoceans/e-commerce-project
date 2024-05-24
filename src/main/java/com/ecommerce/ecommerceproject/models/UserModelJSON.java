package com.ecommerce.ecommerceproject.models;

public class UserModelJSON
{
    public String username;
    public String role;
    public boolean isLoggedIn;

    public UserModelJSON(String username, String role, boolean isLoggedIn) {
        this.username = username;
        this.role = role;
        this.isLoggedIn = isLoggedIn;
    }

}
