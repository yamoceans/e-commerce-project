package com.ecommerce.ecommerceproject.controllers;

import com.ecommerce.ecommerceproject.DBUtil;
import com.ecommerce.ecommerceproject.EmailService;
import com.ecommerce.ecommerceproject.models.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController
{

    @GetMapping("/")
    public String displayShop(HttpServletRequest request, Model model)
    {
        List<ProductModel> products = DBUtil.getAllProducts();

        String[] productVariableNames = {"softPlasticBagsInfo", "hardPlasticBagsInfo"
                , "aluminiumBagsInfo", "glassBagsInfo", "softPaperBagsInfo", "hardPaperBagsInfo"
                , "softPlasticCompressedInfo", "hardPlasticCompressedInfo", "aluminiumCompressedInfo"
                , "glassCompressedInfo", "softPaperCompressedInfo", "hardPaperCompressedInfo"};


        for(int i = 0; i < products.size(); i++)
        {
            model.addAttribute(productVariableNames[i],
                    "Name: " + products.get(i).getName() + ", Product Type: " + products.get(i).getProductType() +
                            ", Material Type: " + products.get(i).getMaterialType() + ", Price per kilo: " + products.get(i).getPrice());
        }

        model.addAttribute("productListModel", new ProductListModel());

        System.out.println("Flag 1: " + ProductListModel.getSoftPlasticBagsNum());

        return "shop.html";
    }

    @PostMapping("/orderForm")
    public String displayOrderForm(Model model, OrderModel orderModel, ProductListModel productListModel)
    {
        float totalPrice = 0;

        System.out.println(ProductListModel.getSoftPlasticBagsNum());

        //NOTE: We managed to obtain the values for productList, as the values in ProductListModel are all static, meaning that when the user submits,
        // the values are stored in the static variables, and the object given disappears.
        int[] productList = {ProductListModel.getSoftPlasticBagsNum(), ProductListModel.getHardPlasticBagsNum(),
                ProductListModel.getAluminiumBagsNum(), ProductListModel.getGlassBagsNum(),
                ProductListModel.getSoftPaperBagsNum(), ProductListModel.getHardPaperBagsNum(),
                ProductListModel.getSoftPlasticCompressedNum(), ProductListModel.getHardPlasticCompressedNum(),
                ProductListModel.getAluminiumCompressedNum(), ProductListModel.getGlassCompressedNum(),
                ProductListModel.getSoftPaperCompressedNum(),
                ProductListModel.getHardPaperCompressedNum()};

        List<ProductModel> products = DBUtil.getAllProducts();

        model.addAttribute("orderModel", orderModel);

        OrderModel.setProductList(productList);

        for(int i = 0; i < products.size() - 1; i++)
        {
            totalPrice += products.get(i).getPrice() * productList[i];
        }

        OrderModel.setTotalPayment(totalPrice);

        return "orderForm.html";
    }

    @PostMapping("/orderCreated")
    public String completeOrderCreation(HttpServletRequest request, Model model, OrderModel orderModel) throws NoSuchAlgorithmException, InvalidKeySpecException {

        int[] productList = {ProductListModel.getSoftPlasticBagsNum(), ProductListModel.getHardPlasticBagsNum(),
                    ProductListModel.getAluminiumBagsNum(), ProductListModel.getGlassBagsNum(),
                    ProductListModel.getSoftPaperBagsNum(), ProductListModel.getHardPaperBagsNum(),
                    ProductListModel.getSoftPlasticCompressedNum(), ProductListModel.getHardPlasticCompressedNum(),
                    ProductListModel.getAluminiumCompressedNum(), ProductListModel.getGlassCompressedNum(),
                    ProductListModel.getSoftPaperCompressedNum(),
                    ProductListModel.getHardPaperCompressedNum()};

        StringBuilder productListStringBuilder = new StringBuilder();
        List<ProductModel> products = DBUtil.getAllProducts();
        model.addAttribute("orderModel", orderModel);
        int userID = DBUtil.getUserIDFromSession(request);

        OrderModel.setCreatedByID(0);

        System.out.println("Customer ID: " + DBUtil.getUserIDFromSession(request));
        System.out.println("Created by ID: " + OrderModel.getCreatedByID());

        for(int i = 0; i < products.size(); i++)
        {
            System.out.println(products.get(i).getName() + ": " + OrderModel.getProductList()[i]);
        }

        System.out.println("First name: " + OrderModel.getFirstName());
        System.out.println("Last name: " + OrderModel.getLastName());
        System.out.println("Company name: " + OrderModel.getCompanyName());
        System.out.println("Total Payment: " + OrderModel.getTotalPayment());
        System.out.println("Registration Number: " + OrderModel.getRegistrationNumber());
        System.out.println("Bank Account Number: " + OrderModel.getBankAccountNumber());
        System.out.println("Email: " + OrderModel.getEmail());
        System.out.println("Phone Number: " + OrderModel.getPhoneNumber());
        System.out.println("To Address: " + OrderModel.getToAddress());
        System.out.println("State: " + OrderModel.getState());
        System.out.println("Zip Code: " + OrderModel.getZipCode());
        System.out.println("Start Date: " + OrderModel.getStartDate());
        System.out.println("Completion Date: " + OrderModel.getCompletionDate());
        System.out.println("Return Address: " + OrderModel.getReturnAddress());

        for(int i = 0; i < products.size(); i++)
        {
            productListStringBuilder.append(productList[i] + ", ");
        }
        System.out.println("Product List: " + productListStringBuilder.toString());

        DBUtil.addOrder(DBUtil.getUserIDFromSession(request), 0, productListStringBuilder.toString(),
               OrderModel.getFirstName(), OrderModel.getLastName(), OrderModel.getCompanyName(),
               OrderModel.getTotalPayment(), OrderModel.getRegistrationNumber(), OrderModel.getBankAccountNumber(), OrderModel.getEmail(), OrderModel.getPhoneNumber(),
               OrderModel.getState(), OrderModel.getToAddress(), OrderModel.getZipCode(),
               Date.valueOf(java.time.LocalDate.now()), "221B Baker Street", 1);


        return "orderCompletion.html";
    }
}
