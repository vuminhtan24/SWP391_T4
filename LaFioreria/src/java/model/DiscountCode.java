/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;

/**
 *
 * @author VU MINH TAN
 */
public class DiscountCode {
    private int id;
    private String code;
    private String type;
    private BigDecimal value;
    private BigDecimal maxDiscount;
    private BigDecimal minOrderAmount;
    private String description;

    public DiscountCode() {
    }

    public DiscountCode(int id, String code, String type, BigDecimal value, BigDecimal maxDiscount, BigDecimal minOrderAmount, String description) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.value = value;
        this.maxDiscount = maxDiscount;
        this.minOrderAmount = minOrderAmount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
