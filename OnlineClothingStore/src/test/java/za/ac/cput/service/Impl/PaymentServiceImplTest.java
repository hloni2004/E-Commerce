package za.ac.cput.service.Impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.factory.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceImplTest {
    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AddressServiceImpl addressService;

    private static Payment payment;
    private static Order order;
    private static User user;
    private static Address address;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U4", "paymentuser", "payment@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        address = AddressFactory.buildAddress("A2", "456 Street", "City", "Province", 
                "5678", "Country", user);
        order = OrderFactory.createOrder("O2", user, LocalDateTime.now(), "PENDING", 
                100.0, address, new ArrayList<>());
        payment = PaymentFactory.createPayment("PAY1", order, 100.0, LocalDateTime.now(), 
                "CARD", "SUCCESS");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void save() {
        User savedUser = userService.save(user);
        assertNotNull(savedUser);
        System.out.println("✓ Saved user to DB: " + savedUser);
        
        Address addressWithSavedUser = new Address.Builder()
                .setAddressId(address.getAddressId())
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setProvince(address.getProvince())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .setUser(savedUser)
                .build();
        Address savedAddress = addressService.create(addressWithSavedUser);
        assertNotNull(savedAddress);
        System.out.println("✓ Saved address to DB: " + savedAddress);
        
        Order orderWithSavedRefs = new Order.Builder()
                .setOrderId(order.getOrderId())
                .setUser(savedUser)
                .setOrderDate(order.getOrderDate())
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setShippingAddress(savedAddress)
                .setItems(order.getItems())
                .build();
        Order savedOrder = orderService.save(orderWithSavedRefs);
        assertNotNull(savedOrder);
        System.out.println("✓ Saved order to DB: " + savedOrder);
        
        Payment paymentWithSavedOrder = new Payment.Builder()
                .setPaymentId(payment.getPaymentId())
                .setOrder(savedOrder)
                .setAmount(payment.getAmount())
                .setPaymentDate(payment.getPaymentDate())
                .setPaymentMethod(payment.getPaymentMethod())
                .setStatus(payment.getStatus())
                .build();
        
        Payment saved = paymentService.save(paymentWithSavedOrder);
        assertNotNull(saved);
        assertEquals(payment.getPaymentId(), saved.getPaymentId());
        System.out.println("✓ Saved payment to DB: " + saved);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        Optional<Payment> found = paymentService.findById("PAY1");
        assertTrue(found.isPresent());
        assertEquals(payment.getStatus(), found.get().getStatus());
        System.out.println("Found payment: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAll() {
        List<Payment> all = paymentService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All payments: " + all.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findByOrder() {
        List<Payment> result = paymentService.findByOrder(order);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Payments by order: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByStatus() {
        List<Payment> result = paymentService.findByStatus("SUCCESS");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Payments with SUCCESS status: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void findByPaymentMethod() {
        List<Payment> result = paymentService.findByPaymentMethod("CARD");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Card payments: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void findByPaymentDateBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Payment> result = paymentService.findByPaymentDateBetween(start, end);
        assertNotNull(result);
        System.out.println("Payments in date range: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void findByAmountGreaterThan() {
        List<Payment> result = paymentService.findByAmountGreaterThan(50.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Payments > 50: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void findByAmountLessThan() {
        List<Payment> result = paymentService.findByAmountLessThan(200.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Payments < 200: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void findByOrderOrderId() {
        List<Payment> result = paymentService.findByOrderOrderId("O2");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Payments by order ID: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void countByStatus() {
        long count = paymentService.countByStatus("SUCCESS");
        assertTrue(count > 0);
        System.out.println("Count of SUCCESS payments: " + count);
    }

    @Test
    @org.junit.jupiter.api.Order(12)
    void deleteById() {
        paymentService.deleteById("PAY1");
        Optional<Payment> deleted = paymentService.findById("PAY1");
        assertFalse(deleted.isPresent());
        System.out.println("Payment deleted successfully");
    }
}
