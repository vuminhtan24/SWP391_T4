/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class RepairHistory {
    int id;
    int repairId;
    int bouquetId;
    String bouquetName;
    int oldBatchId;
    private String oldFlowerName;
    int newBatchId; // Có thể null
    String newFlowerName; // Có thể null
    String updatedAt;

    public RepairHistory() {
    }

    public RepairHistory(int id, int repairId, int bouquetId, String bouquetName, int oldBatchId, String oldFlowerName, int newBatchId, String newFlowerName, String updatedAt) {
        this.id = id;
        this.repairId = repairId;
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.oldBatchId = oldBatchId;
        this.oldFlowerName = oldFlowerName;
        this.newBatchId = newBatchId;
        this.newFlowerName = newFlowerName;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public void setBouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
    }

    public String getBouquetName() {
        return bouquetName;
    }

    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }

    public int getOldBatchId() {
        return oldBatchId;
    }

    public void setOldBatchId(int oldBatchId) {
        this.oldBatchId = oldBatchId;
    }

    public String getOldFlowerName() {
        return oldFlowerName;
    }

    public void setOldFlowerName(String oldFlowerName) {
        this.oldFlowerName = oldFlowerName;
    }

    public int getNewBatchId() {
        return newBatchId;
    }

    public void setNewBatchId(int newBatchId) {
        this.newBatchId = newBatchId;
    }

    public String getNewFlowerName() {
        return newFlowerName;
    }

    public void setNewFlowerName(String newFlowerName) {
        this.newFlowerName = newFlowerName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RepairHistory{" + "id=" + id + ", repairId=" + repairId + ", bouquetId=" + bouquetId + ", bouquetName=" + bouquetName + ", oldBatchId=" + oldBatchId + ", oldFlowerName=" + oldFlowerName + ", newBatchId=" + newBatchId + ", newFlowerName=" + newFlowerName + ", updatedAt=" + updatedAt + '}';
    }

    
    
}
