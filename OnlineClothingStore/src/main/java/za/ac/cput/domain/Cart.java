package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id")
    private String cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> items;

    protected Cart() {}

    private Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.user = builder.user;
        this.items = builder.items;
    }

    public String getCartId() { return cartId; }
    public User getUser() { return user; }
    public List<CartItem> getItems() { return items; }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", user=" + (user != null ? user.getUserId() : null) +
                '}';
    }

    public static class Builder {
        private String cartId;
        private User user;
        private List<CartItem> items;

        public Builder setCartId(String cartId) { this.cartId = cartId; return this; }
        public Builder setUser(User user) { this.user = user; return this; }
        public Builder setItems(List<CartItem> items) { this.items = items; return this; }

        public Cart build() { return new Cart(this); }
    }
}
