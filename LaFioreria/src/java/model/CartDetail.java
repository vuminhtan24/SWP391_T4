/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dal.BouquetDAO;
import dal.CartDAO;

/**
 *
 * @author Legion
 */
public class CartDetail {

    private int cartId;
    private int customerId;
    private int bouquetId;
    private int quantity;
    private Bouquet bouquet;

    public CartDetail() {
    }

    public CartDetail(int cartId, int customerId, int bouquetId, int quantity) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.bouquetId = bouquetId;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getCartId() {
        return cartId;
    }

    public Bouquet getBouquet() {
        return this.bouquet;
    }

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "CartDetail{" + "cartId=" + cartId + ", customerId=" + customerId + ", bouquetId=" + bouquetId + ", quantity=" + quantity + ", bouquet=" + bouquet + '}';
    }

}
