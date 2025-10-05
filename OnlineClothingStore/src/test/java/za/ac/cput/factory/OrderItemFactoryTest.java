package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemFactoryTest {

    @Test
    void createOrderItem() {
        String orderItemId = "OI123";
        Order order = new Order.Builder().setOrderId("O1").build();
        Product product = new Product.Builder().setProductId("P1").setName("Shirt").setPrice(100.0).setStockQuantity(10).setCategory(null).build();
        int quantity = 2;
        double price = 200.0;

        OrderItem orderItem = OrderItemFactory.createOrderItem(orderItemId, order, product, quantity, price);

        assertNotNull(orderItem);
        assertEquals(orderItemId, orderItem.getOrderItemId());
        assertEquals(order, orderItem.getOrder());
        assertEquals(product, orderItem.getProduct());
        assertEquals(quantity, orderItem.getQuantity());
        assertEquals(price, orderItem.getPrice());
    }
}
