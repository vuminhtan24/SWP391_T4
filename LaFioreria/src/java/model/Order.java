/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Legion
 */
public class Order {
    private int orderId;
    private String orderDate;
    private int customerId;
    private String customerName;
    private String totalAmount;
    private int statusId;
    private String statusName;   
    private Integer shipperId; 
    private String shipperName;  

    // Constructors
    public Order() {
    }

    public Order(int orderId, String orderDate, int customerId, String customerName,
                 String totalAmount, int statusId, String statusName,
                 Integer shipperId, String shipperName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.statusId = statusId;
        this.statusName = statusName;
        this.shipperId = shipperId;
        this.shipperName = shipperName;
    }

    public Order(int orderId, String orderDate, int customerId, String totalAmount, int statusId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.statusId = statusId;
    }


    // Getters and Setters for existing fields
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    // Getters and Setters for new fields
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getShipperId() { // Use Integer to allow null
        return shipperId;
    }

    public void setShipperId(Integer shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    @Override
    public String toString() {
        return "Order{" +
               "orderId=" + orderId +
               ", orderDate='" + orderDate + '\'' +
               ", customerId=" + customerId +
               ", customerName='" + customerName + '\'' +
               ", totalAmount='" + totalAmount + '\'' +
               ", statusId=" + statusId +
               ", statusName='" + statusName + '\'' +
               ", shipperId=" + shipperId +
               ", shipperName='" + shipperName + '\'' +
               '}';
    }
}
