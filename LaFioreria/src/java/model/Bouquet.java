/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Bouquet {
    int bouquetId;
    String bouquetName;
    Date createdAt;
    Date expirationDate;
    int createdBy;
    String Description;
    String imageUrl;
    int cid;
    int price;

    public Bouquet() {
    }

    public Bouquet(int bouquetId, String bouquetName, Date createdAt, Date expirationDate, int createdBy, String Description, String imageUrl, int cid, int price) {
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        this.createdBy = createdBy;
        this.Description = Description;
        this.imageUrl = imageUrl;
        this.cid      = cid;
        this.price    = price;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Bouquet{" + "bouquetId=" + bouquetId + ", bouquetName=" + bouquetName + ", createdAt=" + createdAt + ", expirationDate=" + expirationDate + ", createdBy=" + createdBy + ", Description=" + Description + ", imageUrl=" + imageUrl + ", cid=" + cid + ", price=" + price + '}';
    }
    
    
    
}
