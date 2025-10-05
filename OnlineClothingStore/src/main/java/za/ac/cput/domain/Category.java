package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;

    protected Category() {}

    private Category(Builder builder) {
        this.categoryId = builder.categoryId;
        this.categoryName = builder.categoryName;
        this.description = builder.description;
        this.products = builder.products;
    }

    public String getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public List<Product> getProducts() { return products; }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public static class Builder {
        private String categoryId;
        private String categoryName;
        private String description;
        private List<Product> products;

        public Builder setCategoryId(String categoryId) { this.categoryId = categoryId; return this; }
        public Builder setCategoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setProducts(List<Product> products) { this.products = products; return this; }

        public Category build() { return new Category(this); }
    }
}
