package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private Address createValidAddress() {
        return new Address.Builder()
                .setAddressId("A1")
                .setStreet("123 Main St")
                .build();
    }

    private Cart createValidCart() {
        return new Cart.Builder()
                .setCartId("C1")
                .build();
    }

    private Order createValidOrder() {
        return new Order.Builder()
                .setOrderId("O1")
                .setUser(null)
                .setOrderDate(LocalDateTime.now())
                .setStatus("PENDING")
                .setTotalPrice(100.0)
                .setShippingAddress(null)
                .setItems(Collections.emptyList())
                .build();
    }

    @Test
    void createUser_validInput_success() {
        List<Address> addresses = Collections.singletonList(createValidAddress());
        Cart cart = createValidCart();
        List<Order> orders = Collections.singletonList(createValidOrder());

        User user = UserFactory.createUser(
                "U1",
                "testuser",
                "test@example.com",
                "Password@1",
                "0123456789",
                "CUSTOMER",
                LocalDateTime.now(),
                LocalDateTime.now(),
                addresses,
                cart,
                orders
        );

        assertNotNull(user);
        assertEquals("U1", user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("CUSTOMER", user.getRole());
        assertEquals(addresses, user.getAddresses());
        assertEquals(cart, user.getCart());
        assertEquals(orders, user.getOrders());
    }

    @Test
    void createUser_nullUserId_throwsException() {
        List<Address> addresses = Collections.singletonList(createValidAddress());
        Cart cart = createValidCart();
        List<Order> orders = Collections.singletonList(createValidOrder());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                UserFactory.createUser(
                        null,
                        "testuser",
                        "test@example.com",
                        "Password@1",
                        "0123456789",
                        "CUSTOMER",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        addresses,
                        cart,
                        orders
                )
        );
        assertEquals("User ID is required", exception.getMessage());
    }

    // Add similar tests for other required fields if needed
}
