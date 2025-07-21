/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class CartWholeSaleDetail {
    Integer cartWholeSaleID;
    Integer userID;
    Integer bouquetID;
    Integer quantity;
    Integer pricePerUnit;
    Integer totalValue;
    Integer expense;
    
    public CartWholeSaleDetail() {
    }

    public CartWholeSaleDetail(Integer cartWholeSaleID, Integer userID, Integer bouquetID, Integer quantity, Integer pricePerUnit, Integer totalValue, Integer expense) {
        this.cartWholeSaleID = cartWholeSaleID;
        this.userID = userID;
        this.bouquetID = bouquetID;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalValue = totalValue;
        this.expense = expense;
    }


    public Integer getCartWholeSaleID() {
        return cartWholeSaleID;
    }

    public void setCartWholeSaleID(Integer cartWholeSaleID) {
        this.cartWholeSaleID = cartWholeSaleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getBouquetID() {
        return bouquetID;
    }

    public void setBouquetID(Integer bouquetID) {
        this.bouquetID = bouquetID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return "CartWholeSaleDetail{" + "cartWholeSaleID=" + cartWholeSaleID + ", userID=" + userID + ", bouquetID=" + bouquetID + ", quantity=" + quantity + ", pricePerUnit=" + pricePerUnit + ", totalValue=" + totalValue + ", expense=" + expense + '}';
    }
    
}
