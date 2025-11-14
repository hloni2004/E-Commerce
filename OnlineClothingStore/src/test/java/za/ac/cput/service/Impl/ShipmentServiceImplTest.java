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
class ShipmentServiceImplTest {
    @Autowired
    private ShipmentServiceImpl shipmentService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AddressServiceImpl addressService;

    private static Shipment shipment;
    private static Order order;
    private static User user;
    private static Address address;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U5", "shipmentuser", "shipment@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        address = AddressFactory.buildAddress("A3", "789 Avenue", "City", "Province", 
                "9012", "Country", user);
        order = OrderFactory.createOrder("O3", user, LocalDateTime.now(), "SHIPPED", 
                150.0, address, new ArrayList<>());
        shipment = ShipmentFactory.createShipment("SHIP1", order, "TRACK123", "FedEx", 
                "IN_TRANSIT", LocalDateTime.now(), LocalDateTime.now().plusDays(3));
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
        
        Shipment shipmentWithSavedOrder = new Shipment.Builder()
                .setShipmentId(shipment.getShipmentId())
                .setOrder(savedOrder)
                .setTrackingNumber(shipment.getTrackingNumber())
                .setCarrier(shipment.getCarrier())
                .setStatus(shipment.getStatus())
                .setShippedDate(shipment.getShippedDate())
                .setDeliveryDate(shipment.getDeliveryDate())
                .build();
        
        Shipment saved = shipmentService.save(shipmentWithSavedOrder);
        assertNotNull(saved);
        assertEquals(shipment.getShipmentId(), saved.getShipmentId());
        System.out.println("✓ Saved shipment to DB: " + saved);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        Optional<Shipment> found = shipmentService.findById("SHIP1");
        assertTrue(found.isPresent());
        assertEquals(shipment.getStatus(), found.get().getStatus());
        System.out.println("Found shipment: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAll() {
        List<Shipment> all = shipmentService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All shipments: " + all.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findByOrder() {
        Optional<Shipment> found = shipmentService.findByOrder(order);
        assertTrue(found.isPresent());
        assertEquals(shipment.getShipmentId(), found.get().getShipmentId());
        System.out.println("Shipment by order: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByStatus() {
        List<Shipment> result = shipmentService.findByStatus("IN_TRANSIT");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Shipments IN_TRANSIT: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void findByCarrier() {
        List<Shipment> result = shipmentService.findByCarrier("FedEx");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("FedEx shipments: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void findByTrackingNumber() {
        Optional<Shipment> found = shipmentService.findByTrackingNumber("TRACK123");
        assertTrue(found.isPresent());
        assertEquals(shipment.getShipmentId(), found.get().getShipmentId());
        System.out.println("Shipment by tracking: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void findByShippedDateBetween() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Shipment> result = shipmentService.findByShippedDateBetween(start, end);
        assertNotNull(result);
        System.out.println("Shipments in date range: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void findByDeliveryDateBetween() {
        LocalDateTime start = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(5);
        List<Shipment> result = shipmentService.findByDeliveryDateBetween(start, end);
        assertNotNull(result);
        System.out.println("Deliveries in date range: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void findByStatusOrderByDeliveryDateAsc() {
        List<Shipment> result = shipmentService.findByStatusOrderByDeliveryDateAsc("IN_TRANSIT");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Shipments ordered by delivery: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void deleteById() {
        shipmentService.deleteById("SHIP1");
        Optional<Shipment> deleted = shipmentService.findById("SHIP1");
        assertFalse(deleted.isPresent());
        System.out.println("Shipment deleted successfully");
    }
}
