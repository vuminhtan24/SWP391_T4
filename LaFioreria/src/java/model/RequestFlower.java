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
public class RequestFlower {
    int orderId;
    int orderItemId;
    int flowerId;
    int quantity;
    String status;
    LocalDate requestCreationDate;
    LocalDate requestConfirmationDate;

    public RequestFlower() {
    }

    public RequestFlower(int orderId, int orderItemId, int flowerId, int quantity ,String status, LocalDate requestCreationDate, LocalDate requestConfirmationDate) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.status = status;
        this.requestCreationDate = requestCreationDate;
        this.requestConfirmationDate = requestConfirmationDate;
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

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestCreationDate() {
        return requestCreationDate;
    }

    public void setRequestCreationDate(LocalDate requestCreationDate) {
        this.requestCreationDate = requestCreationDate;
    }

    public LocalDate getRequestConfirmationDate() {
        return requestConfirmationDate;
    }

    public void setRequestConfirmationDate(LocalDate requestConfirmationDate) {
        this.requestConfirmationDate = requestConfirmationDate;
    }

    @Override
    public String toString() {
        return "RequestFlower{" + "orderId=" + orderId + ", orderItemId=" + orderItemId + ", bouquetId=" + flowerId + ", status=" + status + ", requestCreationDate=" + requestCreationDate + ", requestConfirmationDate=" + requestConfirmationDate + '}';
    }
    
    
}
