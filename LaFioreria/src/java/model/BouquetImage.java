/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class BouquetImage {
    int bouquetId;
    String image_url;

    public BouquetImage() {
    }
    
        
    public BouquetImage(int bouquetId, String image_url) {
        this.bouquetId = bouquetId;
        this.image_url = image_url;
    }

    public int getbouquetId() {
        return bouquetId;
    }

    public void setbouquetId(int bouquetId) {
        this.bouquetId = bouquetId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "BouquetImage{" + "bouquetId=" + bouquetId + ", image_url=" + image_url + '}';
    }
       
}
