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
    int flowerIdId;
    int quantity;
    int unitPrice;
    String importDate;
    String expirationDate;
    int hold;

    public FlowerBatch() {
    }

    public FlowerBatch(int batchId, int flowerIdId, int quantity, int unitPrice, String importDate, String expirationDate, int hold) {
        this.batchId = batchId;
        this.flowerIdId = flowerIdId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.importDate = importDate;
        this.expirationDate = expirationDate;
        this.hold = hold;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getFlowerIdId() {
        return flowerIdId;
    }

    public void setFlowerIdId(int flowerIdId) {
        this.flowerIdId = flowerIdId;
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

    @Override
    public String toString() {
        return "FlowerBatch{" + "batchId=" + batchId + ", flowerIdId=" + flowerIdId + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", importDate=" + importDate + ", expirationDate=" + expirationDate + ", hold=" + hold + '}';
    }
    
    
}
