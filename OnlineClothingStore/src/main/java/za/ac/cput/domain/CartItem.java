package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    private String cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected CartItem() {}

    private CartItem(Builder builder) {
        this.cartItemId = builder.cartItemId;
        this.cart = builder.cart;
        this.product = builder.product;
        this.quantity = builder.quantity;
    }

    public String getCartItemId() { return cartItemId; }
    public Cart getCart() { return cart; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId='" + cartItemId + '\'' +
                ", product=" + (product != null ? product.getName() : null) +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private String cartItemId;
        private Cart cart;
        private Product product;
        private int quantity;

        public Builder setCartItemId(String cartItemId) { this.cartItemId = cartItemId; return this; }
        public Builder setCart(Cart cart) { this.cart = cart; return this; }
        public Builder setProduct(Product product) { this.product = product; return this; }
        public Builder setQuantity(int quantity) { this.quantity = quantity; return this; }

        public CartItem build() { return new CartItem(this); }
    }
}
