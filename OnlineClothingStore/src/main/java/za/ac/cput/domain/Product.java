package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Product() {}

    private Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
        this.imageUrl = builder.imageUrl;
        this.category = builder.category;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getImageUrl() { return imageUrl; }
    public Category getCategory() { return category; }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    public static class Builder {
        private String productId;
        private String name;
        private String description;
        private double price;
        private int stockQuantity;
        private String imageUrl;
        private Category category;

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }
        public Builder setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }
        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
