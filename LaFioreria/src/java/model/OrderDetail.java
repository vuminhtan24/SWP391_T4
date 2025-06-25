/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author VU MINH TAN
 */
public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int bouquetId;    
    private String bouquetName;
    private String bouquetImage; 
    private int quantity;      
    private String unitPrice;
    private int sellPrice;
    private String status;
    
    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, int orderId, int bouquetId, String bouquetName, String bouquetImage, int quantity, String unitPrice) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.bouquetImage = bouquetImage;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(int orderDetailId, int orderId, int bouquetId, String bouquetName, String bouquetImage, int quantity, String unitPrice, int sellPrice, String status) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.bouquetImage = bouquetImage;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.sellPrice = sellPrice;
        this.status = status;
    }    
    
    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public String getBouquetName() {
        return bouquetName;
    }

    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }

    public String getBouquetImage() {
        return bouquetImage;
    }

    public void setBouquetImage(String bouquetImage) {
        this.bouquetImage = bouquetImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailId=" + orderDetailId + ", orderId=" + orderId + ", bouquetId=" + bouquetId + ", bouquetName=" + bouquetName + ", bouquetImage=" + bouquetImage + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}';
    }

    
    
}
