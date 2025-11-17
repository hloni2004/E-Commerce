package za.ac.cput.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String to, String orderNumber, byte[] invoicePdf) {
        if (mailSender == null) {
            System.out.println("⚠ Email service not configured, skipping email notification");
            return;
        }
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Order Confirmation - StyleHub #" + orderNumber);
            
            String emailContent = """
                <html>
                <body style='font-family: Arial, sans-serif;'>
                    <div style='max-width: 600px; margin: 0 auto; padding: 20px;'>
                        <h2 style='color: #333;'>Thank You for Your Order!</h2>
                        <p>Dear Customer,</p>
                        <p>Thank you for shopping with us at StyleHub. Your order has been successfully placed.</p>
                        <p><strong>Order Number:</strong> %s</p>
                        <p>Please find your order invoice attached as a PDF.</p>
                        <p>We will notify you when your order has been shipped.</p>
                        <br>
                        <p>Best regards,<br>The StyleHub Team</p>
                    </div>
                </body>
                </html>
                """.formatted(orderNumber);
            
            helper.setText(emailContent, true);

            // Attach PDF invoice
            helper.addAttachment("Invoice_" + orderNumber + ".pdf", new ByteArrayResource(invoicePdf));

            mailSender.send(message);
            System.out.println("✓ Order confirmation email sent to: " + to);
        } catch (MessagingException e) {
            System.err.println("✗ Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
