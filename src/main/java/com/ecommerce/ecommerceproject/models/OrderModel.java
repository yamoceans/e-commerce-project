package com.ecommerce.ecommerceproject.models;

import ch.qos.logback.classic.Level;
import com.ecommerce.ecommerceproject.DBUtil;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderModel
{
    private static int orderID;
    private static int customerID;
    private static int createdByID;
    private static int[] productList;
    private static String firstName;
    private static String lastName;
    private static String companyName;
    private static float totalPayment;
    private static String registrationNumber;
    private static String bankAccountNumber;
    private static String email;
    private static String state;
    private static String toAddress;
    private static String zipCode;
    private static Date startDate;
    private static Date completionDate;
    private static String phoneNumber;
    private static String returnAddress;

    private static int softPlasticBagsNum = 0;
    private static int hardPlasticBagsNum = 0;
    private static int aluminiumBagsNum = 0;
    private static int glassBagsNum = 0;
    private static int hardPaperBagsNum = 0;
    private static int softPaperBagsNum = 0;
    private static int softPlasticCompressedNum = 0;
    private static int hardPlasticCompressedNum = 0;
    private static int aluminiumCompressedNum = 0;
    private static int glassCompressedNum = 0;
    private static int hardPaperCompressedNum = 0;
    private static int softPaperCompressedNum = 0;



    public OrderModel()
    {
        if(productList != null)
        {
            productList = new int[DBUtil.getAllProducts().size()];
        }
    }

    public static int getOrderID() {
        return orderID;
    }
    public static void setOrderID(int orderID) {
        OrderModel.orderID = orderID;
    }
    public static int getCustomerID() {
        return customerID;
    }
    public static void setCustomerID(int customerID) {
        OrderModel.customerID = customerID;
    }

    public static int getCreatedByID() {
        return createdByID;
    }

    public static void setCreatedByID(int createdByID) {
        OrderModel.createdByID = createdByID;
    }

    public static int[] getProductList() {
        return productList;
    }

    public static void setProductList(int[] productList) {
        OrderModel.productList = productList;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        OrderModel.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        OrderModel.lastName = lastName;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        OrderModel.companyName = companyName;
    }

    public static float getTotalPayment() {
        return totalPayment;
    }

    public static String getRegistrationNumber() {
        return registrationNumber;
    }

    public static void setRegistrationNumber(String registrationNumber) {
        OrderModel.registrationNumber = registrationNumber;
    }

    public static String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public static void setBankAccountNumber(String bankAccountNumber) {
        OrderModel.bankAccountNumber = bankAccountNumber;
    }

    public static void setTotalPayment(float totalPayment) {
        OrderModel.totalPayment = totalPayment;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        OrderModel.email = email;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        OrderModel.state = state;
    }

    public static String getToAddress() {
        return toAddress;
    }

    public static void setToAddress(String toAddress) {
        OrderModel.toAddress = toAddress;
    }

    public static String getZipCode() {
        return zipCode;
    }

    public static void setZipCode(String zipCode) {
        OrderModel.zipCode = zipCode;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        OrderModel.startDate = startDate;
    }

    public static Date getCompletionDate() {
        return completionDate;
    }

    public static void setCompletionDate(Date completionDate) {
        OrderModel.completionDate = completionDate;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        OrderModel.phoneNumber = phoneNumber;
    }

    public static String getReturnAddress() {
        return returnAddress;
    }

    public static void setReturnAddress(String returnAddress) {
        OrderModel.returnAddress = returnAddress;
    }

    public static int getSoftPlasticBagsNum() {
        return softPlasticBagsNum;
    }

    public static void setSoftPlasticBagsNum(int softPlasticBagsNum) {
        OrderModel.softPlasticBagsNum = softPlasticBagsNum;
    }

    public static int getHardPlasticBagsNum() {
        return hardPlasticBagsNum;
    }

    public static void setHardPlasticBagsNum(int hardPlasticBagsNum) {
        OrderModel.hardPlasticBagsNum = hardPlasticBagsNum;
    }

    public static int getAluminiumBagsNum() {
        return aluminiumBagsNum;
    }

    public static void setAluminiumBagsNum(int aluminiumBagsNum) {
        OrderModel.aluminiumBagsNum = aluminiumBagsNum;
    }

    public static int getGlassBagsNum() {
        return glassBagsNum;
    }

    public static void setGlassBagsNum(int glassBagsNum) {
        OrderModel.glassBagsNum = glassBagsNum;
    }

    public static int getHardPaperBagsNum() {
        return hardPaperBagsNum;
    }

    public static void setHardPaperBagsNum(int hardPaperBagsNum) {
        OrderModel.hardPaperBagsNum = hardPaperBagsNum;
    }

    public static int getSoftPaperBagsNum() {
        return softPaperBagsNum;
    }

    public static void setSoftPaperBagsNum(int softPaperBagsNum) {
        OrderModel.softPaperBagsNum = softPaperBagsNum;
    }

    public static int getSoftPlasticCompressedNum() {
        return softPlasticCompressedNum;
    }

    public static void setSoftPlasticCompressedNum(int softPlasticCompressedNum) {
        OrderModel.softPlasticCompressedNum = softPlasticCompressedNum;
    }

    public static int getHardPlasticCompressedNum() {
        return hardPlasticCompressedNum;
    }

    public static void setHardPlasticCompressedNum(int hardPlasticCompressedNum) {
        OrderModel.hardPlasticCompressedNum = hardPlasticCompressedNum;
    }

    public static int getAluminiumCompressedNum() {
        return aluminiumCompressedNum;
    }

    public static void setAluminiumCompressedNum(int aluminiumCompressedNum) {
        OrderModel.aluminiumCompressedNum = aluminiumCompressedNum;
    }

    public static int getGlassCompressedNum() {
        return glassCompressedNum;
    }

    public static void setGlassCompressedNum(int glassCompressedNum) {
        OrderModel.glassCompressedNum = glassCompressedNum;
    }

    public static int getHardPaperCompressedNum() {
        return hardPaperCompressedNum;
    }

    public static void setHardPaperCompressedNum(int hardPaperCompressedNum) {
        OrderModel.hardPaperCompressedNum = hardPaperCompressedNum;
    }

    public static int getSoftPaperCompressedNum() {
        return softPaperCompressedNum;
    }

    public static void setSoftPaperCompressedNum(int softPaperCompressedNum) {
        OrderModel.softPaperCompressedNum = softPaperCompressedNum;
    }
}
