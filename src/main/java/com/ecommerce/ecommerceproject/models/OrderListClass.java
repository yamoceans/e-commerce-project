package com.ecommerce.ecommerceproject.models;

import com.ecommerce.ecommerceproject.DBUtil;

import java.sql.Date;

public class OrderListClass
{
    private int orderID;
    private int userID;
    private int createdByID;
    private String productList;
    private String firstName;
    private String lastName;
    private String companyName;
    private float totalPayment;
    private String registrationNumber;
    private String bankAccountNumber;
    private String email;
    private String state;
    private String toAddress;
    private String zipCode;
    private Date creationDate;
    private String phoneNumber;
    private String returnAddress;
    private int isActive;



    public OrderListClass(int orderID, int userID, int createdByID, String productList, String firstName,
                          String lastName, String companyName, float totalPayment, String registrationNumber,
                          String bankAccountNumber, String email, String phoneNumber, String state,
                          String toAddress, String zipCode, Date creationDate, String returnAddress, int isActive)
    {
        this.orderID = orderID;
        this.userID = userID;
        this.createdByID = createdByID;
        this.productList = productList;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.totalPayment = totalPayment;
        this.registrationNumber = registrationNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.toAddress = toAddress;
        this.zipCode = zipCode;
        this.creationDate = creationDate;
        this.returnAddress = returnAddress;
        this.isActive = isActive;


    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(int createdByID) {
        this.createdByID = createdByID;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
