package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.service.OrderItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "*")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> save(@RequestBody OrderItem orderItem) {
        OrderItem saved = orderItemService.save(orderItem);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> findById(@PathVariable String id) {
        Optional<OrderItem> orderItem = orderItemService.findById(id);
        return orderItem.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> findAll() {
        List<OrderItem> orderItems = orderItemService.findAll();
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        orderItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> findByOrderId(@PathVariable String orderId) {
        List<OrderItem> orderItems = orderItemService.findByOrderOrderId(orderId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItem>> findByProductId(@PathVariable String productId) {
        List<OrderItem> orderItems = orderItemService.findByProductProductId(productId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<List<OrderItem>> findByQuantity(@PathVariable int quantity) {
        List<OrderItem> orderItems = orderItemService.findByQuantity(quantity);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<OrderItem>> findByPrice(@PathVariable double price) {
        List<OrderItem> orderItems = orderItemService.findByPrice(price);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/price/greater/{price}")
    public ResponseEntity<List<OrderItem>> findByPriceGreaterThan(@PathVariable double price) {
        List<OrderItem> orderItems = orderItemService.findByPriceGreaterThan(price);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/price/less/{price}")
    public ResponseEntity<List<OrderItem>> findByPriceLessThan(@PathVariable double price) {
        List<OrderItem> orderItems = orderItemService.findByPriceLessThan(price);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/price/range")
    public ResponseEntity<List<OrderItem>> findByPriceRange(@RequestParam double min, @RequestParam double max) {
        List<OrderItem> orderItems = orderItemService.findByPriceBetween(min, max);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }
}
