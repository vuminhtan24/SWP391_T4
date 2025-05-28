/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Warehouse {
    int warehouseId;
    String name;
    String address;
    int managerId;

    public Warehouse() {
    }

    public Warehouse(int warehouseId, String name, String address, int managerId) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.address = address;
        this.managerId = managerId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "warehouseId=" + warehouseId + ", name=" + name + ", address=" + address + ", managerId=" + managerId + '}';
    }
    
    
}
