/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LAPTOP
 */
public class OrderStatusCount {
    private String statusName;
    private int total;

    public OrderStatusCount(String statusName, int total) {
        this.statusName = statusName;
        this.total = total;
    }

    // Getter, Setter

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderStatusCount{" + "statusName=" + statusName + ", total=" + total + '}';
    }
    
    
}
