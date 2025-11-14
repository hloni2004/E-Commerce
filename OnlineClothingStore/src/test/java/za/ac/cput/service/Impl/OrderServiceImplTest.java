package za.ac.cput.service.Impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.OrderFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AddressServiceImpl addressService;

    private static Order order;
    private static User user;
    private static Address address;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U3", "orderuser", "order@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        address = AddressFactory.buildAddress("A1", "123 Main St", "City", "Province", 
                "1234", "Country", user);
        order = OrderFactory.createOrder("O1", user, LocalDateTime.now(), "PENDING", 
                100.0, address, new ArrayList<>());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void save() {
        User savedUser = userService.save(user);
        System.out.println("Saved user: " + savedUser);
        
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
        System.out.println("Saved address: " + savedAddress);
        
        // Verify address was actually saved
        Address retrievedAddress = addressService.read(savedAddress.getAddressId());
        assertNotNull(retrievedAddress, "Address should be persisted in database");
        System.out.println("Retrieved address from DB: " + retrievedAddress);
        
        Order orderWithSavedRefs = new Order.Builder()
                .setOrderId(order.getOrderId())
                .setUser(savedUser)
                .setOrderDate(order.getOrderDate())
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setShippingAddress(savedAddress)
                .setItems(order.getItems())
                .build();
        
        Order saved = orderService.save(orderWithSavedRefs);
        assertNotNull(saved);
        assertEquals(order.getOrderId(), saved.getOrderId());
        System.out.println("✓ Saved order to DB: " + saved);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        Optional<Order> found = orderService.findById("O1");
        assertTrue(found.isPresent());
        assertEquals(order.getStatus(), found.get().getStatus());
        System.out.println("Found order: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAll() {
        List<Order> all = orderService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All orders: " + all.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findByUser() {
        List<Order> result = orderService.findByUser(user);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Orders by user: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByStatus() {
        List<Order> result = orderService.findByStatus("PENDING");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Orders with PENDING status: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void findByOrderDateBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Order> result = orderService.findByOrderDateBetween(start, end);
        assertNotNull(result);
        System.out.println("Orders in date range: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void findByShippingAddress() {
        List<Order> result = orderService.findByShippingAddress(address);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Orders by address: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void findByUserAndStatus() {
        List<Order> result = orderService.findByUserAndStatus(user, "PENDING");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("User orders with PENDING: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void findByUserOrderByOrderDateDesc() {
        List<Order> result = orderService.findByUserOrderByOrderDateDesc(user);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("User orders ordered by date: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void findByStatusOrderByOrderDateAsc() {
        List<Order> result = orderService.findByStatusOrderByOrderDateAsc("PENDING");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Status orders ordered by date: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void countByStatus() {
        long count = orderService.countByStatus("PENDING");
        assertTrue(count > 0);
        System.out.println("Count of PENDING orders: " + count);
    }

    @Test
    @org.junit.jupiter.api.Order(12)
    void existsByUserAndStatus() {
        boolean exists = orderService.existsByUserAndStatus(user, "PENDING");
        assertTrue(exists);
        System.out.println("Exists by user and status: " + exists);
    }

    @Test
    @org.junit.jupiter.api.Order(13)
    void findByItemsProductId() {
        List<Order> result = orderService.findByItemsProductId("P1");
        assertNotNull(result);
        System.out.println("Orders with product: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(14)
    void findByItemsOrderItemId() {
        List<Order> result = orderService.findByItemsOrderItemId("OI1");
        assertNotNull(result);
        System.out.println("Orders with order item: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(15)
    void deleteById() {
        orderService.deleteById("O1");
        Optional<Order> deleted = orderService.findById("O1");
        assertFalse(deleted.isPresent());
        System.out.println("Order deleted successfully");
    }
}
