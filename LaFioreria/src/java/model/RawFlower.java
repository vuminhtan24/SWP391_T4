/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;
/**
 *
 * @author Admin
 */
public class RawFlower {
    int rawId;
    String rawName;
    int rawQuantity;
    int unitPrice;
    String expirationDate;
    Warehouse warehouse;
    String imageUrl;
    int hold;
    int importPrice;

     public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        if (hold < 0) {
            this.hold = 0; // Đặt hold bằng 0 nếu hold được đặt âm
        } else if (hold > this.rawQuantity) {
            this.hold = this.rawQuantity; // Đặt hold bằng quantity nếu hold vượt quá
        } else {
            this.hold = hold; // Gán hold nếu hợp lệ
        }
    }

    // Tính toán AvailableQuantity
    public int getAvailableQuantity() {
        return rawQuantity - hold; // Tính số lượng có sẵn
    }

    public int getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }
    
    public RawFlower() {
    }

    public RawFlower(int rawId, String rawName, int rawQuantity, int unitPrice, String expirationDate, Warehouse warehouse, String imageUrl, int hold, int importPrice) {
        this.rawId = rawId;
        this.rawName = rawName;
        this.rawQuantity = rawQuantity;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate;
        this.warehouse = warehouse;
        this.imageUrl = imageUrl;
        this.hold = hold;
        this.importPrice = importPrice;
 
    }


    
    public int getRawId() {
        return rawId;
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public int getRawQuantity() {
        return rawQuantity;
    }

    public void setRawQuantity(int rawQuantity) {
        this.rawQuantity = rawQuantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RawFlower{" + "rawId=" + rawId + ", rawName=" + rawName + ", rawQuantity=" + rawQuantity + ", unitPrice=" + unitPrice + ", expirationDate=" + expirationDate + ", warehouse=" + warehouse + ", imageUrl=" + imageUrl + ", hold=" + hold + ", importPrice=" + importPrice + '}';
    }
    
}
