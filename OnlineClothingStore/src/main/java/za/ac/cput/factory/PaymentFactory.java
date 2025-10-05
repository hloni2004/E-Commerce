package za.ac.cput.factory;

import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;

public class PaymentFactory {
    public static Payment createPayment(
            String paymentId,
            Order order,
            double amount,
            LocalDateTime paymentDate,
            String paymentMethod,
            String status
    ) {
        // Add validation as needed
        return new Payment.Builder()
                .setPaymentId(paymentId)
                .setOrder(order)
                .setAmount(amount)
                .setPaymentDate(paymentDate)
                .setPaymentMethod(paymentMethod)
                .setStatus(status)
                .build();
    }
}
