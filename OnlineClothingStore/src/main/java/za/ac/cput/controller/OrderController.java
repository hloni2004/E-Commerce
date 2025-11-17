package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.*;
import za.ac.cput.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AddressService addressService;
    private final InvoiceService invoiceService;
    private final EmailService emailService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, 
                          CartService cartService, CartItemService cartItemService,
                          AddressService addressService, InvoiceService invoiceService,
                          EmailService emailService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.addressService = addressService;
        this.invoiceService = invoiceService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        Order saved = orderService.save(order);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        try {
            // Get user
            User user = userService.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Get cart
            Cart cart = cartService.getCartByUserId(request.getUserId());
            if (cart == null) {
                return ResponseEntity.badRequest().body("Cart not found");
            }

            // Get cart items
            List<CartItem> cartItems = cartItemService.getItemsByCartId(cart.getCartId());
            if (cartItems.isEmpty()) {
                return ResponseEntity.badRequest().body("Cart is empty");
            }

            // Get or create shipping address
            Address shippingAddress = null;
            if (request.getAddressId() != null) {
                shippingAddress = addressService.read(request.getAddressId());
            }

            // Calculate total
            double total = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();

            // Create order
            String orderId = UUID.randomUUID().toString();
            Order order = new Order.Builder()
                    .setOrderId(orderId)
                    .setUser(user)
                    .setOrderDate(LocalDateTime.now())
                    .setStatus("PENDING")
                    .setTotalPrice(total)
                    .setShippingAddress(shippingAddress)
                    .build();

            Order savedOrder = orderService.save(order);

            // Create order items from cart items
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem.Builder()
                        .setOrderItemId(UUID.randomUUID().toString())
                        .setOrder(savedOrder)
                        .setProduct(cartItem.getProduct())
                        .setQuantity(cartItem.getQuantity())
                        .setPrice(cartItem.getProduct().getPrice())
                        .build();
                orderItems.add(orderItem);
            }

            // Save order with items
            Order completeOrder = new Order.Builder()
                    .setOrderId(savedOrder.getOrderId())
                    .setUser(savedOrder.getUser())
                    .setOrderDate(savedOrder.getOrderDate())
                    .setStatus(savedOrder.getStatus())
                    .setTotalPrice(savedOrder.getTotalPrice())
                    .setShippingAddress(savedOrder.getShippingAddress())
                    .setItems(orderItems)
                    .build();
            
            Order finalOrder = orderService.save(completeOrder);

            // Generate PDF invoice and send email (non-blocking, don't fail checkout)
            try {
                byte[] invoicePdf = invoiceService.generateInvoicePdf(finalOrder);
                if (invoicePdf.length > 0) {
                    emailService.sendOrderConfirmationEmail(
                            user.getEmail(),
                            orderId,
                            invoicePdf
                    );
                }
            } catch (Exception emailError) {
                System.err.println("✗ Failed to send email, but order was successful: " + emailError.getMessage());
                // Don't fail the checkout if email fails
            }

            // Clear cart
            for (CartItem item : cartItems) {
                cartItemService.delete(item.getCartItemId());
            }

            return ResponseEntity.ok(finalOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Checkout failed: " + e.getMessage());
        }
    }

    // DTO for checkout request
    public static class CheckoutRequest {
        private String userId;
        private String addressId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getAddressId() { return addressId; }
        public void setAddressId(String addressId) { this.addressId = addressId; }
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
