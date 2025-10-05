package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // Find all orders for a specific user
    List<Order> findByUser(User user);

    // Find orders by user ID
    List<Order> findByUser_UserId(String userId);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders by date range
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find orders by user and status
    List<Order> findByUserAndStatus(User user, String status);

    // Find orders by user and date range
    List<Order> findByUserAndOrderDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    // Find orders containing a specific product - FIXED
    List<Order> findByItems_Product_ProductId(String productId);

    // Count orders by user
    long countByUser(User user);

    // Count orders by status
    long countByStatus(String status);

    // Find recent orders ordered by date descending
    List<Order> findTop10ByOrderByOrderDateDesc();
}