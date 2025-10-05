package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByOrder(Order order);
    List<Payment> findByStatus(String status);
    List<Payment> findByPaymentMethod(String paymentMethod);
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findByAmountGreaterThan(double amount);
    List<Payment> findByAmountLessThan(double amount);
    List<Payment> findByOrderOrderId(String orderId);
    long countByStatus(String status);
}
