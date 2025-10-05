package za.ac.cput.service;

import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(String orderItemId);
    List<OrderItem> findAll();
    void deleteById(String orderItemId);

    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByQuantity(int quantity);
    List<OrderItem> findByPrice(double price);
    List<OrderItem> findByOrderOrderId(String orderId);
    List<OrderItem> findByProductProductId(String productId);
    List<OrderItem> findByPriceGreaterThan(double price);
    List<OrderItem> findByPriceLessThan(double price);
    List<OrderItem> findByPriceBetween(double minPrice, double maxPrice);
}
