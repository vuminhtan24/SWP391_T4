package scheduler;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class SchedulerInitializer implements ServletContextListener {
    private static ScheduledExecutorService scheduler; // Static để duy nhất
    private final FlowerScheduler flowerScheduler = new FlowerScheduler(); // Instance duy nhất

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(flowerScheduler::checkFlowerBatches, 0, 86400, TimeUnit.SECONDS);
            System.out.println("FlowerScheduler started at " + new java.util.Date());
        } else {
            System.out.println("FlowerScheduler already running, skipping initialization.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("FlowerScheduler stopped at " + new java.util.Date());
        }
    }
}