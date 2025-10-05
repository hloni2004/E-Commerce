package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

public class UserFactory {
    public static User createUser(
            String userId,
            String username,
            String email,
            String password,
            String phoneNumber,
            String role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<Address> addresses,
            Cart cart,
            List<Order> orders
    ) {
        // Add validation as needed
        return new User.Builder()
                .setUserId(userId)
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setPhoneNumber(phoneNumber)
                .setRole(role)
                .setCreatedAt(createdAt)
                .setUpdatedAt(updatedAt)
                .setAddresses(addresses)
                .setCart(cart)
                .setOrders(orders)
                .build();
    }
}
