/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Legion
 */
public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int bouquetId;
    private int quantity;
    private double unitPrice;
    private double sellPrice;
    private String status;
    private String request_group_id;
    
    // Constructors, Getters & Setters
    public OrderItem() {
    }

    public OrderItem(int orderId, int bouquetId, int quantity, double unitPrice, double sellPrice) {
        this.orderId = orderId;
        this.bouquetId = bouquetId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.sellPrice = sellPrice;
    }

    public OrderItem(int orderItemId, int orderId, int bouquetId, int quantity, double unitPrice, double sellPrice, String status, String request_group_id) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.bouquetId = bouquetId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.sellPrice = sellPrice;
        this.status = status;
        this.request_group_id = request_group_id;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getRequest_group_id() {
        return request_group_id;
    }

    public void setRequest_group_id(String request_group_id) {
        this.request_group_id = request_group_id;
    }   

    @Override
    public String toString() {
        return "OrderItem{" + "orderItemId=" + orderItemId + ", orderId=" + orderId + ", bouquetId=" + bouquetId + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", sellPrice=" + sellPrice + ", status=" + status + ", request_group_id=" + request_group_id + '}';
    }
    
    
}
