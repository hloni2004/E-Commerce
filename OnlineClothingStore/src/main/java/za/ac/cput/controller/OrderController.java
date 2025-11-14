package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Order;
import za.ac.cput.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        Order saved = orderService.save(order);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable String id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> findByUserId(@PathVariable String userId) {
        // Note: Service expects User object, this endpoint may need UserService to fetch user first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> findByStatus(@PathVariable String status) {
        List<Order> orders = orderService.findByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Order>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Order> orders = orderService.findByOrderDateBetween(start, end);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> findByUserAndStatus(@PathVariable String userId, @PathVariable String status) {
        // Note: Service expects User object, this endpoint may need UserService to fetch user first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<Order>> findUserOrdersRecent(@PathVariable String userId) {
        // Note: Service expects User object, this endpoint may need UserService to fetch user first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/status/{status}/ordered")
    public ResponseEntity<List<Order>> findByStatusOrdered(@PathVariable String status) {
        List<Order> orders = orderService.findByStatusOrderByOrderDateAsc(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countByStatus(@PathVariable String status) {
        long count = orderService.countByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> findByProductId(@PathVariable String productId) {
        List<Order> orders = orderService.findByItemsProductId(productId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
