package za.ac.cput.service;

import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment save(Payment payment);
    Optional<Payment> findById(String paymentId);
    List<Payment> findAll();
    void deleteById(String paymentId);

    List<Payment> findByOrder(Order order);
    List<Payment> findByStatus(String status);
    List<Payment> findByPaymentMethod(String paymentMethod);
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findByAmountGreaterThan(double amount);
    List<Payment> findByAmountLessThan(double amount);
    List<Payment> findByOrderOrderId(String orderId);
    long countByStatus(String status);
}
