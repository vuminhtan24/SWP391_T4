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
public class BouquetDAO extends DBContext {

    public List<Bouquet> getAll() {
        List<Bouquet> listBouquet = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.bouquet";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int bouquet_id = rs.getInt("Bouquet_ID");
                String bouquet_name = rs.getString("bouquet_name").trim();
                Date created_at = rs.getDate("created_at");
                Date expire_date = rs.getDate("expiration_date");
                int created_by = rs.getInt("created_by");
                String description = rs.getString("description").trim();
                String imgurl = rs.getString("image_url");
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                Bouquet newBouquet = new Bouquet(bouquet_id, bouquet_name, created_at, expire_date, created_by, description, imgurl, cid, price);
                listBouquet.add(newBouquet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return listBouquet;
    }

    public List<Bouquet> searchBouquet(String name, Integer minPrice, Integer maxPrice, Integer categoryID) {
        List<Bouquet> searchListBQ = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM la_fioreria.bouquet b "
                + "Join la_fioreria.category c ON b.cid = c.category_id "
                + "Where 1=1 ");
        if (name != null && !name.trim().isEmpty()) {
            sql.append("AND b.bouquet_name LIKE ? ");
            params.add("%" + name + "%");
        }
        if (minPrice != null) {
            sql.append("AND b.price >= ? ");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append("AND b.price <= ? ");
            params.add(maxPrice);
        }
        if (categoryID != null) {
            sql.append("AND b.cid = ?");
            params.add(categoryID);
        }

        try {
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                pre.setObject(i + 1, params.get(i));
            }
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int bouquet_id = rs.getInt("Bouquet_ID");
                String bouquet_name = rs.getString("bouquet_name").trim();
                Date created_at = rs.getDate("created_at");
                Date expire_date = rs.getDate("expiration_date");
                int created_by = rs.getInt("created_by");
                String description = rs.getString("description").trim();
                String imgurl = rs.getString("image_url");
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                Bouquet searchBouquet = new Bouquet(bouquet_id, bouquet_name, created_at, expire_date, created_by, description, imgurl, cid, price);
                searchListBQ.add(searchBouquet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return searchListBQ;
    }

    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();
        List<Bouquet> list = dao.getAll();

        for (Bouquet bouquet : list) {
            System.out.println(bouquet);
        }
    }

}
