/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class BouquetRaw {
    int bouquet_id;
    int batchId;
    int quantity;

    public BouquetRaw() {
    }

    public BouquetRaw(int bouquet_id, int batchId, int quantity) {
        this.bouquet_id = bouquet_id;
        this.batchId = batchId;
        this.quantity = quantity;
    }

    public int getBouquet_id() {
        return bouquet_id;
    }

    public void setBouquet_id(int bouquet_id) {
        this.bouquet_id = bouquet_id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BouquetRaw{" + "bouquet_id=" + bouquet_id + ", raw_id=" + batchId + ", quantity=" + quantity + '}';
    }
    
}
