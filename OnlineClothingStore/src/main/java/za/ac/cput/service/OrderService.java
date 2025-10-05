package za.ac.cput.service;

import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    Optional<Order> findById(String orderId);
    List<Order> findAll();
    void deleteById(String orderId);

    List<Order> findByUser(User user);
    List<Order> findByStatus(String status);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByShippingAddress(Address address);
    List<Order> findByUserAndStatus(User user, String status);
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByStatusOrderByOrderDateAsc(String status);
    long countByStatus(String status);
    boolean existsByUserAndStatus(User user, String status);
    List<Order> findByItemsProductId(String productId);
    List<Order> findByItemsOrderItemId(String orderItemId);
}
