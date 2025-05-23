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
import model.Category;
/**
 *
 * @author ADMIN
 */
public class CategoryDAO extends DBContext{
    
    public List<Category> getBouquetCategory(){
        List<Category> listBouquetCategory = new ArrayList<>();
        
        String sql = "SELECT DISTINCT c.* \n" +
                     "FROM la_fioreria.category c\n" +
                     "JOIN la_fioreria.bouquet b ON c.category_id = b.cid";
        
         try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name").trim();
                String description = rs.getString("description").trim();
                
                Category newBouquetCategory = new Category(category_id, category_name, description);
                listBouquetCategory.add(newBouquetCategory);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
         return listBouquetCategory;
    }
    
    
}
