package za.ac.cput.service.Impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.*;
import za.ac.cput.factory.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemServiceImplTest {
    @Autowired
    private OrderItemServiceImpl orderItemService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AddressServiceImpl addressService;

    private static OrderItem orderItem;
    private static Order order;
    private static Product product;
    private static Category category;
    private static User user;
    private static Address address;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U6", "itemuser", "item@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        address = AddressFactory.buildAddress("A4", "101 Boulevard", "City", "Province", 
                "3456", "Country", user);
        order = OrderFactory.createOrder("O4", user, LocalDateTime.now(), "PENDING", 
                500.0, address, new ArrayList<>());
        category = CategoryFactory.buildCategory("C2", "Computers", "Computer equipment");
        product = ProductFactory.createProduct("P2", "Desktop", "Gaming desktop", 
                500.0, 5, "http://example.com/desktop.jpg", category);
        orderItem = OrderItemFactory.createOrderItem("OI1", order, product, 1, 500.0);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void save() {
        User savedUser = userService.save(user);
        assertNotNull(savedUser);
        System.out.println("✓ Saved user to DB: " + savedUser);
        
        Address addressWithSavedUser = new Address.Builder()
                .setAddressId(address.getAddressId())
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setProvince(address.getProvince())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .setUser(savedUser)
                .build();
        Address savedAddress = addressService.create(addressWithSavedUser);
        assertNotNull(savedAddress);
        System.out.println("✓ Saved address to DB: " + savedAddress);
        
        Order orderWithSavedRefs = new Order.Builder()
                .setOrderId(order.getOrderId())
                .setUser(savedUser)
                .setOrderDate(order.getOrderDate())
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setShippingAddress(savedAddress)
                .setItems(order.getItems())
                .build();
        Order savedOrder = orderService.save(orderWithSavedRefs);
        assertNotNull(savedOrder);
        System.out.println("✓ Saved order to DB: " + savedOrder);
        
        Category savedCategory = categoryService.create(category);
        assertNotNull(savedCategory);
        System.out.println("✓ Saved category to DB: " + savedCategory);
        
        Product productWithSavedCategory = new Product.Builder()
                .setProductId(product.getProductId())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setStockQuantity(product.getStockQuantity())
                .setImageUrl(product.getImageUrl())
                .setCategory(savedCategory)
                .build();
        Product savedProduct = productService.save(productWithSavedCategory);
        assertNotNull(savedProduct);
        System.out.println("✓ Saved product to DB: " + savedProduct);
        
        OrderItem orderItemWithSavedRefs = new OrderItem.Builder()
                .setOrderItemId(orderItem.getOrderItemId())
                .setOrder(savedOrder)
                .setProduct(savedProduct)
                .setQuantity(orderItem.getQuantity())
                .setPrice(orderItem.getPrice())
                .build();
        
        OrderItem saved = orderItemService.save(orderItemWithSavedRefs);
        assertNotNull(saved);
        assertEquals(orderItem.getOrderItemId(), saved.getOrderItemId());
        System.out.println("✓ Saved order item to DB: " + saved);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        Optional<OrderItem> found = orderItemService.findById("OI1");
        assertTrue(found.isPresent());
        assertEquals(orderItem.getQuantity(), found.get().getQuantity());
        System.out.println("Found order item: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAll() {
        List<OrderItem> all = orderItemService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All order items: " + all.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findByOrder() {
        List<OrderItem> result = orderItemService.findByOrder(order);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items by order: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByProduct() {
        List<OrderItem> result = orderItemService.findByProduct(product);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items by product: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void findByQuantity() {
        List<OrderItem> result = orderItemService.findByQuantity(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items with quantity 1: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void findByPrice() {
        List<OrderItem> result = orderItemService.findByPrice(500.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items with price 500: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void findByOrderOrderId() {
        List<OrderItem> result = orderItemService.findByOrderOrderId("O4");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items by order ID: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void findByProductProductId() {
        List<OrderItem> result = orderItemService.findByProductProductId("P2");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items by product ID: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void findByPriceGreaterThan() {
        List<OrderItem> result = orderItemService.findByPriceGreaterThan(400.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items price > 400: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(11)
    void findByPriceLessThan() {
        List<OrderItem> result = orderItemService.findByPriceLessThan(600.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items price < 600: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(12)
    void findByPriceBetween() {
        List<OrderItem> result = orderItemService.findByPriceBetween(400.0, 600.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Order items price 400-600: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(13)
    void deleteById() {
        orderItemService.deleteById("OI1");
        Optional<OrderItem> deleted = orderItemService.findById("OI1");
        assertFalse(deleted.isPresent());
        System.out.println("Order item deleted successfully");
    }
}
