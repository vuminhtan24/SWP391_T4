/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testcode;

import dal.FlowerDAO;
import dal.SalesDAO;
import java.util.Map;

/**
 *
 * @author LAPTOP
 */
public class TestUserDao {

    public static void main(String[] args) {
        SalesDAO dao = new SalesDAO();

        // Test 1: Tổng hợp tổn thất theo lý do
        System.out.println("=== Tổng hợp tổn thất theo lý do ===");
        Map<String, Integer> discardStats = dao.getDiscardReasonStats();
        for (Map.Entry<String, Integer> entry : discardStats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Test 2: So sánh nhập vs tổn thất
        System.out.println("\n=== So sánh hoa nhập vs tổn thất ===");
        Map<String, Integer> importVsWaste = dao.getTotalImportVsWaste();
        for (Map.Entry<String, Integer> entry : importVsWaste.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
