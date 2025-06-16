/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class FlowerType {
    int flowerId;
    String rawName;
    Warehouse warehouse;
    String image;
    boolean active;

    public FlowerType() {
    }

    public FlowerType(int flowerId, String rawName, Warehouse warehouse, String image, boolean active) {
        this.flowerId = flowerId;
        this.rawName = rawName;
        this.warehouse = warehouse;
        this.image = image;
        this.active = active;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "FlowerType{" + "flowerId=" + flowerId + ", rawName=" + rawName + ", warehouse=" + warehouse + ", image=" + image + ", active=" + active + '}';
    }
    
    
}
