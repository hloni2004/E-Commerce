package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductFactoryTest {

    @Test
    void createProduct() {
        String productId = "P100";
        String name = "T-Shirt";
        String description = "Cotton T-Shirt";
        double price = 199.99;
        int stockQuantity = 50;
        String imageUrl = "http://example.com/tshirt.jpg";
        Category category = new Category.Builder()
                .setCategoryId("C1")
                .setCategoryName("Clothing")
                .build();

        Product product = ProductFactory.createProduct(
                productId, name, description, price, stockQuantity, imageUrl, category
        );

        assertNotNull(product);
        assertEquals(productId, product.getProductId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(stockQuantity, product.getStockQuantity());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(category, product.getCategory());
    }
}
