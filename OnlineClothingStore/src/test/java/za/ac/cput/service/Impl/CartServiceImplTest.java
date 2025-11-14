package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceImplTest {
    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private UserServiceImpl userService;

    private static Cart cart;
    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U2", "cartuser", "cart@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        cart = CartFactory.buildCart("CART1", user, new ArrayList<>());
    }

    @Test
    @Order(1)
    void create() {
        userService.save(user);
        Cart created = cartService.create(cart);
        assertNotNull(created);
        assertEquals(cart.getCartId(), created.getCartId());
        System.out.println("Created cart: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Cart found = cartService.read("CART1");
        assertNotNull(found);
        assertEquals(cart.getCartId(), found.getCartId());
        System.out.println("Found cart: " + found);
    }

    @Test
    @Order(3)
    void update() {
        Cart updated = cartService.update(cart);
        assertNotNull(updated);
        assertEquals(cart.getCartId(), updated.getCartId());
        System.out.println("Updated cart: " + updated);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Cart> all = cartService.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All carts: " + all.size());
    }

    @Test
    @Order(5)
    void getCartByUser() {
        Cart found = cartService.getCartByUser(user);
        assertNotNull(found);
        assertEquals(cart.getCartId(), found.getCartId());
        System.out.println("Cart by user: " + found);
    }

    @Test
    @Order(6)
    void getCartByUserId() {
        Cart found = cartService.getCartByUserId("U2");
        assertNotNull(found);
        assertEquals(cart.getCartId(), found.getCartId());
        System.out.println("Cart by user ID: " + found);
    }

    @Test
    @Order(7)
    void existsCartForUser() {
        boolean exists = cartService.existsCartForUser(user);
        assertTrue(exists);
        System.out.println("Cart exists for user: " + exists);
    }

    @Test
    @Order(8)
    void delete() {
        cartService.delete("CART1");
        Cart deleted = cartService.read("CART1");
        assertNull(deleted);
        System.out.println("Cart deleted successfully");
    }
}