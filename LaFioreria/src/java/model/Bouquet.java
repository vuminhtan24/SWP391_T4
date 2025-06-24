    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Bouquet {
    int bouquetId;
    String bouquetName;
    String Description;
    int cid;
    int price;
    int sellPrice;
    String status;
    
    public Bouquet() {
    }

    public Bouquet(int bouquetId, String bouquetName, String Description, int cid, int price, int sellPrice) {
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.Description = Description;
        this.cid      = cid;
        this.price    = price;
        this.sellPrice = sellPrice;
    }
    
    public Bouquet(int bouquetId, String bouquetName, String Description, int cid, int price, int sellPrice, String status) {
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.Description = Description;
        this.cid      = cid;
        this.price    = price;
        this.sellPrice = sellPrice;
        this.status = status;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bouquet{" + "bouquetId=" + bouquetId + ", bouquetName=" + bouquetName + ", Description=" + Description + ", cid=" + cid + ", price=" + price + ", sellPrice=" + sellPrice + ", status=" + status +'}';
    }
  
}
