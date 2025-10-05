package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status; // PENDING, PAID, SHIPPED, DELIVERED, CANCELED

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> items;

    protected Order() {}

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.user = builder.user;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.totalPrice = builder.totalPrice;
        this.shippingAddress = builder.shippingAddress;
        this.items = builder.items;
    }

    public String getOrderId() { return orderId; }
    public User getUser() { return user; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }
    public Address getShippingAddress() { return shippingAddress; }
    public List<OrderItem> getItems() { return items; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public static class Builder {
        private String orderId;
        private User user;
        private LocalDateTime orderDate;
        private String status;
        private double totalPrice;
        private Address shippingAddress;
        private List<OrderItem> items;

        public Builder setOrderId(String orderId) { this.orderId = orderId; return this; }
        public Builder setUser(User user) { this.user = user; return this; }
        public Builder setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; return this; }
        public Builder setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder setItems(List<OrderItem> items) { this.items = items; return this; }

        public Order build() { return new Order(this); }
    }
}
