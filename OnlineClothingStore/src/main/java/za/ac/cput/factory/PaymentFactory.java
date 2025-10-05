package za.ac.cput.factory;

import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;
import za.ac.cput.util.Helper;

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
        if (!Helper.isNotEmpty(paymentId) ||
                order == null ||
                !Helper.isValidPrice(amount) ||
                paymentDate == null ||
                !Helper.isNotEmpty(paymentMethod) ||
                !Helper.isNotEmpty(status)) {
            throw new IllegalArgumentException("Invalid input for Payment creation");
        }

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
