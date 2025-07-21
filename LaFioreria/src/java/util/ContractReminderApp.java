package util;

import dal.EmployeeDAO;
import model.EmployeeInfo;
import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class ContractReminderApp {

    private static final String SENDER_EMAIL = "dangquangkhai261104@gmail.com";
    private static final String APP_PASSWORD = "jafemfcyoapoyytm";
    private static final String MANAGER_EMAIL = "khaidhe186918@fpt.edu.vn";

    public static void main(String[] args) {
        System.out.println("🔔 Checking for contracts nearing expiration...");

        EmployeeDAO dao = new EmployeeDAO();
        List<EmployeeInfo> expiring = dao.getEmployeesExpiringInDays(3);

        if (expiring.isEmpty()) {
            System.out.println("📭 No employees with contracts expiring soon.");
            return;
        }

        StringBuilder html = new StringBuilder();
        html.append("<h2>⏰ List of Employees with Contracts Near Expiration</h2>");
        html.append("<table border='1' cellpadding='5' cellspacing='0'>");
        html.append("<tr><th>Employee Code</th><th>Department</th><th>Position</th><th>End Date</th></tr>");

        for (EmployeeInfo e : expiring) {
            html.append("<tr>")
                    .append("<td>").append(e.getEmployeeCode()).append("</td>")
                    .append("<td>").append(e.getDepartment()).append("</td>")
                    .append("<td>").append(e.getPosition()).append("</td>")
                    .append("<td>").append(e.getEndDate()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        html.append("<p>Best regards,<br>The LaFioreria System</p>");

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MANAGER_EMAIL));
            message.setSubject("📋 Reminder: List of Expiring Employee Contracts");
            message.setContent(html.toString(), "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✅ Reminder email sent to manager: " + MANAGER_EMAIL);

        } catch (MessagingException ex) {
            System.out.println("❌ Failed to send reminder email.");
            ex.printStackTrace();
        }

        System.out.println("✅ Check completed.");
    }
}
