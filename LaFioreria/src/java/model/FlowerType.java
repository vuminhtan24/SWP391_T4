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
    String flowerName;   
    String image;
    boolean active;

    public FlowerType() {
    }

    public FlowerType(int flowerId, String flowerName, String image, boolean active) {
        this.flowerId = flowerId;
        this.flowerName = flowerName;       
        this.image = image;
        this.active = active;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
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
        return "FlowerType{" + "flowerId=" + flowerId + ", FlowerName=" + flowerName + ", image=" + image + ", active=" + active + '}';
    }
    
    
}
