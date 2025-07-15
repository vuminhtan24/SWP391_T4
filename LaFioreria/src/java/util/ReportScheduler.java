package util;

import dal.SalesDAO;
import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class ReportScheduler {

    private static final String SENDER_EMAIL = "dangquangkhai261104@gmail.com";    // Email gửi
    private static final String APP_PASSWORD = "jafemfcyoapoyytm";                 // App password

    // ✅ Danh sách email nhận báo cáo
    private static final String[] RECIPIENTS = {
        "khaidhe186918@fpt.edu.vn",
        "leninmactuan@gmail.com"
    };

    public static void sendDailyReport() {
        try {
            // 1. Tạo nội dung email
            String subject = "📊 Báo cáo doanh thu ngày " + java.time.LocalDate.now();
            String content = buildReportContent();

            // 2. Cấu hình Gmail SMTP
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // 3. Xác thực Gmail
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });

            // 4. Gửi cho từng email
            for (String recipient : RECIPIENTS) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setContent(content, "text/html; charset=UTF-8");

                Transport.send(message);
                System.out.println("✅ Đã gửi báo cáo tới: " + recipient);
            }

        } catch (MessagingException e) {
            System.out.println("❌ Gửi email thất bại.");
        }
    }

    private static String buildReportContent() {
        SalesDAO dao = new SalesDAO();

        int todayOrders = dao.getTodayOrderCount();
        double thisMonthRevenue = dao.getThisMonthRevenue();
        double totalRevenue = dao.getTotalRevenue();

        // Nội dung HTML
        return "<h2>📈 Báo cáo doanh thu</h2>"
                + "<ul>"
                + "<li><strong>Đơn hôm nay:</strong> " + todayOrders + "</li>"
                + "<li><strong>Doanh thu tháng:</strong> " + thisMonthRevenue + " VND</li>"
                + "<li><strong>Tổng doanh thu:</strong> " + totalRevenue + " VND</li>"
                + "</ul>"
                + "<p>Trân trọng,<br>Hệ thống LaFioreria</p>";
    }

    public static void scheduleDailyReport() {
        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DATE, 1);
        }

        Date firstTime = calendar.getTime();
        long period = 24 * 60 * 60 * 1000;

        timer.schedule(new TimerTask() {
            public void run() {
                sendDailyReport();
            }
        }, firstTime, period);

        System.out.println("🕒 Đã lên lịch gửi email tự động hàng ngày lúc 7h sáng.");
    }
}
