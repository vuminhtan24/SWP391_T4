/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LAPTOP
 */
public class StatResult {

    private String label;       // Ví dụ: "Tháng 6", "Năm 2025", "Monday"
    private double revenue;     // Tổng doanh thu
    private int orderCount;     // Số đơn hàng

    public StatResult() {
    }

    public StatResult(String label, double revenue, int orderCount) {
        this.label = label;
        this.revenue = revenue;
        this.orderCount = orderCount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
