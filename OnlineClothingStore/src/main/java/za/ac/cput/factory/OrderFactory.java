package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {
    public static Order createOrder(
            String orderId,
            User user,
            LocalDateTime orderDate,
            String status,
            double totalPrice,
            Address shippingAddress,
            List<OrderItem> items
    ) {
        // Add validation as needed
        return new Order.Builder()
                .setOrderId(orderId)
                .setUser(user)
                .setOrderDate(orderDate)
                .setStatus(status)
                .setTotalPrice(totalPrice)
                .setShippingAddress(shippingAddress)
                .setItems(items)
                .build();
    }
}
