/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import model.Blog;
import model.Category;
import model.User;

/**
 *
 * @author k16
 */
public class BlogDAO extends BaseDao {

    public static void main(String[] args) {

    }

    public List<Blog> getAllBlogWithFilter(int limit, int offset, String searchKey, String sortBy, String sort, int categoryId) {
        List<Blog> bList = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM blog WHERE status = 'Active'");

        if (categoryId > 0) {
            sqlBuilder.append(" AND category_id = ?");
        }

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sqlBuilder.append(" AND (title LIKE ? OR context LIKE ? OR pre_context LIKE ?)");
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            sqlBuilder.append(" ORDER BY ");

            switch (sortBy.toLowerCase()) {
                case "title":
                    sqlBuilder.append("title");
                    break;
                case "created_at":
                case "created":
                    sqlBuilder.append("created_at");
                    break;
                case "updated_at":
                case "updated":
                    sqlBuilder.append("updated_at");
                    break;
                case "author_id":
                case "author":
                    sqlBuilder.append("author_id");
                    break;
                default:
                    sqlBuilder.append("created_at");
            }

            // Add sort direction
            if (sort != null && sort.equalsIgnoreCase("ASC")) {
                sqlBuilder.append(" ASC");
            } else {
                sqlBuilder.append(" DESC");
            }
        } else {
            sqlBuilder.append(" ORDER BY created_at DESC");
        }

        sqlBuilder.append(" LIMIT ? OFFSET ?");

        String sql = sqlBuilder.toString();

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            int paramIndex = 1;

            if (categoryId > 0) {
                ps.setInt(paramIndex++, categoryId);
            }

            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String searchPattern = "%" + searchKey.trim() + "%";
                ps.setString(paramIndex++, searchPattern); // title
                ps.setString(paramIndex++, searchPattern); // context
                ps.setString(paramIndex++, searchPattern); // pre_context
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            rs = ps.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setContext(rs.getString("context"));

                User u = new User();
                u.setUserid(rs.getInt("author_id"));

                blog.setOwner(u);
                blog.setCreated_at(rs.getTimestamp("created_at"));
                blog.setUpdated_at(rs.getTimestamp("updated_at"));

                Category c = new Category();
                c.setCategoryId(rs.getInt("cid"));

                blog.setCategory(c);
                blog.setImg_url(rs.getString("image_url"));
                blog.setPre_context(rs.getString("pre_context"));

                // Add other fields if they exist in your Blog class
                // blog.setStatus(rs.getString("status"));
                // blog.setCategoryId(rs.getInt("category_id"));
                bList.add(blog);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error in getAllBlogWithFilter: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in getAllBlogWithFilter: " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return bList;
    }

// Helper method to get total count for pagination
    public int getTotalBlogCountWithFilter(String searchKey, int categoryId) {
        int totalCount = 0;

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) as total FROM blog WHERE status = 'Active'");

        if (categoryId > 0) {
            sqlBuilder.append(" AND category_id = ?");
        }

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sqlBuilder.append(" AND (title LIKE ? OR context LIKE ? OR pre_context LIKE ?)");
        }

        String sql = sqlBuilder.toString();

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            int paramIndex = 1;

            if (categoryId > 0) {
                ps.setInt(paramIndex++, categoryId);
            }

            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String searchPattern = "%" + searchKey.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex, searchPattern);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error getting total count: " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return totalCount;
    }
}
