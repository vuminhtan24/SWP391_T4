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
public class CategoryDAO extends DBContext {

    public List<Category> getBouquetCategory() {
        List<Category> listBouquetCategory = new ArrayList<>();

        String sql = "SELECT DISTINCT c.* \n"
                + "FROM la_fioreria.category c\n"
                + "JOIN la_fioreria.bouquet b ON c.category_id = b.cid";

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

    public String getCategoryNameByBouquet(int bqId) {
        String sql = "SELECT c.category_name FROM la_fioreria.category c\n"
                + "Join la_fioreria.bouquet b ON b.cid = c.category_id\n"
                + "WHERE b.Bouquet_ID = ?;";
        
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, bqId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                String categoryName = rs.getString("category_name").trim();

                return categoryName;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
       
    public List<Category> searchCategory(String categoryName) {
        List<Category> searchList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT category_id, category_name, description "
                + "FROM la_fioreria.category "
                + "WHERE 1=1 ");

        // Thêm điều kiện tìm kiếm theo category_name
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            sql.append("AND category_name LIKE ? ");
        } else {
            // Nếu categoryName rỗng, trả về danh sách rỗng hoặc tất cả danh mục (tùy yêu cầu)
            return searchList; // Hoặc có thể bỏ điều kiện này để lấy tất cả danh mục
        }

        try (PreparedStatement pre = connection.prepareStatement(sql.toString())) {
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                pre.setString(1, "%" + categoryName + "%");
            }

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    int category_id = rs.getInt("category_id");
                    String category_name = rs.getString("category_name") != null ? rs.getString("category_name").trim() : "";
                    String description = rs.getString("description") != null ? rs.getString("description").trim() : "";
                    Category category = new Category(category_id, category_name, description);
                    searchList.add(category);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in searchCategory: " + e.getMessage());
            e.printStackTrace();
        }
        return searchList;
    }
    
    // Lấy danh mục theo ID
    public Category getCategoryById(int id) {
        String sql = "SELECT category_id, category_name, description FROM la_fioreria.category WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int categoryId = rs.getInt("category_id");
                    String categoryName = rs.getString("category_name") != null ? rs.getString("category_name").trim() : "";
                    String description = rs.getString("description") != null ? rs.getString("description").trim() : "";
                    return new Category(categoryId, categoryName, description);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getCategoryById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật danh mục
    public void updateCategory(Category category) {
        String sql = "UPDATE la_fioreria.category SET category_name = ?, description = ? WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updateCategory: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Category c = new Category();
        CategoryDAO dao = new CategoryDAO();
        List<Category> cl = dao.getBouquetCategory();
        System.out.println(cl);
    }

}
