package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
    List<Order> findByStatus(String status);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByShippingAddress(Address address);
    List<Order> findByUserAndStatus(User user, String status);
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByStatusOrderByOrderDateAsc(String status);
    long countByStatus(String status);
    boolean existsByUserAndStatus(User user, String status);
    List<Order> findByItems_ProductId(String productId);
    List<Order> findByItems_OrderItemId(String orderItemId);
}
