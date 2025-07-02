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
public class CategoryDAO extends BaseDao {

    public List<Category> getAll() {
        List<Category> listCategory = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.category";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name").trim();
                String description = rs.getString("description").trim();
                listCategory.add(new Category(category_id, category_name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return listCategory;
    }

    public List<Category> getBouquetCategory() {
        List<Category> listBouquetCategory = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM la_fioreria.category c JOIN la_fioreria.bouquet b ON c.category_id = b.cid";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name").trim();
                String description = rs.getString("description").trim();
                listBouquetCategory.add(new Category(category_id, category_name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return listBouquetCategory;
    }

    public String getCategoryNameByBouquet(int bqId) {
        String sql = "SELECT c.category_name FROM la_fioreria.category c JOIN la_fioreria.bouquet b ON b.cid = c.category_id WHERE b.Bouquet_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bqId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("category_name").trim();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public String getCategoryDesByBouquet(int bqId) {
        String sql = "SELECT c.description FROM la_fioreria.category c JOIN la_fioreria.bouquet b ON b.cid = c.category_id WHERE b.Bouquet_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bqId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("description").trim();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public List<Category> searchCategory(String categoryName) {
        List<Category> searchList = new ArrayList<>();
        String sql = "SELECT category_id, category_name, description FROM la_fioreria.category WHERE category_name LIKE ?";
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return searchList;
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + categoryName.trim() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name") != null ? rs.getString("category_name").trim() : "";
                String description = rs.getString("description") != null ? rs.getString("description").trim() : "";
                searchList.add(new Category(category_id, category_name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return searchList;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT category_id, category_name, description FROM la_fioreria.category WHERE category_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name") != null ? rs.getString("category_name").trim() : "";
                String description = rs.getString("description") != null ? rs.getString("description").trim() : "";
                return new Category(categoryId, categoryName, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public boolean updateCategory(Category category) {
        String sql = "UPDATE la_fioreria.category SET category_name = ?, description = ? WHERE category_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows for category ID: " + category.getCategoryId());
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean insertCategory(Category category) {
        String sql = "INSERT INTO la_fioreria.category (category_name, description) VALUES (?, ?)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM la_fioreria.category WHERE category_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean isCategoryNameExists(String categoryName, int excludeCategoryId) {
        String sql = "SELECT COUNT(*) FROM la_fioreria.category WHERE category_name = ? AND category_id != ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            ps.setInt(2, excludeCategoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in isCategoryNameExists: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean isCategoryNameExists(String categoryName) {
        String sql = "SELECT COUNT(*) FROM la_fioreria.category WHERE category_name = ?";
        try {
            connection = dbc.getConnection(); // Khởi tạo kết nối
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error in isCategoryNameExists: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                this.closeResources(); // Đóng tài nguyên
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Category c = new Category();
        CategoryDAO dao = new CategoryDAO();
        List<Category> cl = dao.getBouquetCategory();
        System.out.println(cl);
    }

}
