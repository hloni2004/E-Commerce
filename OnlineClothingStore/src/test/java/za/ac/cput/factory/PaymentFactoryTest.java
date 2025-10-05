package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Payment;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFactoryTest {

    @Test
    void createPayment_withValidInput_shouldReturnPayment() {
        String paymentId = "PAY123";
        Order order = new Order.Builder().setOrderId("O1").build();
        double amount = 250.0;
        LocalDateTime paymentDate = LocalDateTime.now();
        String paymentMethod = "CREDIT_CARD";
        String status = "COMPLETED";

        Payment payment = PaymentFactory.createPayment(
                paymentId, order, amount, paymentDate, paymentMethod, status
        );

        assertNotNull(payment);
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(order, payment.getOrder());
        assertEquals(amount, payment.getAmount());
        assertEquals(paymentDate, payment.getPaymentDate());
        assertEquals(paymentMethod, payment.getPaymentMethod());
        assertEquals(status, payment.getStatus());
    }

    @Test
    void createPayment_withInvalidInput_shouldThrowException() {
        Order order = new Order.Builder().setOrderId("O1").build();
        LocalDateTime paymentDate = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "", order, 250.0, paymentDate, "CREDIT_CARD", "COMPLETED")
        );
        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "PAY123", null, 250.0, paymentDate, "CREDIT_CARD", "COMPLETED")
        );
        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "PAY123", order, -10.0, paymentDate, "CREDIT_CARD", "COMPLETED")
        );
        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "PAY123", order, 250.0, null, "CREDIT_CARD", "COMPLETED")
        );
        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "PAY123", order, 250.0, paymentDate, "", "COMPLETED")
        );
        assertThrows(IllegalArgumentException.class, () ->
                PaymentFactory.createPayment(
                        "PAY123", order, 250.0, paymentDate, "CREDIT_CARD", "")
        );
    }
}
