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
import model.RawFlower;

/**
 *
 * @author Admin
 */
public class RawFlowerDAO extends DBContext {

    public List<RawFlower> getAll() {
        List<RawFlower> listRawFlower = new ArrayList<>();

        String sql = "SELECT * FROM la_fioreria.raw_flower;";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int raw_id = rs.getInt("raw_id");
                String raw_name = rs.getString("raw_name").trim();
                int raw_quantity = rs.getInt("raw_quantity");
                int unit_price = rs.getInt("unit_price");
                String expiration_date = rs.getString("expiration_date");
                int warehouse_id = rs.getInt("Warehouse_id");
                String image_url = rs.getString("image_url").trim();
                int hold = rs.getInt("hold");
                int import_price = rs.getInt("import_price");
                RawFlower newRawFlower = new RawFlower(raw_id, raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url, hold, import_price);
                listRawFlower.add(newRawFlower);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return listRawFlower;
    }
    
    public int count() {
        try {
            String sql = "SELECT COUNT(*) AS `count` FROM rawflower";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<RawFlower> getRawFlowerByFilter(Integer minPrice, Integer maxPrice, Date fromDate, Date toDate) {
    ArrayList<RawFlower> list = new ArrayList<>();
    String sql = "SELECT * FROM la_fioreria.raw_flower WHERE 1=1";
        if (minPrice != null) {
            sql += " AND unit_price >= " + minPrice;
        }
        if (maxPrice != null) {
            sql += " AND unit_price <= " + maxPrice;
        }
        if (fromDate != null) {
            sql += " AND expiration_date >= '" + fromDate + "'";
        }
        if (toDate != null) {
            sql += " AND expiration_date <= '" + toDate + "'";
        }

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            RawFlower rf = new RawFlower();
            rf.setRawId(rs.getInt("raw_id"));
            rf.setRawName(rs.getString("raw_name").trim());
            rf.setRawQuantity(rs.getInt("raw_quantity"));
            rf.setUnitPrice(rs.getInt("unit_price"));
            rf.setExpirationDate(rs.getString("expiration_date"));
            rf.setWarehouseId(rs.getInt("warehouse_id"));
            rf.setImageUrl(rs.getString("image_url").trim());
            rf.setHold(rs.getInt("hold"));
            rf.setImportPrice(rs.getInt("import_price"));
            list.add(rf);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

    //cập nhật các thông tin chung
    public void updateRawFlower1(int raw_id, String raw_name, int unit_price, Date expiration_date, int warehouse_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_name = ?, unit_price = ?, expiration_date = ?, warehouse_id = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, raw_name);
            ps.setInt(2, unit_price);
            ps.setDate(3, expiration_date);
            ps.setInt(4, warehouse_id);
            ps.setInt(5, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //lấy danh sách các bản ghi RawFlower từ database theo từng trang (phân trang).
    public List<RawFlower> getAllRawFlowerPaging(int pageIndex, int pageSize) {
        List<RawFlower> list = new ArrayList<>();
        String sql = "SELECT * FROM ( "
                + "SELECT ROW_NUMBER() OVER (ORDER BY raw_id) as rownum, * "
                + "FROM la_fioreria.raw_flower "
                + ") as temp WHERE rownum BETWEEN ? AND ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            statement.setInt(1, start);
            statement.setInt(2, end);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int raw_id = rs.getInt("raw_id");
                String raw_name = rs.getString("raw_name").trim();
                int raw_quantity = rs.getInt("raw_quantity");
                int unit_price = rs.getInt("unit_price");
                String expiration_date = rs.getString("expiration_date");
                int warehouse_id = rs.getInt("warehouse_id");
                String image_url = rs.getString("image_url").trim();
                int hold = rs.getInt("hold");
                int import_price = rs.getInt("import_price");

                RawFlower rawFlower = new RawFlower(raw_id, raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url, hold, import_price);
                list.add(rawFlower);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Cập nhật thêm ảnh và tồn kho hold
    public void updateRawFlower2(int raw_id, String image_url, int hold) {
        String sql = "UPDATE la_fioreria.raw_flower SET image_url = ?, hold = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, image_url);
            ps.setInt(2, hold);
            ps.setInt(3, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Cập nhật số lượng, giá nhập, và số lượng đang giữ
    public void updateRawFlower3(int raw_id, int raw_quantity, int import_price, int hold) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_quantity = ?, import_price = ?, hold = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, raw_quantity);
            ps.setInt(2, import_price);
            ps.setInt(3, hold);
            ps.setInt(4, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Cập nhật toàn bộ thông tin
    public void updateRawFlower4(int raw_id, String raw_name, int raw_quantity, int unit_price,
            Date expiration_date, int warehouse_id, String image_url,
            int hold, int import_price) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_name = ?, raw_quantity = ?, unit_price = ?, "
                + "expiration_date = ?, warehouse_id = ?, image_url = ?, hold = ?, import_price = ? "
                + "WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, raw_name);
            ps.setInt(2, raw_quantity);
            ps.setInt(3, unit_price);
            ps.setDate(4, expiration_date);
            ps.setInt(5, warehouse_id);
            ps.setString(6, image_url);
            ps.setInt(7, hold);
            ps.setInt(8, import_price);
            ps.setInt(9, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Cập nhật chỉ tên, ảnh và warehouse (vai trò Marketer chẳng hạn)
    public void updateRawFlower5(int raw_id, String raw_name, String image_url, int warehouse_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_name = ?, image_url = ?, warehouse_id = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, raw_name);
            ps.setString(2, image_url);
            ps.setInt(3, warehouse_id);
            ps.setInt(4, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Chuyên cho Inventory Staff: cập nhật số lượng, giá nhập, kích cỡ
    public void updateRawFlower6(int raw_id, int raw_quantity, int hold, int import_price, int unit_price) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_quantity = ?, hold = ?, import_price = ?, unit_price = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, raw_quantity);
            ps.setInt(2, hold);
            ps.setInt(3, import_price);
            ps.setInt(4, unit_price);
            ps.setInt(5, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Chỉ thêm sản phẩm mới với raw_quantity = 0 và hold = 0
    public void addRawFlower1(String raw_name, int unit_price, Date expiration_date, int warehouse_id, String image_url, int import_price) {
        String sql = "INSERT INTO la_fioreria.raw_flower (raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url, hold, import_price) "
                + "VALUES (?, 0, ?, ?, ?, ?, 0, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, raw_name);
            ps.setInt(2, unit_price);
            ps.setDate(3, expiration_date);
            ps.setInt(4, warehouse_id);
            ps.setString(5, image_url);
            ps.setInt(6, import_price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Thêm đủ thông tin
    public void addRawFlower(String raw_name, int raw_quantity, int unit_price, Date expiration_date,
            int warehouse_id, String image_url, int hold, int import_price) {
        String sql = "INSERT INTO la_fioreria.raw_flower (raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url, hold, import_price) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, raw_name);
            ps.setInt(2, raw_quantity);
            ps.setInt(3, unit_price);
            ps.setDate(4, expiration_date);
            ps.setInt(5, warehouse_id);
            ps.setString(6, image_url);
            ps.setInt(7, hold);
            ps.setInt(8, import_price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<RawFlower> getRawFlower() {
        ArrayList<RawFlower> list = new ArrayList<>();
        String sql = "SELECT raw_id, raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url FROM la_fioreria.raw_flower";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RawFlower rf = new RawFlower();
                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouseId(rs.getInt("warehouse_id"));
                rf.setImageUrl(rs.getString("image_url").trim());
                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Xóa mềm
    public void hideRawFlower(int raw_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET active = 0 WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Đếm tổng số sản phẩm (RawFlower) hiện có trong hệ thống.
    //Thường dùng cho:
    //Phân trang: Biết tổng số lượng bản ghi để chia ra trang hiển thị.
    //Thống kê: Hiển thị số liệu tổng quan cho người quản lý.
    //Kiểm tra dữ liệu: Đảm bảo dữ liệu có trong bảng, không bị trống.
    public int getTotalRawFlowerCount() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM la_fioreria.raw_flower";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    //cập nhật số lượng tồn kho và giá nhập của nguyên liệu (RawFlower) khi có đợt nhập hàng mới vào kho.
    public void importRawFlower(int raw_id, int importPrice, int quantity) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_quantity = raw_quantity + ?, import_price = ? WHERE raw_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, importPrice);
            ps.setInt(3, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //tìm kiếm danh sách các đối tượng RawFlower có tên chứa từ khóa (keyword) nhất định trong bảng raw_flower của database.
    public List<RawFlower> searchRawFlowerByKeyword(String keyword) {
        String sql = "SELECT * FROM la_fioreria.raw_flower WHERE raw_name LIKE ?";
        List<RawFlower> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RawFlower rf = new RawFlower();
                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouseId(rs.getInt("warehouse_id"));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
