/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class WholeSaleFlower {
    int id;
    int wholesale_request_id;
    int bouquet_id;
    int flower_id;
    int flower_ws_price;

    public WholeSaleFlower() {
    }

    public WholeSaleFlower(int id, int wholesale_request_id, int bouquet_id, int flower_id, int flower_ws_price) {
        this.id = id;
        this.wholesale_request_id = wholesale_request_id;
        this.bouquet_id = bouquet_id;
        this.flower_id = flower_id;
        this.flower_ws_price = flower_ws_price;
    }
    
    public WholeSaleFlower(int wholesale_request_id, int bouquet_id, int flower_id, int flower_ws_price) {
        this.wholesale_request_id = wholesale_request_id;
        this.bouquet_id = bouquet_id;
        this.flower_id = flower_id;
        this.flower_ws_price = flower_ws_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWholesale_request_id() {
        return wholesale_request_id;
    }

    public void setWholesale_request_id(int wholesale_request_id) {
        this.wholesale_request_id = wholesale_request_id;
    }

    public int getBouquet_id() {
        return bouquet_id;
    }

    public void setBouquet_id(int bouquet_id) {
        this.bouquet_id = bouquet_id;
    }

    public int getFlower_id() {
        return flower_id;
    }

    public void setFlower_id(int flower_id) {
        this.flower_id = flower_id;
    }

    public int getFlower_ws_price() {
        return flower_ws_price;
    }

    public void setFlower_ws_price(int flower_ws_price) {
        this.flower_ws_price = flower_ws_price;
    }

    @Override
    public String toString() {
        return "WholeSaleFlower{" + "id=" + id + ", wholesale_request_id=" + wholesale_request_id + ", bouquet_id=" + bouquet_id + ", flower_id=" + flower_id + ", flower_ws_price=" + flower_ws_price + '}';
    }
    
    
}
