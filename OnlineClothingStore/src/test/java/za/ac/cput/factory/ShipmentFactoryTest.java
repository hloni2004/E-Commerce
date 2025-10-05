package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentFactoryTest {

    private Order createValidOrder() {
        User user = new User.Builder()
                .setUserId("U1")
                .setUsername("testuser")
                .setEmail("test@example.com")
                .setPassword("Password@1")
                .setRole("CUSTOMER")
                .build();
        Address address = new Address.Builder()
                .setAddressId("A1")
                .setStreet("123 Main St")
                .build();
        OrderItem item = new OrderItem.Builder()
                .setOrderItemId("OI1")
                .setQuantity(1)
                .setPrice(500.0)
                .build();
        List<OrderItem> items = Collections.singletonList(item);

        return OrderFactory.createOrder(
                "ORD123", user, LocalDateTime.now(), "PENDING", 500.0, address, items
        );
    }

    @Test
    void createShipment_validInput_success() {
        Order order = createValidOrder();
        String shipmentId = "S123";
        String trackingNumber = "TRACK123";
        String carrier = "DHL";
        String status = "Shipped";
        LocalDateTime shippedDate = LocalDateTime.now();
        LocalDateTime deliveryDate = shippedDate.plusDays(2);

        Shipment shipment = ShipmentFactory.createShipment(
                shipmentId, order, trackingNumber, carrier, status, shippedDate, deliveryDate
        );

        assertNotNull(shipment);
        assertEquals(shipmentId, shipment.getShipmentId());
        assertEquals(order, shipment.getOrder());
        assertEquals(trackingNumber, shipment.getTrackingNumber());
        assertEquals(carrier, shipment.getCarrier());
        assertEquals(status, shipment.getStatus());
        assertEquals(shippedDate, shipment.getShippedDate());
        assertEquals(deliveryDate, shipment.getDeliveryDate());
    }

    @Test
    void createShipment_nullShipmentId_throwsException() {
        Order order = createValidOrder();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ShipmentFactory.createShipment(
                        null, order, "TRACK", "DHL", "Shipped", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
                )
        );
        assertEquals("Shipment ID is required", exception.getMessage());
    }

    @Test
    void createShipment_nullOrder_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ShipmentFactory.createShipment(
                        "S1", null, "TRACK", "DHL", "Shipped", LocalDateTime.now(), LocalDateTime.now().plusDays(1)
                )
        );
        assertEquals("Order is required", exception.getMessage());
    }

    // Add more tests for other validations as needed
}
