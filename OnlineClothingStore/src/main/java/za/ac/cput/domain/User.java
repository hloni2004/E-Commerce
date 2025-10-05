package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    protected String userId;

    @Column(name = "username", nullable = false, unique = true)
    protected String username;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "role", nullable = false)
    protected String role; // CUSTOMER / ADMIN

    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    protected Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<Order> orders;

    protected User() {}

    private User(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.role = builder.role;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.addresses = builder.addresses;
        this.cart = builder.cart;
        this.orders = builder.orders;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Address> getAddresses() { return addresses; }
    public Cart getCart() { return cart; }
    public List<Order> getOrders() { return orders; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static class Builder {
        private String userId;
        private String username;
        private String email;
        private String password;
        private String phoneNumber;
        private String role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Address> addresses;
        private Cart cart;
        private List<Order> orders;

        public Builder setUserId(String userId) { this.userId = userId; return this; }
        public Builder setUsername(String username) { this.username = username; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder setRole(String role) { this.role = role; return this; }
        public Builder setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder setAddresses(List<Address> addresses) { this.addresses = addresses; return this; }
        public Builder setCart(Cart cart) { this.cart = cart; return this; }
        public Builder setOrders(List<Order> orders) { this.orders = orders; return this; }

        public Builder copy(User user) {
            this.userId = user.userId;
            this.username = user.username;
            this.email = user.email;
            this.password = user.password;
            this.phoneNumber = user.phoneNumber;
            this.role = user.role;
            this.createdAt = user.createdAt;
            this.updatedAt = user.updatedAt;
            this.addresses = user.addresses;
            this.cart = user.cart;
            this.orders = user.orders;
            return this;
        }

        public User build() { return new User(this); }
    }
}
