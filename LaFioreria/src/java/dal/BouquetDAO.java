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
import model.FlowerBatchAllocation;

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
                int sellPrice = rs.getInt("sellPrice");
                String status = rs.getString("status");
                Bouquet newBouquet = new Bouquet(bouquet_id, bouquet_name, description, cid, price, sellPrice, status);
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

    public List<Bouquet> getMostSellBouquet() {
        List<Bouquet> listBouquet = new ArrayList<>();

        String sql = "SELECT \n"
                + "    b.Bouquet_ID,\n"
                + "    b.bouquet_name,\n"
                + "    b.description,\n"
                + "    b.cid,\n"
                + "    b.price,\n"
                + "    b.sellPrice,\n"
                + "    b.status,\n"
                + "    SUM(oi.quantity) AS total_quantity\n"
                + "FROM bouquet b\n"
                + "JOIN order_item oi ON b.Bouquet_ID = oi.bouquet_id\n"
                + "WHERE oi.status = 'done'\n"
                + "GROUP BY \n"
                + "    b.Bouquet_ID, b.bouquet_name, b.description, b.cid, b.price, b.sellPrice, b.status;";

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
                int sellPrice = rs.getInt("sellPrice");
                String status = rs.getString("status");
                Bouquet newBouquet = new Bouquet(bouquet_id, bouquet_name, description, cid, price, sellPrice, status);
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
                "SELECT DISTINCT b.* FROM la_fioreria.bouquet b \n"
                + "JOIN la_fioreria.category c ON b.cid = c.category_id \n"
                + "LEFT JOIN la_fioreria.bouquet_raw br ON b.Bouquet_ID = br.bouquet_id \n"
                + "LEFT JOIN la_fioreria.flower_batch r ON br.batch_id = r.batch_id \n"
                + "LEFT JOIN la_fioreria.flower_type ft ON ft.flower_id = r.flower_id\n"
                + "WHERE 1=1 "
        );

        if (name != null && !name.trim().isEmpty()) {
            sql.append("AND b.bouquet_name LIKE ? ");
            params.add("%" + name + "%");
        }
        if (minPrice != null) {
            sql.append("AND b.sellPrice >= ? ");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append("AND b.sellPrice <= ? ");
            params.add(maxPrice);
        }
        if (categoryID != null) {
            sql.append("AND b.cid = ? ");
            params.add(categoryID);
        }
        if (rawId != null) {
            sql.append("AND ft.flower_id = ?");
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
                int sellPrice = rs.getInt("sellPrice");
                String status = rs.getString("status");
                searchListBQ.add(new Bouquet(bouquet_id, bouquet_name, description, cid, price, sellPrice, status));
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

    public List<BouquetRaw> getBouquetRaw() {
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
          (bouquet_name, description, cid, price, sellPrice)
        VALUES (?, ?, ?, ?, ?)
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
            ps.setInt(5, bouquet.getSellPrice());

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
          (bouquet_id, batch_id, quantity)
        VALUES (?, ?, ?)
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetRaw.getBouquet_id());
            ps.setInt(2, bouquetRaw.getBatchId());
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
        SET bouquet_name = ?, description = ?, cid = ?, price = ?, sellPrice = ?, status = ?
        WHERE Bouquet_ID = ?;
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, bouquet.getBouquetName());
            ps.setString(2, bouquet.getDescription());
            ps.setInt(3, bouquet.getCid());
            ps.setInt(4, bouquet.getPrice());
            ps.setInt(5, bouquet.getSellPrice());
            ps.setString(6, bouquet.getStatus());
            ps.setInt(7, bouquet.getBouquetId());
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
            ps.setInt(1, bqRaw.getBatchId());
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
                int sellPrice = rs.getInt("sellPrice");
                String status = rs.getString("status");
                return new Bouquet(id, bouquetName, description, cid, price, sellPrice, status);
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

    public List<BouquetRaw> getFlowerBatchByBouquetID(int id) {
        List<BouquetRaw> listBQR = new ArrayList<>();
        String sql = """
        SELECT b.bouquet_id, b.batch_id, b.quantity FROM la_fioreria.bouquet_raw b
        JOIN la_fioreria.flower_batch fb ON fb.batch_id = b.batch_id
        LEFT JOIN la_fioreria.flower_type ft ON ft.flower_id = fb.flower_id
        WHERE b.bouquet_id = ?;             
        """;
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int batch_id = rs.getInt("batch_id");
                int quantity = rs.getInt("quantity");
                BouquetRaw bqRaw = new BouquetRaw(id, batch_id, quantity);
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
                int sellPrice = rs.getInt("sellPrice");
                String status = rs.getString("status");
                return new Bouquet(bouquetId, bouquetName, description, cid, price, sellPrice, status);
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
        String sql = "SELECT COUNT(*) \n"
                + "FROM bouquet_raw b\n"
                + "JOIN flower_batch f ON f.batch_id = b.batch_id\n"
                + "WHERE f.flower_id = ?;"; // Giả định bảng trung gian
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flowerId);
            rs = ps.executeQuery();
            while (rs.next()) {
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

    public boolean isBatchInBouquet(int batchId) {
        String sql = "SELECT COUNT(*) \n"
                + "FROM bouquet_raw \n"
                + "WHERE batch_id = ?;"; // Giả định bảng trung gian
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, batchId);
            rs = ps.executeQuery();
            while (rs.next()) {
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

    public Bouquet getBouquetFullInfoById(int bid) {
        Bouquet b = new Bouquet();
        String sql = """
                     SELECT 
                         *
                     FROM
                         bouquet
                     WHERE Bouquet_ID = ?
                     """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bid);
            rs = ps.executeQuery();
            while (rs.next()) {
                b.setBouquetId(rs.getInt("bouquet_id"));
                b.setBouquetName(rs.getString("bouquet_name"));
                b.setDescription(rs.getString("description"));
                b.setCid(rs.getInt("cid"));
                b.setPrice(rs.getInt("price"));
                b.setSellPrice(rs.getInt("sellPrice"));
                b.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("BouquetDAO: Error in isFlowerInBouquet - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }

        return b;
    }  

    public int bouquetAvailable(int bouquetId) {
        int available = 0;
        String sql = """
        SELECT MIN(sub.possible_bouquet) AS max_bouquet_count
        FROM (
            SELECT br.bouquet_id,
                   FLOOR((
                       fb.quantity
                       - COALESCE(SUM(CASE WHEN fba.status = 'soft_hold' AND fba.created_at >= NOW() - INTERVAL 30 MINUTE THEN fba.quantity ELSE 0 END), 0)
                       - COALESCE(SUM(CASE WHEN fba.status = 'confirmed' THEN fba.quantity ELSE 0 END), 0)
                   ) / br.quantity) AS possible_bouquet
            FROM bouquet_raw br
            JOIN flower_batch fb ON br.batch_id = fb.batch_id
            LEFT JOIN flower_batch_allocation fba ON fb.batch_id = fba.batch_id
            WHERE br.bouquet_id = ?
            GROUP BY br.batch_id, br.quantity
        ) AS sub
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bouquetId);
            rs = ps.executeQuery();
            if (rs.next()) {
                available = rs.getInt("max_bouquet_count");
            }
        } catch (SQLException e) {
            System.err.println("BouquetDAO: Error in bouquetAvailable - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return available;
    }

    public List<Bouquet> allBouquetAvailable() {
        List<Bouquet> availableList = new ArrayList<>();
        String sql = """
            SELECT b.bouquet_id,
                   MIN(FLOOR(
                       (fb.quantity 
                        - COALESCE(SUM(CASE WHEN fba.status = 'soft_hold' AND fba.created_at >= NOW() - INTERVAL 30 MINUTE THEN fba.quantity ELSE 0 END), 0)
                        - COALESCE(SUM(CASE WHEN fba.status = 'confirmed' THEN fba.quantity ELSE 0 END), 0)
                       ) / br.quantity)) AS max_bouquet_count
            FROM bouquet b
            JOIN bouquet_raw br ON b.bouquet_id = br.bouquet_id
            JOIN flower_batch fb ON br.batch_id = fb.batch_id
            LEFT JOIN flower_batch_allocation fba ON fb.batch_id = fba.batch_id
            GROUP BY b.bouquet_id
        """;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int bouquetId = rs.getInt("bouquet_id");
                int quantity = rs.getInt("max_bouquet_count");
                availableList.add(new Bouquet(bouquetId, quantity));
            }
        } catch (SQLException e) {
            System.err.println("BouquetDAO: Error in allBouquetAvailable - " + e.getMessage());
        } finally {
            try {
                this.closeResources();
            } catch (Exception e) {
            }
        }
        return availableList;
    }

    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();
        Bouquet b = new Bouquet();
        BouquetRaw q = new BouquetRaw();
        b = dao.getBouquetByID(3);
//        List<BouquetRaw> r = dao.getFlowerBatchByBouquetID(3);
//        BouquetImage big = dao.getBouquetImage(1);
//        List<BouquetImage> big = dao.getBouquetImage(5);
//        System.out.println(big);
//        System.out.println(dao.bouquetAvailable(1));

        System.out.println(dao.getBouquetFullInfoById(1));
    }

}
