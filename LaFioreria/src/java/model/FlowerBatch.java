/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class FlowerBatch {
    int batchId;
    int flowerId;
    int quantity;
    int unitPrice;
    String importDate;
    String expirationDate;
    int hold;
    Warehouse warehouse;
    String status;
    int realTimeQuantity;

    public FlowerBatch() {
    }

    public FlowerBatch(int batchId, int flowerId, int quantity, int unitPrice, String importDate, String expirationDate, int hold, Warehouse warehouse, String status, int realTimeQuantity) {
        this.batchId = batchId;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.importDate = importDate;
        this.expirationDate = expirationDate;
        this.hold = hold;
        this.warehouse = warehouse;
        this.status = status;
        this.realTimeQuantity = realTimeQuantity;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRealTimeQuantity() {
        return realTimeQuantity;
    }

    public void setRealTimeQuantity(int realTimeQuantity) {
        this.realTimeQuantity = realTimeQuantity;
    }
    
    @Override
    public String toString() {
        return "FlowerBatch{" + "batchId=" + batchId + ", flowerId=" + flowerId + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", importDate=" + importDate + ", expirationDate=" + expirationDate + ", hold=" + hold + ", warehouse=" + warehouse + ", status=" + status + '}';
    }
       
}
