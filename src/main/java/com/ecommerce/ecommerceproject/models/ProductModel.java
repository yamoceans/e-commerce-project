package com.ecommerce.ecommerceproject.models;

import java.sql.Date;

public class ProductModel
{
    public int numOfProducts = 0;
    private int ID;
    private String Name;
    private Date date;
    private float price;
    private int status;
    private int availableAmount;
    private int orderAmount;
    private String materialType;
    private String productType;

    public ProductModel(int ID, String name, Date date, float price, int status, int amount, String materialType, String productType) {
        this.ID = ID;
        Name = name;
        this.date = date;
        this.price = price;
        this.status = status;
        this.availableAmount = amount;
        this.materialType = materialType;
        this.productType = productType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


}
