package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.OrderItemRepository;
import za.ac.cput.service.OrderItemService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> findById(String orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public void deleteById(String orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    @Override
    public List<OrderItem> findByProduct(Product product) {
        return orderItemRepository.findByProduct(product);
    }

    @Override
    public List<OrderItem> findByQuantity(int quantity) {
        return orderItemRepository.findByQuantity(quantity);
    }

    @Override
    public List<OrderItem> findByPrice(double price) {
        return orderItemRepository.findByPrice(price);
    }

    @Override
    public List<OrderItem> findByOrderOrderId(String orderId) {
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    @Override
    public List<OrderItem> findByProductProductId(String productId) {
        return orderItemRepository.findByProductProductId(productId);
    }

    @Override
    public List<OrderItem> findByPriceGreaterThan(double price) {
        return orderItemRepository.findByPriceGreaterThan(price);
    }

    @Override
    public List<OrderItem> findByPriceLessThan(double price) {
        return orderItemRepository.findByPriceLessThan(price);
    }

    @Override
    public List<OrderItem> findByPriceBetween(double minPrice, double maxPrice) {
        return orderItemRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
