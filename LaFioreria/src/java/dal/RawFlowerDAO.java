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
public class RawFlowerDAO extends BaseDao {

    // Assuming you have instance variables:
// private Connection connection;
// private PreparedStatement ps;
// private ResultSet rs;
// private DBC dbc; // Your DB helper class
// And a closeResources() method that closes rs, ps, and connection.
    public List<RawFlower> getAll() {
        List<RawFlower> listRawFlower = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.raw_flower";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower r = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                r.setRawId(rs.getInt("raw_id"));
                r.setRawName(rs.getString("raw_name").trim());
                r.setRawQuantity(rs.getInt("raw_quantity"));
                r.setUnitPrice(rs.getInt("unit_price"));
                r.setExpirationDate(rs.getString("expiration_date")); // Consider changing column type to DATE if possible
                r.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                r.setImageUrl(rs.getString("image_url").trim());
                r.setHold(rs.getInt("hold"));
                r.setImportPrice(rs.getInt("import_price"));
                r.setActive(rs.getBoolean("active"));

                listRawFlower.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return listRawFlower;
    }

    public int count() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS `count` FROM la_fioreria.raw_flower";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return total;
    }

    public List<RawFlower> getRawFlowerByFilter(Integer whid, Integer minPrice, Integer maxPrice, Date fromDate, Date toDate) {
        List<RawFlower> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM la_fioreria.raw_flower WHERE 1=1");

        if (minPrice != null) {
            sql.append(" AND unit_price >= ").append(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND unit_price <= ").append(maxPrice);
        }
        if (fromDate != null) {
            sql.append(" AND expiration_date >= '").append(fromDate).append("'");
        }
        if (toDate != null) {
            sql.append(" AND expiration_date <= '").append(toDate).append("'");
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setActive(rs.getBoolean("active"));

                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public void updateRawFlower1(int raw_id, String raw_name, int unit_price, Date expiration_date, int warehouse_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_name = ?, unit_price = ?, expiration_date = ?, warehouse_id = ? WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, raw_name.trim());
            ps.setInt(2, unit_price);
            ps.setDate(3, expiration_date);
            ps.setInt(4, warehouse_id);
            ps.setInt(5, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public List<RawFlower> getAllRawFlowerPaging(int pageIndex, int pageSize) {
        List<RawFlower> list = new ArrayList<>();
        String sql
                = "SELECT * FROM ( "
                + "  SELECT ROW_NUMBER() OVER (ORDER BY raw_id) AS rownum, * "
                + "  FROM la_fioreria.raw_flower "
                + ") AS temp WHERE rownum BETWEEN ? AND ?";

        int start = (pageIndex - 1) * pageSize + 1;
        int end = pageIndex * pageSize;

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, end);
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setActive(rs.getBoolean("active"));

                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public void updateRawFlower2(int raw_id, String image_url, int hold) {
        String sql = "UPDATE la_fioreria.raw_flower SET image_url = ?, hold = ? WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, image_url.trim());
            ps.setInt(2, hold);
            ps.setInt(3, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void updateRawFlower3(int raw_id, int raw_quantity, int import_price, int hold) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_quantity = ?, import_price = ?, hold = ? WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, raw_quantity);
            ps.setInt(2, import_price);
            ps.setInt(3, hold);
            ps.setInt(4, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void updateRawFlower4(int raw_id, String raw_name, int raw_quantity, int unit_price,
            Date expiration_date, int warehouse_id, String image_url,
            int hold, int import_price) throws SQLException {
        String sql
                = "UPDATE la_fioreria.raw_flower SET raw_name = ?, raw_quantity = ?, unit_price = ?, "
                + "expiration_date = ?, warehouse_id = ?, image_url = ?, hold = ?, import_price = ? "
                + "WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, raw_name.trim());
            ps.setInt(2, raw_quantity);
            ps.setInt(3, unit_price);
            ps.setDate(4, expiration_date);
            ps.setInt(5, warehouse_id);
            ps.setString(6, image_url.trim());
            ps.setInt(7, hold);
            ps.setInt(8, import_price);
            ps.setInt(9, raw_id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No rows updated. RawFlower ID " + raw_id + " not found.");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void updateRawFlower5(int raw_id, String raw_name, String image_url, int warehouse_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_name = ?, image_url = ?, warehouse_id = ? WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, raw_name.trim());
            ps.setString(2, image_url.trim());
            ps.setInt(3, warehouse_id);
            ps.setInt(4, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void updateRawFlower6(int raw_id, int raw_quantity, int hold, int import_price, int unit_price) {
        String sql = "UPDATE la_fioreria.raw_flower SET raw_quantity = ?, hold = ?, import_price = ?, unit_price = ? WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, raw_quantity);
            ps.setInt(2, hold);
            ps.setInt(3, import_price);
            ps.setInt(4, unit_price);
            ps.setInt(5, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void addRawFlower1(String raw_name, int unit_price, int warehouse_id, String image_url, int import_price) {
        String sql
                = "INSERT INTO la_fioreria.raw_flower "
                + "(raw_name, raw_quantity, unit_price, warehouse_id, image_url, hold, import_price, active) "
                + "VALUES (?, 0, ?, ?, ?, 0, ?, 1)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, raw_name.trim());
            ps.setInt(2, unit_price);
            ps.setInt(3, warehouse_id);
            ps.setString(4, image_url.trim());
            ps.setInt(5, import_price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public void addRawFlower(String raw_name, int raw_quantity, int unit_price, Date expiration_date,
            int warehouse_id, String image_url, int hold, int import_price) {
        String sql
                = "INSERT INTO la_fioreria.raw_flower "
                + "(raw_name, raw_quantity, unit_price, expiration_date, warehouse_id, image_url, hold, import_price, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, raw_name.trim());
            ps.setInt(2, raw_quantity);
            ps.setInt(3, unit_price);
            ps.setDate(4, expiration_date);
            ps.setInt(5, warehouse_id);
            ps.setString(6, image_url.trim());
            ps.setInt(7, hold);
            ps.setInt(8, import_price);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public RawFlower getRawFlowerById(int rawId) {
        String sql = "SELECT * FROM la_fioreria.raw_flower WHERE raw_id = ? AND active = 1";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, rawId);
            rs = ps.executeQuery();
            if (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setActive(rs.getBoolean("active"));

                return rf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public List<RawFlower> getRawFlower() {
        List<RawFlower> list = new ArrayList<>();
        String sql
                = "SELECT raw_id, raw_name, raw_quantity, unit_price, expiration_date, "
                + "       warehouse_id, image_url, import_price, active "
                + "FROM la_fioreria.raw_flower WHERE active = 1";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setActive(rs.getBoolean("active"));

                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public void hideRawFlower(int raw_id) {
        String sql = "UPDATE la_fioreria.raw_flower SET active = 0 WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public int getTotalRawFlowerCount() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM la_fioreria.raw_flower";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
        return total;
    }

    public void importRawFlower(int raw_id, int importPrice, int quantity) {
        String sql
                = "UPDATE la_fioreria.raw_flower "
                + "SET raw_quantity = raw_quantity + ?, import_price = ? "
                + "WHERE raw_id = ?";
        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, importPrice);
            ps.setInt(3, raw_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public List<RawFlower> searchRawFlowerByKeyword(String keyword) {
        List<RawFlower> list = new ArrayList<>();
        String sql = "SELECT * FROM la_fioreria.raw_flower WHERE raw_name LIKE ?";

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword.trim() + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setActive(rs.getBoolean("active"));

                list.add(rf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public List<RawFlower> getRawFlowerSorted(String sortBy, String sortOrder) {
        List<RawFlower> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT raw_id, raw_name, raw_quantity, unit_price, expiration_date, "
                + "       warehouse_id, image_url, hold, import_price, active "
                + "FROM la_fioreria.raw_flower WHERE active = 1"
        );

        if (sortBy != null && sortOrder != null) {
            if (!sortBy.matches("^(unit_price|import_price|raw_quantity)$")
                    || !sortOrder.matches("^(asc|desc)$")) {
                System.out.println("Invalid sortBy or sortOrder: sortBy=" + sortBy + ", sortOrder=" + sortOrder);
                return getRawFlower();
            }
            sql.append(" ORDER BY ");
            if (sortBy.equals("unit_price")) {
                sql.append("unit_price ").append(sortOrder.toUpperCase());
            } else if (sortBy.equals("raw_quantity")) {
                sql.append("raw_quantity ").append(sortOrder.toUpperCase());
            } else if (sortBy.equals("import_price")) {
                sql.append("import_price ").append(sortOrder.toUpperCase());
            }
        }

        try {
            connection = dbc.getConnection();
            ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                RawFlower rf = new RawFlower();
                WarehouseDAO wdao = new WarehouseDAO();

                rf.setRawId(rs.getInt("raw_id"));
                rf.setRawName(rs.getString("raw_name").trim());
                rf.setRawQuantity(rs.getInt("raw_quantity"));
                rf.setUnitPrice(rs.getInt("unit_price"));
                rf.setExpirationDate(rs.getString("expiration_date"));
                rf.setWarehouse(wdao.getWarehouseById(rs.getInt("warehouse_id")));
                rf.setImageUrl(rs.getString("image_url").trim());
                rf.setHold(rs.getInt("hold"));
                rf.setImportPrice(rs.getInt("import_price"));
                rf.setActive(rs.getBoolean("active"));

                list.add(rf);
            }
        } catch (SQLException e) {
            System.out.println("Error in getRawFlowerSorted: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                closeResources();
            } catch (Exception e) {
                // ignore
            }
        }

        return list;
    }

    public static void main(String[] args) {
        RawFlowerDAO dao = new RawFlowerDAO();
        System.out.println(dao.getRawFlowerSorted("import_price", "asc"));
    }
}
