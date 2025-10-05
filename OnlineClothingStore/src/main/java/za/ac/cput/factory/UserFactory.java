package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        if (userId == null || userId.isEmpty())
            throw new IllegalArgumentException("User ID is required");
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username is required");
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email is required");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password is required");
        if (role == null || role.isEmpty())
            throw new IllegalArgumentException("Role is required");
        if (createdAt == null)
            throw new IllegalArgumentException("CreatedAt is required");
        if (updatedAt == null)
            throw new IllegalArgumentException("UpdatedAt is required");

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
