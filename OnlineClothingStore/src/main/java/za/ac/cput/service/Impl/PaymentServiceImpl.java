package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.PaymentRepository;
import za.ac.cput.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            // Log error without sensitive data
            throw new RuntimeException("Failed to save payment", e);
        }
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void deleteById(String paymentId) {
        try {
            paymentRepository.deleteById(paymentId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete payment", e);
        }
    }

    @Override
    public List<Payment> findByOrder(Order order) {
        return paymentRepository.findByOrder(order);
    }

    @Override
    public List<Payment> findByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    public List<Payment> findByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    @Override
    public List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByPaymentDateBetween(start, end);
    }

    @Override
    public List<Payment> findByAmountGreaterThan(double amount) {
        return paymentRepository.findByAmountGreaterThan(amount);
    }

    @Override
    public List<Payment> findByAmountLessThan(double amount) {
        return paymentRepository.findByAmountLessThan(amount);
    }

    @Override
    public List<Payment> findByOrderOrderId(String orderId) {
        return paymentRepository.findByOrderOrderId(orderId);
    }

    @Override
    public long countByStatus(String status) {
        return paymentRepository.countByStatus(status);
    }
}
