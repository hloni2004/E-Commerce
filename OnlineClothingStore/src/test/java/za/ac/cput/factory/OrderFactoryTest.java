package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {

    @Test
    void createOrder() {
        String orderId = "ORD123";
        User user = new User.Builder().setUserId("U1").setUsername("testuser").setEmail("test@example.com")
                .setPassword("Password@1").setRole("CUSTOMER").build();
        LocalDateTime orderDate = LocalDateTime.now();
        String status = "PENDING";
        double totalPrice = 500.0;
        Address address = new Address.Builder().setAddressId("A1").setStreet("123 Main St").build();
        OrderItem item = new OrderItem.Builder().setOrderItemId("OI1").setQuantity(1).setPrice(500.0).build();
        List<OrderItem> items = Collections.singletonList(item);

        Order order = OrderFactory.createOrder(orderId, user, orderDate, status, totalPrice, address, items);

        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
        assertEquals(user, order.getUser());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(status, order.getStatus());
        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(address, order.getShippingAddress());
        assertEquals(items, order.getItems());
    }
}
