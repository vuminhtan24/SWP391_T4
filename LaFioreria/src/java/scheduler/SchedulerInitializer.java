package scheduler;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class SchedulerInitializer implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Khởi động scheduler khi ứng dụng bắt đầu
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new FlowerScheduler()::checkFlowerBatches, 0, 86400, TimeUnit.SECONDS);
        System.out.println("FlowerScheduler đã được khởi động.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Tắt scheduler khi ứng dụng dừng
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
            System.out.println("FlowerScheduler đã được dừng.");
        }
    }
}