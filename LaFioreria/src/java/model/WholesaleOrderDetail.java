/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class WholesaleOrderDetail {

     private int orderId;
    private int customerId;
    private LocalDate orderDate;
    private int orderItemId;
    private int bouquetId;

    private int bouquetExpense;         
    private int bouquetSellPrice;       

    private int flowerId;
    private int flowerWholesalePrice;   

    private int flowerQuantityInBouquet; 
    private int totalFlowerQuantity;
    // Getters & Setters

    public WholesaleOrderDetail() {
    }

    public WholesaleOrderDetail(int orderId, int customerId, LocalDate orderDate, int orderItemId, int bouquetId, int bouquetExpense, int bouquetSellPrice, int flowerId, int flowerWholesalePrice, int flowerQuantityInBouquet, int totalFlowerQuantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderItemId = orderItemId;
        this.bouquetId = bouquetId;
        this.bouquetExpense = bouquetExpense;
        this.bouquetSellPrice = bouquetSellPrice;
        this.flowerId = flowerId;
        this.flowerWholesalePrice = flowerWholesalePrice;
        this.flowerQuantityInBouquet = flowerQuantityInBouquet;
        this.totalFlowerQuantity = totalFlowerQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
    }

    public int getBouquetExpense() {
        return bouquetExpense;
    }

    public void setBouquetExpense(int bouquetExpense) {
        this.bouquetExpense = bouquetExpense;
    }

    public int getBouquetSellPrice() {
        return bouquetSellPrice;
    }

    public void setBouquetSellPrice(int bouquetSellPrice) {
        this.bouquetSellPrice = bouquetSellPrice;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public int getFlowerWholesalePrice() {
        return flowerWholesalePrice;
    }

    public void setFlowerWholesalePrice(int flowerWholesalePrice) {
        this.flowerWholesalePrice = flowerWholesalePrice;
    }

    public int getFlowerQuantityInBouquet() {
        return flowerQuantityInBouquet;
    }

    public void setFlowerQuantityInBouquet(int flowerQuantityInBouquet) {
        this.flowerQuantityInBouquet = flowerQuantityInBouquet;
    }

    public int getTotalFlowerQuantity() {
        return totalFlowerQuantity;
    }

    public void setTotalFlowerQuantity(int totalFlowerQuantity) {
        this.totalFlowerQuantity = totalFlowerQuantity;
    }

    @Override
    public String toString() {
        return "WholesaleOrderDetail{" + "orderId=" + orderId + ", customerId=" + customerId + ", orderDate=" + orderDate + ", orderItemId=" + orderItemId + ", bouquetId=" + bouquetId + ", bouquetExpense=" + bouquetExpense + ", bouquetSellPrice=" + bouquetSellPrice + ", flowerId=" + flowerId + ", flowerWholesalePrice=" + flowerWholesalePrice + ", flowerQuantityInBouquet=" + flowerQuantityInBouquet + ", totalFlowerQuantity=" + totalFlowerQuantity + '}';
    }
    
    
    
}
