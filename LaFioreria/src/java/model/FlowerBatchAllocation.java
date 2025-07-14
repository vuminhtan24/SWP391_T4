/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class FlowerBatchAllocation {

    int allocation_id ;
    int batch_id ;
    int flower_id ;
    int order_id ;
    int quantity ;
    String status ;
    LocalDateTime created_at;
    int cart_id;

    public FlowerBatchAllocation() {
    }

    public FlowerBatchAllocation(int allocation_id, int batch_id, int flower_id, int order_id, int quantity, String status, LocalDateTime created_at, int cart_id) {
        this.allocation_id = allocation_id;
        this.batch_id = batch_id;
        this.flower_id = flower_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.status = status;
        this.created_at = created_at;
        this.cart_id = cart_id;
    }

    public FlowerBatchAllocation(int batch_id, int flower_id, int quantity) {
        this.batch_id = batch_id;
        this.flower_id = flower_id;
        this.quantity = quantity;
    }
    
    public int getAllocation_id() {
        return allocation_id;
    }

    public void setAllocation_id(int allocation_id) {
        this.allocation_id = allocation_id;
    }

    public int getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }

    public int getFlower_id() {
        return flower_id;
    }

    public void setFlower_id(int flower_id) {
        this.flower_id = flower_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    @Override
    public String toString() {
        return "FlowerBatchAllocation{" + "allocation_id=" + allocation_id + ", batch_id=" + batch_id + ", flower_id=" + flower_id + ", order_id=" + order_id + ", quantity=" + quantity + ", status=" + status + ", created_at=" + created_at + ", cart_id=" + cart_id + '}';
    }
    
    
}
