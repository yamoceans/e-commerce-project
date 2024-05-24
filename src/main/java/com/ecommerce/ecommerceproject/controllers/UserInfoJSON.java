package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.models.UserModelJSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class UserInfoJSON
{
    @GetMapping("userInfoJSON")
    @ResponseBody
    public UserModelJSON userInfoJSON(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        int userID = DBUtil.getUserIDFromSession(request);

        System.out.println("userInfoJSON user ID: " + userID);

        for(int i = 0; i < cookies.length; i++)
        {
            if(cookies[i].getName().equals("SESSIONID"))
            {
                return new UserModelJSON(DBUtil.getUsernameWithID(userID), DBUtil.getUserRoleFromID(userID), true);
            }
        }

        return new UserModelJSON(null, null, false);
    }
}
