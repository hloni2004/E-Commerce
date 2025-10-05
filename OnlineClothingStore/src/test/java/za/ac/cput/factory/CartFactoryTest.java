package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartFactoryTest {

    private User testUser;
    private List<CartItem> testItems;

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

        testItems = new ArrayList<>();
    }

    @Test
    void testBuildCart_WithItems_Success() {
        Cart cart = CartFactory.buildCart(
                "C001",
                testUser,
                testItems
        );

        assertNotNull(cart);
        assertEquals("C001", cart.getCartId());
        assertEquals(testUser, cart.getUser());
        assertEquals(testItems, cart.getItems());
    }

    @Test
    void testBuildCart_WithoutItems_Success() {
        Cart cart = CartFactory.buildCart(
                "C002",
                testUser,
                null
        );

        assertNotNull(cart);
        assertEquals("C002", cart.getCartId());
        assertEquals(testUser, cart.getUser());
        assertNull(cart.getItems());
    }

    @Test
    void testBuildCart_OverloadedMethod_Success() {
        Cart cart = CartFactory.buildCart(
                "C003",
                testUser
        );

        assertNotNull(cart);
        assertEquals("C003", cart.getCartId());
        assertEquals(testUser, cart.getUser());
        assertNull(cart.getItems());
    }

    @Test
    void testBuildCart_NullCartId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartFactory.buildCart(
                    null,
                    testUser,
                    testItems
            );
        });
        assertEquals("Cart ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCart_EmptyCartId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartFactory.buildCart(
                    "",
                    testUser,
                    testItems
            );
        });
        assertEquals("Cart ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCart_WhitespaceCartId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartFactory.buildCart(
                    "   ",
                    testUser,
                    testItems
            );
        });
        assertEquals("Cart ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCart_NullUser_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartFactory.buildCart(
                    "C001",
                    null,
                    testItems
            );
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void testBuildCart_OverloadedMethod_NullUser_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CartFactory.buildCart(
                    "C001",
                    null
            );
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void testBuildCart_EmptyItemsList_Success() {
        List<CartItem> emptyList = new ArrayList<>();

        Cart cart = CartFactory.buildCart(
                "C004",
                testUser,
                emptyList
        );

        assertNotNull(cart);
        assertEquals("C004", cart.getCartId());
        assertTrue(cart.getItems().isEmpty());
    }
}