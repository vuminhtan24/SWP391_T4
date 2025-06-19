/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Bouquet;
import model.BouquetImage;
import model.BouquetRaw;

/**
 *
 * @author ADMIN
 */
public class BouquetDAO extends BaseDao {

    public List<Bouquet> getAll() {
        List<Bouquet> listBouquet = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.bouquet";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bouquet_id = rs.getInt("Bouquet_ID");
                String bouquet_name = rs.getString("bouquet_name").trim();
                String description = rs.getString("description").trim();
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                Bouquet newBouquet = new Bouquet(bouquet_id, bouquet_name, description, cid, price);
                listBouquet.add(newBouquet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return listBouquet;
    }

    public List<Bouquet> searchBouquet(String name, Integer minPrice, Integer maxPrice, Integer categoryID, Integer rawId) {
        List<Bouquet> searchListBQ = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT b.* FROM la_fioreria.bouquet b "
                + "JOIN la_fioreria.category c ON b.cid = c.category_id "
                + "LEFT JOIN la_fioreria.bouquet_raw br ON b.Bouquet_ID = br.bouquet_id "
                + "LEFT JOIN la_fioreria.raw_flower r ON br.raw_id = r.raw_id "
                + "WHERE 1=1 "
        );

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
            sql.append("AND b.cid = ? ");
            params.add(categoryID);
        }
        if(rawId != null){
            sql.append("AND br.raw_id = ?");
            params.add(rawId);
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                int bouquet_id = rs.getInt("Bouquet_ID");
                String bouquet_name = rs.getString("bouquet_name").trim();
                String description = rs.getString("description").trim();
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                searchListBQ.add(new Bouquet(bouquet_id, bouquet_name, description, cid, price));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return searchListBQ;
    }
    
