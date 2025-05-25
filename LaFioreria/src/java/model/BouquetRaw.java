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
    int raw_id;
    int quantity;

    public BouquetRaw() {
    }

    public BouquetRaw(int bouquet_id, int raw_id, int quantity) {
        this.bouquet_id = bouquet_id;
        this.raw_id = raw_id;
        this.quantity = quantity;
    }

    public int getBouquet_id() {
        return bouquet_id;
    }

    public void setBouquet_id(int bouquet_id) {
        this.bouquet_id = bouquet_id;
    }

    public int getRaw_id() {
        return raw_id;
    }

    public void setRaw_id(int raw_id) {
        this.raw_id = raw_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BouquetRaw{" + "bouquet_id=" + bouquet_id + ", raw_id=" + raw_id + ", quantity=" + quantity + '}';
    }
    
}
