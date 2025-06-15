/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LAPTOP
 */
public class SalesRecord {

    private int orderID;
    private String orderDate;
    private String customerName;
    private String productName;
    private String categoryName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private String status;

    public SalesRecord() {
    }

    public SalesRecord(int orderID, String orderDate, String customerName, String productName, String categoryName, int quantity, double unitPrice, double totalPrice, String status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.productName = productName;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SalesRecord{" + "orderID=" + orderID + ", orderDate=" + orderDate + ", customerName=" + customerName + ", productName=" + productName + ", categoryName=" + categoryName + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + ", status=" + status + '}';
    }
    
    
}
