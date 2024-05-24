package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.EmailService;
import com.ecommerce.ecommerceproject.models.OrderListClass;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/")
public class OrdersController
{
    @GetMapping("orders")
    public String displayOrders(HttpServletRequest request, Model model)
    {
        List<OrderListClass> orders;
        int userID = DBUtil.getUserIDFromSession(request);

        if(DBUtil.getUserRoleFromID(userID).equals("admin"))
        {
            orders = DBUtil.getAllOrders();
            model.addAttribute("orders", orders);
            return "ordersAdmin.html";
        }
        else
        {
            orders = DBUtil.getUserOrders(userID);
            model.addAttribute("orders", orders);
            return "ordersCustomer.html";
        }
    }

    @PostMapping("/showOrdersBy")
    public String displayOrdersBy(@RequestParam(value = "showOrderBy") String showOrderBy, HttpServletRequest request, Model model)
    {
        List<OrderListClass> orders = null;
        int userID = DBUtil.getUserIDFromSession(request);
        StringBuilder sortBuilder = new StringBuilder();
        sortBuilder.append(showOrderBy + "_" + DBUtil.getUserRoleFromID(userID));

        switch(sortBuilder.toString())
        {
            case "all_admin":
                orders = DBUtil.getAllOrders();
                break;
            case "active_admin":
                orders = DBUtil.getAllActiveInactiveOrders(1);
                break;
            case "inactive_admin":
                orders = DBUtil.getAllActiveInactiveOrders(0);
                break;
            case "all_customer":
                orders = DBUtil.getUserOrders(userID);
                break;
            case "active_customer":
                orders = DBUtil.getActiveInactiveUserOrders(userID, 1);
                break;
            case "inactive_customer":
                orders = DBUtil.getActiveInactiveUserOrders(userID, 0);
                break;
        }

        if(DBUtil.getUserRoleFromID(userID).equals("admin"))
        {
            model.addAttribute("orders", orders);
            return "ordersAdmin.html";
        }
        else
        {
            model.addAttribute("orders", orders);
            return "ordersCustomer.html";
        }
    }

    @PostMapping("/finalize")
    public String finalizeOrder(@RequestParam(value = "finalizeOrder") int orderID, HttpServletRequest request, Model model)
    {
        int userID = DBUtil.getUserIDFromOrder(orderID);

        DBUtil.completeOrder(DBUtil.getUserIDFromOrder(orderID), orderID, DBUtil.getTotalPaymentFromOrder(orderID));

        EmailService.sendMail("theoceansfamily@gmail.com", "mfydlapaoezabadu", DBUtil.getUserEmailWithID(userID), "Order Completion",
                "Hello.\n" +
                        "Your order with the ID: " + orderID + " has been completed\n" +
                        "Given payment from order: " + DBUtil.getTotalPaymentFromOrder(orderID) + "\n" +
                        "Total amount given ever to user: " + DBUtil.getUserTotalGivenPaymentWithID(userID) + "\n" +
                        "Continue a pleasant day.\n" +
                        "Kind regards\n" +
                        "CentCoop.");

        List<OrderListClass> orders = DBUtil.getAllOrders();
        model.addAttribute("orders", orders);

        System.out.println("OrderID: " + orderID);
        System.out.println("Order was finalized");
        return "ordersAdmin.html";
    }

}
