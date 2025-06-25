/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class RepairOrders {
    int repairId;
    int bouquetId;
    int batchId;
    String reason;
    String createdAt;
    String status;

    public RepairOrders() {
    }

    public RepairOrders(int repairId, int bouquetId, int batchId, String reason, String createdAt, String status) {
        this.repairId = repairId;
        this.bouquetId = bouquetId;
        this.batchId = batchId;
        this.reason = reason;
        this.createdAt = createdAt;
        this.status = status;
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

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RepairOrders{" + "repairId=" + repairId + ", bouquetId=" + bouquetId + ", batchId=" + batchId + ", reason=" + reason + ", createdAt=" + createdAt + ", status=" + status + '}';
    }
        
}
