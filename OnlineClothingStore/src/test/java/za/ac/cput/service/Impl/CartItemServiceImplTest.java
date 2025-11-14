package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.factory.*;
import za.ac.cput.service.CartItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartItemServiceImplTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    private static User testUser;
    private static Cart testCart;
    private static Category testCategory;
    private static Product testProduct;
    private static CartItem testCartItem;

    @BeforeAll
    static void setUp() {
        testUser = UserFactory.createUser(
                "USR004",
                "cartitemtest",
                "cartitem@example.com",
                "Password123!",
                "+27821234570",
                "CUSTOMER",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null,
                null
        );

        testCart = CartFactory.buildCart("CART002", testUser);

        testCategory = CategoryFactory.buildCategory(
                "CAT002",
                "Electronics",
                "Electronic devices and accessories"
        );

        testProduct = ProductFactory.createProduct(
                "PROD001",
                "Smartphone",
                "Latest model smartphone",
                5999.99,
                50,
                "http://example.com/phone.jpg",
                testCategory
        );

        testCartItem = CartItemFactory.buildCartItem(
                "ITEM001",
                testCart,
                testProduct,
                2
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        // Save all dependent entities first
        User savedUser = userService.save(testUser);
        assertNotNull(savedUser);
        
        // Update testCart with saved user
        testCart = CartFactory.buildCart(testCart.getCartId(), savedUser);
        Cart savedCart = cartService.create(testCart);
        assertNotNull(savedCart);
        
        // Save category
        Category savedCategory = categoryService.create(testCategory);
        assertNotNull(savedCategory);
        
        // Update testProduct with saved category
        testProduct = ProductFactory.createProduct(
                testProduct.getProductId(),
                testProduct.getName(),
                testProduct.getDescription(),
                testProduct.getPrice(),
                testProduct.getStockQuantity(),
                testProduct.getImageUrl(),
                savedCategory
        );
        Product savedProduct = productService.save(testProduct);
        assertNotNull(savedProduct);
        
        // Update testCartItem with saved cart and product
        testCartItem = CartItemFactory.buildCartItem(
                testCartItem.getCartItemId(),
                savedCart,
                savedProduct,
                testCartItem.getQuantity()
        );
        
        CartItem created = cartItemService.create(testCartItem);
        assertNotNull(created);
        assertEquals(testCartItem.getCartItemId(), created.getCartItemId());
        assertEquals(2, created.getQuantity());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        CartItem read = cartItemService.read(testCartItem.getCartItemId());
        assertNotNull(read);
        assertEquals(testCartItem.getCartItemId(), read.getCartItemId());
        assertEquals(2, read.getQuantity());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        CartItem existing = cartItemService.read(testCartItem.getCartItemId());
        CartItem updated = new CartItem.Builder()
                .setCartItemId(existing.getCartItemId())
                .setCart(existing.getCart())
                .setProduct(existing.getProduct())
                .setQuantity(5)
                .build();

        CartItem result = cartItemService.update(updated);
        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testGetItemsByCartId() {
        List<CartItem> items = cartItemService.getItemsByCartId(testCart.getCartId());
        assertNotNull(items);
        assertFalse(items.isEmpty());
        System.out.println("Items in cart: " + items.size());
    }

    @Test
    @Order(5)
    void testGetItemByCartIdAndProductId() {
        CartItem item = cartItemService.getItemByCartIdAndProductId(
                testCart.getCartId(),
                testProduct.getProductId()
        );
        assertNotNull(item);
        assertEquals(testCartItem.getCartItemId(), item.getCartItemId());
        System.out.println("Item found: " + item);
    }

    @Test
    @Order(6)
    void testCountItemsByCart() {
        long count = cartItemService.countItemsByCart(testCart);
        assertTrue(count > 0);
        System.out.println("Items count: " + count);
    }

    @Test
    @Order(7)
    void testGetAll() {
        List<CartItem> items = cartItemService.getAll();
        assertNotNull(items);
        assertFalse(items.isEmpty());
        System.out.println("Total cart items: " + items.size());
    }

    @Test
    @Order(8)
    void testDelete() {
        cartItemService.delete(testCartItem.getCartItemId());
        CartItem deleted = cartItemService.read(testCartItem.getCartItemId());
        assertNull(deleted);
        System.out.println("Cart item deleted successfully");
    }
}