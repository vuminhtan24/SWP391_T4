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
public class RequestDisplay {
     int orderId;
     int orderItemId;
     String flowerNames;
     LocalDate requestDate;
     LocalDate confirmDate;
     String status;

    public RequestDisplay() {
    }

    public RequestDisplay(int orderId, int orderItemId, String flowerNames, LocalDate requestDate, LocalDate confirmDate, String status) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.flowerNames = flowerNames;
        this.requestDate = requestDate;
        this.confirmDate = confirmDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getFlowerNames() {
        return flowerNames;
    }

    public void setFlowerNames(String flowerNames) {
        this.flowerNames = flowerNames;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestDisplay{" + "orderId=" + orderId + ", orderItemId=" + orderItemId + ", flowerNames=" + flowerNames + ", requestDate=" + requestDate + ", confirmDate=" + confirmDate + ", status=" + status + '}';
    }
    
    
}
