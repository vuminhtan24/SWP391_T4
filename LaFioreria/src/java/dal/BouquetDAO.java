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
import model.BouquetRaw;

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
                String description = rs.getString("description").trim();
                String imgurl = rs.getString("image_url");
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                Bouquet newBouquet = new Bouquet(bouquet_id, bouquet_name, description, imgurl, cid, price);
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
                String description = rs.getString("description").trim();
                String imgurl = rs.getString("image_url");
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");
                Bouquet searchBouquet = new Bouquet(bouquet_id, bouquet_name, description, imgurl, cid, price);
                searchListBQ.add(searchBouquet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return searchListBQ;
    }

    /**
     * Insert bouquet, trả về khóa sinh tự động (bouquet_id).
     */
    public int insertBouquet(Bouquet bouquet) {
        String sql = """
      INSERT INTO la_fioreria.bouquet
        (bouquet_name, description, image_url, cid, price)
      VALUES (?, ?, ?, ?, ?)
      """;
        // 1. Chỉ định rõ cột PK cần lấy
        String[] genCols = {"bouquet_id"};
        try (PreparedStatement pre = connection.prepareStatement(sql, genCols)) {
            pre.setString(1, bouquet.getBouquetName());
            pre.setString(2, bouquet.getDescription());
            pre.setString(3, bouquet.getImageUrl());
            pre.setInt(4, bouquet.getCid());
            pre.setInt(5, bouquet.getPrice());

            int affected = pre.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Insert đã chạy nhưng không có row nào bị ảnh hưởng.");
            }

            try (ResultSet rs = pre.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Insert thành công nhưng không lấy được generated key.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Insert một dòng bouquet_raw; bouquet_id phải được set trước.
     */
    public void insertBouquetRaw(BouquetRaw bouquetRaw){
        String sql = """
            INSERT INTO la_fioreria.bouquet_raw 
              (bouquet_id, raw_id, quantity)
            VALUES (?, ?, ?)
            """;

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, bouquetRaw.getBouquet_id());
            pre.setInt(2, bouquetRaw.getRaw_id());
            pre.setInt(3, bouquetRaw.getQuantity());
            pre.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void deleteBouquet(int id) {
        String sql = "DELETE FROM la_fioreria.bouquet WHERE Bouquet_ID = ?;";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteBouquetRaw(int id) {
        String sql = "DELETE FROM la_fioreria.bouquet_raw WHERE bouquet_id = ?;";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateBouquet(Bouquet bouquet) {
        String sql = "Update la_fioreria.bouquet\n"
                + "set bouquet_name = ?, description = ?, image_url = ?, cid = ?, price = ?\n"
                + "where Bouquet_ID = ?; ";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, bouquet.getBouquetName());
            pre.setString(2, bouquet.getDescription());
            pre.setString(3, bouquet.getImageUrl());
            pre.setInt(4, bouquet.getCid());
            pre.setInt(5, bouquet.getPrice());
            pre.setInt(6, bouquet.getBouquetId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void updateBouquetRaw(BouquetRaw bqRaw) {
        String sql = "Update la_fioreria.bouquet_raw\n"
                + "SET raw_id = ?, quantity = ?\n"
                + "where bouquet_id = ?;";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, bqRaw.getRaw_id());
            pre.setInt(2, bqRaw.getQuantity());
            pre.setInt(3, bqRaw.getBouquet_id());
            pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Bouquet getBouquetByID(int id) {
        String sql = "SELECT * FROM la_fioreria.bouquet WHERE Bouquet_ID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                String bouquetName = rs.getString("bouquet_name").trim();
                String Description = rs.getString("description").trim();
                String imageUrl = rs.getString("image_url").trim();
                int cid = rs.getInt("cid");
                int price = rs.getInt("price");

                Bouquet bq = new Bouquet(id, bouquetName, Description, imageUrl, cid, price);
                return bq;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<BouquetRaw> getFlowerByBouquetID(int id) {
        List<BouquetRaw> listBQR = new ArrayList<>();
        String sql = "SELECT br.bouquet_id, br.raw_id, br.quantity "
                + "FROM la_fioreria.bouquet_raw br "
                + "JOIN la_fioreria.bouquet b ON b.bouquet_id = br.bouquet_id "
                + "JOIN la_fioreria.raw_flower rf ON rf.raw_id = br.raw_id "
                + "WHERE b.bouquet_id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            // Duyệt hết các dòng trả về
            while (rs.next()) {
                int raw_id = rs.getInt("raw_id");
                int quantity = rs.getInt("quantity");
                BouquetRaw bqRaw = new BouquetRaw(id, raw_id, quantity);
                listBQR.add(bqRaw);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể log, hoặc ném exception lên cao nếu muốn
        }
        // Trả về list (có thể rỗng nếu không tìm thấy bản ghi nào)
        return listBQR;
    }
    
        public Bouquet getBouquetByCategoryID(int cid) {
        String sql = "SELECT * FROM la_fioreria.bouquet WHERE cid = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, cid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int bouquetId = rs.getInt("bouquet_id");
                String bouquetName = rs.getString("bouquet_name").trim();
                String Description = rs.getString("description").trim();
                String imageUrl = rs.getString("image_url").trim();
                int price = rs.getInt("price");
                Bouquet bq = new Bouquet(bouquetId, bouquetName, Description, imageUrl, cid, price);
                return bq;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();
        List<Bouquet> list = dao.getAll();
        Bouquet b = new Bouquet();
        BouquetRaw q = new BouquetRaw();
        b = dao.getBouquetByID(3);
        List<BouquetRaw> r = dao.getFlowerByBouquetID(3);
        
    }

}
