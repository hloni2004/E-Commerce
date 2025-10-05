package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
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
