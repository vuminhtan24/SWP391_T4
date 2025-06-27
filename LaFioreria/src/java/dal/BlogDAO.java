/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Blog;
import model.Category;
import model.User;

/**
 *
 * @author k16
 */
public class BlogDAO extends BaseDao {

    public static void main(String[] args) {
        BlogDAO bDao = new BlogDAO();
        System.out.println(bDao.getTotalBlogCountWithFilter("", 0, "Active"));
    }

    public List<Blog> getAllBlogWithFilter(int limit, int offset, String searchKey, String sortBy, String sort, int categoryId, String status) {
        List<Blog> bList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM blog WHERE 1 = 1");

        if (categoryId > 0) {
            sqlBuilder.append(" AND cid = ?");
        }

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sqlBuilder.append(" AND (title LIKE ? OR context LIKE ? OR pre_context LIKE ?)");
        }

        if (status != null && !status.trim().isEmpty()) {
            sqlBuilder.append(" AND status = ?");
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
            if ("asc".equalsIgnoreCase(sort)) {
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
                String pattern = "%" + searchKey.trim() + "%";
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
                ps.setString(paramIndex++, pattern);
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status);
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
                blog.setStatus(rs.getString("status"));

                bList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getAllBlogWithFilter: " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return bList;
    }

    public int getTotalBlogCountWithFilter(String searchKey, int categoryId, String status) {
        int totalCount = 0;

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT COUNT(*) as total FROM blog WHERE 1 = 1");

        if (categoryId > 0) {
            sqlBuilder.append(" AND cid = ?");
        }

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sqlBuilder.append(" AND (title LIKE ? OR context LIKE ? OR pre_context LIKE ?)");
        }

        if (status != null && !status.trim().isEmpty()) {
            sqlBuilder.append(" AND (status = ?)");
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

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status);
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

    public Blog getBlogById(int bid) {
        Blog b = new Blog();
        String sql = """
                     SELECT b.*, c.category_name FROM blog b
                     JOIN category c ON b.cid = c.category_id
                     WHERE b.blog_id = ?
                     """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setInt(1, bid);

            rs = ps.executeQuery();

            if (rs.next()) {
                b.setBlogId(rs.getInt("blog_id"));
                b.setTitle(rs.getString("title"));
                b.setContext(rs.getString("context"));

                User u = new User();
                u.setUserid(rs.getInt("author_id"));

                b.setOwner(u);
                b.setCreated_at(rs.getTimestamp("created_at"));
                b.setUpdated_at(rs.getTimestamp("updated_at"));

                Category c = new Category();
                c.setCategoryId(rs.getInt("cid"));
                c.setCategoryName(rs.getString("category_name"));

                b.setCategory(c);
                b.setImg_url(rs.getString("image_url"));
                b.setPre_context(rs.getString("pre_context"));
                b.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            System.out.println("Error Blog by id: " + e.getMessage());
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        return b;
    }

    public boolean addBlog(Blog b) {
        String sql = """
                     INSERT INTO `la_fioreria`.`blog`
                     (`title`, `context`, `author_id`, `created_at`, `cid`, `image_url`, `pre_context`, `status`)
                     VALUES
                     (?, ?, ?, ?, ?, ?, ?, ?);
                     """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, b.getTitle());
            ps.setString(2, b.getContext());
            ps.setInt(3, b.getOwner().getUserid());
            ps.setTimestamp(4, b.getCreated_at());
            ps.setInt(5, b.getCategory().getCategoryId());
            ps.setString(6, b.getImg_url());
            ps.setString(7, b.getPre_context());
            ps.setString(8, b.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error add blog: " + e.getMessage());
            return false;
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public boolean updateBlogStatus(int bid, String status) {
        String sql = """
                     UPDATE blog
                     SET status = ?
                     WHERE blog_id = ?;
                     """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, bid);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error update blog status: " + e.getMessage());
            return false;
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public boolean updateBlog(Blog b) {
        String sql = """
                     UPDATE `la_fioreria`.`blog`
                     SET
                     `title` = ?,
                     `context` = ?,
                     `updated_at` = ?,
                     `image_url` = ?,
                     `pre_context` = ?,
                     `status` = ?,  
                     `cid` = ?
                     WHERE `blog_id` = ?;
                     """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, b.getTitle());
            ps.setString(2, b.getContext());
            ps.setTimestamp(3, b.getUpdated_at());
            ps.setString(4, b.getImg_url());
            ps.setString(5, b.getPre_context());
            ps.setString(6, b.getStatus());
            ps.setInt(7, b.getCategory().getCategoryId());
            ps.setInt(8, b.getBlogId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error Update post: " + e.getMessage());
            return false;
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
