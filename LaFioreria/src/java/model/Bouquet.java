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
    String imageUrl;

    public Bouquet() {
    }

    public Bouquet(int bouquetId, String bouquetName, String Description, int cid, int price) {
        this.bouquetId = bouquetId;
        this.bouquetName = bouquetName;
        this.Description = Description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Bouquet{" + "bouquetId=" + bouquetId + ", bouquetName=" + bouquetName + ", Description=" + Description + ", cid=" + cid + ", price=" + price + ", imageUrl=" + imageUrl + '}';
    }
}