    public List<BouquetRaw> getBouquetRaw(){
        List<BouquetRaw> list = new ArrayList<>();
        String sql = "SELECT * From la_fioreria.bouquet_raw";
        
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("bouquet_id");
                int rawId = rs.getInt("raw_id");
                int quantity = rs.getInt("quantity");
            BouquetRaw raw = new BouquetRaw(id, rawId, quantity);
            list.add(raw);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * Insert bouquet, trả về khóa sinh tự động (bouquet_id).
     */
    public int insertBouquet(Bouquet bouquet) {
        String sql = """
        INSERT INTO la_fioreria.bouquet
          (bouquet_name, description, cid, price)
        VALUES (?, ?, ?, ?)
        """;
        // Chỉ định rõ cột PK cần lấy
        String[] genCols = {"bouquet_id"};

        try {
            // 1. Lấy connection tương tự getAll()
            connection = dbc.getConnection();
            // 2. Chuẩn bị statement với generated keys
            ps = connection.prepareStatement(sql, genCols);

            // 3. Set tham số
            ps.setString(1, bouquet.getBouquetName());
            ps.setString(2, bouquet.getDescription());
            ps.setInt(3, bouquet.getCid());
            ps.setInt(4, bouquet.getPrice());

            // 4. Thực thi
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Insert đã chạy nhưng không có row nào bị ảnh hưởng.");
            }

            // 5. Lấy generated key
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Insert thành công nhưng không lấy được generated key.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            // 6. Đóng connection, ps, rs giống getAll()
            try {
                this.closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * Insert một dòng bouquet_raw; bouquet_id phải được set trước.
     */
    public void insertBouquetRaw(BouquetRaw bouquetRaw) {
        String sql = """
        INSERT INTO la_fioreria.bouquet_raw 
          (bouquet_id, raw_id, quantity)
        VALUES (?, ?, ?)
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetRaw.getBouquet_id());
            ps.setInt(2, bouquetRaw.getRaw_id());
            ps.setInt(3, bouquetRaw.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void deleteBouquet(int id) {
        String sql = "DELETE FROM la_fioreria.bouquet WHERE Bouquet_ID = ?;";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void deleteBouquetRaw(int id) {
        String sql = "DELETE FROM la_fioreria.bouquet_raw WHERE bouquet_id = ?;";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void updateBouquet(Bouquet bouquet) {
        String sql = """
        UPDATE la_fioreria.bouquet
        SET bouquet_name = ?, description = ?, cid = ?, price = ?
        WHERE Bouquet_ID = ?;
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, bouquet.getBouquetName());
            ps.setString(2, bouquet.getDescription());
            ps.setInt(3, bouquet.getCid());
            ps.setInt(4, bouquet.getPrice());
            ps.setInt(5, bouquet.getBouquetId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void updateBouquetRaw(BouquetRaw bqRaw) {
        String sql = """
        UPDATE la_fioreria.bouquet_raw
        SET raw_id = ?, quantity = ?
        WHERE bouquet_id = ?;
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bqRaw.getRaw_id());
            ps.setInt(2, bqRaw.getQuantity());
            ps.setInt(3, bqRaw.getBouquet_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public Bouquet getBouquetByID(int id) {
        String sql = "SELECT * FROM la_fioreria.bouquet WHERE Bouquet_ID = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String bouquetName = rs.getString("bouquet_name").trim();
                String description = rs.getString("description").trim();
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                return new Bouquet(id, bouquetName, description, cid, price);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public List<BouquetRaw> getFlowerByBouquetID(int id) {
        List<BouquetRaw> listBQR = new ArrayList<>();
        String sql = """
        SELECT br.bouquet_id, br.raw_id, br.quantity
        FROM la_fioreria.bouquet_raw br
        JOIN la_fioreria.bouquet b ON b.bouquet_id = br.bouquet_id
        JOIN la_fioreria.raw_flower rf ON rf.raw_id = br.raw_id
        WHERE b.bouquet_id = ?;
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int raw_id = rs.getInt("raw_id");
                int quantity = rs.getInt("quantity");
                BouquetRaw bqRaw = new BouquetRaw(id, raw_id, quantity);
                listBQR.add(bqRaw);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return listBQR;
    }

    public Bouquet getBouquetByCategoryID(int cid) {
        String sql = "SELECT * FROM la_fioreria.bouquet WHERE cid = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            rs = ps.executeQuery();
            if (rs.next()) {
                int bouquetId = rs.getInt("bouquet_id");
                String bouquetName = rs.getString("bouquet_name").trim();
                String description = rs.getString("description").trim();
                int price = rs.getInt("price");
                return new Bouquet(bouquetId, bouquetName, description, cid, price);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public List<BouquetImage> getBouquetImage(int bouquetID) {
        List<BouquetImage> images = new ArrayList<>();
        String sql = "SELECT bi.Bouquet_ID, bi.image_url FROM bouquet b\n"
                + "JOIN bouquet_images bi ON bi.Bouquet_ID = b.Bouquet_ID\n"
                + "WHERE b.Bouquet_ID = ?;";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetID);
            rs = ps.executeQuery();
            while (rs.next()) {
                String imgURL = rs.getString("image_url").trim();
                BouquetImage img = new BouquetImage(bouquetID, imgURL);
                images.add(img);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return images;
    }

    public List<BouquetImage> getAllBouquetImage() {
        List<BouquetImage> allImages = new ArrayList<>();
        String sql = "SELECT bi.Bouquet_ID, bi.image_url FROM bouquet b\n"
                + "JOIN bouquet_images bi ON bi.Bouquet_ID = b.Bouquet_ID\n";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bouquetID = rs.getInt("Bouquet_ID");
                String imgURL = rs.getString("image_url").trim();
                BouquetImage img = new BouquetImage(bouquetID, imgURL);
                allImages.add(img);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return allImages;
    }

    public void deleteBouquetImage(int id) {
        String sql = "DELETE FROM la_fioreria.bouquet_images WHERE Bouquet_ID = ?;";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public void updateBouquetImage(BouquetImage image) {
        String sql = """
        UPDATE la_fioreria.bouquet_images
                SET image_url = ?
                WHERE Bouquet_ID = ?;
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, image.getImage_url());
            ps.setInt(2, image.getbouquetId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

    }

    public void insertBouquetImage(BouquetImage img) {
        String sql = """
        INSERT INTO la_fioreria.bouquet_images 
          (Bouquet_ID, image_url)
        VALUES (?, ?)
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, img.getbouquetId());
            ps.setString(2, img.getImage_url());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
    }

    public boolean isFlowerInBouquet(int flowerId) {
        String sql = "SELECT COUNT(*) FROM bouquet_raw WHERE raw_id = ?"; // Giả định bảng trung gian
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            ps.executeUpdate();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("BouquetDAO: Error in isFlowerInBouquet - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();
        Bouquet b = new Bouquet();
        BouquetRaw q = new BouquetRaw();
        b = dao.getBouquetByID(3);
        List<BouquetRaw> r = dao.getFlowerByBouquetID(3);
//        BouquetImage big = dao.getBouquetImage(1);
        List<BouquetImage> big = dao.getBouquetImage(1);
        System.out.println(big);

    }

}
