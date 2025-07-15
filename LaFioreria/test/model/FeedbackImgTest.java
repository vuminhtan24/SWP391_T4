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
public class FeedbackImgTest {
    
    public FeedbackImgTest() {
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

    /**
     * Test of getFeedbackId method, of class FeedbackImg.
     */
    @Test
    public void testGetFeedbackId() {
        System.out.println("getFeedbackId");
        FeedbackImg instance = new FeedbackImg();
        int expResult = 0;
        int result = instance.getFeedbackId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFeedbackId method, of class FeedbackImg.
     */
    @Test
    public void testSetFeedbackId() {
        System.out.println("setFeedbackId");
        int feedbackId = 0;
        FeedbackImg instance = new FeedbackImg();
        instance.setFeedbackId(feedbackId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImageUrl method, of class FeedbackImg.
     */
    @Test
    public void testGetImageUrl() {
        System.out.println("getImageUrl");
        FeedbackImg instance = new FeedbackImg();
        String expResult = "";
        String result = instance.getImageUrl();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setImageUrl method, of class FeedbackImg.
     */
    @Test
    public void testSetImageUrl() {
        System.out.println("setImageUrl");
        String imageUrl = "";
        FeedbackImg instance = new FeedbackImg();
        instance.setImageUrl(imageUrl);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class FeedbackImg.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        FeedbackImg instance = new FeedbackImg();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
