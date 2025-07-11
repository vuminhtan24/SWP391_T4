/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LAPTOP
 */
public class CalculatorTest {

    public CalculatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        int a = 2;
        int b = 3;
        Calculator instance = new Calculator();
        int expResult = 5;
        int result = instance.add(a, b);
        assertEquals(expResult, result); // ✅ So sánh kết quả
    }

    /**
     * Test of isPositive method, of class Calculator.
     */
    @Test
    public void testIsPositive() {
        System.out.println("isPositive");
        Calculator instance = new Calculator();

        // ✅ Kiểm tra số dương → true
        assertTrue(instance.isPositive(10));

        // ✅ Kiểm tra số âm → false
        assertFalse(instance.isPositive(-1));

        // ✅ Kiểm tra số 0 → false (tuỳ cách bạn viết logic trong Calculator)
        assertFalse(instance.isPositive(0));
    }

}
