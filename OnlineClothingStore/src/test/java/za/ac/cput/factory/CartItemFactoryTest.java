package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CartItemFactoryTest {

    private Cart testCart;
    private Product testProduct;
    private User testUser;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testUser = new User.Builder()
                .setUserId("U001")
                .setUsername("testuser")
                .setEmail("test@example.com")
                .setPassword("Test@1234")
                .setRole("CUSTOMER")
                .setCreatedAt(LocalDateTime.now())
                .build();

        testCart = new Cart.Builder()
                .setCartId("C001")
                .setUser(testUser)
                .build();

        testCategory = new Category.Builder()
                .setCategoryId("CAT001")
                .setCategoryName("Shirts")
                .build();

        testProduct = new Product.Builder()
                .setProductId("P001")
                .setName("T-Shirt")
                .setDescription("Cotton T-Shirt")
                .setPrice(299.99)
                .setStockQuantity(50)
                .setCategory(testCategory)
                .build();
    }

    @Test
    void testBuildCartItem_Success() {
        CartItem cartItem = CartItemFactory.buildCartItem(
                "CI001",
                testCart,
                testProduct,
                5
        );

        assertNotNull(cartItem);
        assertEquals("CI001", cartItem.getCartItemId());
        assertEquals(testCart, cartItem.getCart());
        assertEquals(testProduct, cartItem.getProduct());
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    void testBuildCartItem_MaxQuantity_Success() {
        CartItem cartItem = CartItemFactory.buildCartItem(
                "CI002",
                testCart,
                testProduct,
                50
        );

        assertNotNull(cartItem);
        assertEquals(50, cartItem.getQuantity());
    }

    @Test
    void testBuildCartItem_NullCartItemId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    null,
                    testCart,
                    testProduct,
                    5
            );
        });
        assertEquals("Cart Item ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCartItem_EmptyCartItemId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "",
                    testCart,
                    testProduct,
                    5
            );
        });
        assertEquals("Cart Item ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCartItem_NullCart_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    null,
                    testProduct,
                    5
            );
        });
        assertEquals("Cart cannot be null", exception.getMessage());
    }

    @Test
    void testBuildCartItem_NullProduct_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    null,
                    5
            );
        });
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void testBuildCartItem_ZeroQuantity_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    testProduct,
                    0
            );
        });
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void testBuildCartItem_NegativeQuantity_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    testProduct,
                    -5
            );
        });
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void testBuildCartItem_QuantityExceedsStock_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    testProduct,
                    51
            );
        });
        assertEquals("Quantity exceeds available stock", exception.getMessage());
    }

    @Test
    void testBuildCartItem_QuantityFarExceedsStock_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    testProduct,
                    100
            );
        });
        assertEquals("Quantity exceeds available stock", exception.getMessage());
    }

    @Test
    void testBuildCartItem_ProductWithZeroStock_ThrowsException() {
        Product outOfStockProduct = new Product.Builder()
                .setProductId("P002")
                .setName("Out of Stock Item")
                .setPrice(199.99)
                .setStockQuantity(0)
                .setCategory(testCategory)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartItemFactory.buildCartItem(
                    "CI001",
                    testCart,
                    outOfStockProduct,
                    1
            );
        });
        assertEquals("Quantity exceeds available stock", exception.getMessage());
    }
}