package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Override
    public List<Order> findByShippingAddress(Address address) {
        return orderRepository.findByShippingAddress(address);
    }

    @Override
    public List<Order> findByUserAndStatus(User user, String status) {
        return orderRepository.findByUserAndStatus(user, status);
    }

    @Override
    public List<Order> findByUserOrderByOrderDateDesc(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    @Override
    public List<Order> findByStatusOrderByOrderDateAsc(String status) {
        return orderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    @Override
    public long countByStatus(String status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public boolean existsByUserAndStatus(User user, String status) {
        return orderRepository.existsByUserAndStatus(user, status);
    }

    @Override
    public List<Order> findByItemsProductId(String productId) {
        return orderRepository.findByItems_ProductId(productId);
    }

    @Override
    public List<Order> findByItemsOrderItemId(String orderItemId) {
        return orderRepository.findByItems_OrderItemId(orderItemId);
    }
}
