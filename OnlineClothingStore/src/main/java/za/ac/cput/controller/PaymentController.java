package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Payment;
import za.ac.cput.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> save(@RequestBody Payment payment) {
        Payment saved = paymentService.save(payment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable String id) {
        Optional<Payment> payment = paymentService.findById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> payments = paymentService.findAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        paymentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> findByOrderId(@PathVariable String orderId) {
        List<Payment> payments = paymentService.findByOrderOrderId(orderId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> findByStatus(@PathVariable String status) {
        List<Payment> payments = paymentService.findByStatus(status);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<Payment>> findByPaymentMethod(@PathVariable String method) {
        List<Payment> payments = paymentService.findByPaymentMethod(method);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Payment>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Payment> payments = paymentService.findByPaymentDateBetween(start, end);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/amount/greater/{amount}")
    public ResponseEntity<List<Payment>> findByAmountGreaterThan(@PathVariable double amount) {
        List<Payment> payments = paymentService.findByAmountGreaterThan(amount);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/amount/less/{amount}")
    public ResponseEntity<List<Payment>> findByAmountLessThan(@PathVariable double amount) {
        List<Payment> payments = paymentService.findByAmountLessThan(amount);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countByStatus(@PathVariable String status) {
        long count = paymentService.countByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
