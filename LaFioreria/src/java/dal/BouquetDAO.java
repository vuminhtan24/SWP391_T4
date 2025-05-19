/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Bouquet;

/**
 *
 * @author ADMIN
 */
public class BouquetDAO extends DBContext{
    public List<Bouquet> getAll(){
        List<Bouquet> listBouquet = new ArrayList<>();
        
        String sql = "SELECT * FROM la_fioreria.bouquet;";
        
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int bouquet_id = rs.getInt("Bouquet_ID");
                String bouquet_name = rs.getString("bouquet_name").trim();
                Date created_at     = rs.getDate("created_at");
                Date expire_date    = rs.getDate("expiration_date");
                int created_by      = rs.getInt("created_by");
                String description  = rs.getString("description").trim();
                String imgurl       = rs.getString("image_url");
                int cid             = rs.getInt("cid");
                Bouquet newBouquet  = new Bouquet(bouquet_id, bouquet_name, created_at, expire_date, created_by, description, imgurl, cid);
                listBouquet.add(newBouquet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
                return listBouquet;
    }
    
    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();
        List<Bouquet> list = dao.getAll();
        
        for (Bouquet bouquet : list) {
            System.out.println(bouquet);
        }
    }
    
}
