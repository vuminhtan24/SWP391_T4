/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scheduler;

/**
 *
 * @author Admin
 */
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import scheduler.FlowerScheduler;

public class AppInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FlowerScheduler.main(null);
    }
}
