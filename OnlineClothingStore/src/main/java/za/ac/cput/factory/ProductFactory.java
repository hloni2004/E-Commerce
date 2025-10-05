package za.ac.cput.factory;

import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;

public class ProductFactory {
    public static Product createProduct(
            String productId,
            String name,
            String description,
            double price,
            int stockQuantity,
            String imageUrl,
            Category category
    ) {
        // Add validation as needed
        return new Product.Builder()
                .setProductId(productId)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .setImageUrl(imageUrl)
                .setCategory(category)
                .build();
    }
}
