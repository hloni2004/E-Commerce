package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    private String orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    protected OrderItem() {}

    private OrderItem(Builder builder) {
        this.orderItemId = builder.orderItemId;
        this.order = builder.order;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public String getOrderItemId() { return orderItemId; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId='" + orderItemId + '\'' +
                ", product=" + (product != null ? product.getName() : null) +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private String orderItemId;
        private Order order;
        private Product product;
        private int quantity;
        private double price;

        public Builder setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; return this; }
        public Builder setOrder(Order order) { this.order = order; return this; }
        public Builder setProduct(Product product) { this.product = product; return this; }
        public Builder setQuantity(int quantity) { this.quantity = quantity; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }

        public OrderItem build() { return new OrderItem(this); }
    }
}
