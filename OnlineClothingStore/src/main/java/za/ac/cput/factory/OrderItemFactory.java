package za.ac.cput.factory;

import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;

public class OrderItemFactory {
    public static OrderItem createOrderItem(
            String orderItemId,
            Order order,
            Product product,
            int quantity,
            double price
    ) {
        // Add validation as needed
        return new OrderItem.Builder()
                .setOrderItemId(orderItemId)
                .setOrder(order)
                .setProduct(product)
                .setQuantity(quantity)
                .setPrice(price)
                .build();
    }
}
