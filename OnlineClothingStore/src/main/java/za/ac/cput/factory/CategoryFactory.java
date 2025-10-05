package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.util.Helper;

import java.util.List;

public class CategoryFactory {

    public static Category buildCategory(String categoryId, String categoryName,
                                         String description, List<Product> products) {

        // Validate categoryId
        if (!Helper.isNotEmpty(categoryId)) {
            throw new IllegalArgumentException("Category ID cannot be null or empty");
        }

        // Validate categoryName
        if (!Helper.isNotEmpty(categoryName)) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }

        // Description can be null/empty (optional field)

        // Products can be null or empty (category without products is valid)

        return new Category.Builder()
                .setCategoryId(categoryId)
                .setCategoryName(categoryName)
                .setDescription(description)
                .setProducts(products)
                .build();
    }

    // Overloaded method without products and description
    public static Category buildCategory(String categoryId, String categoryName) {
        return buildCategory(categoryId, categoryName, null, null);
    }

    // Overloaded method without products
    public static Category buildCategory(String categoryId, String categoryName, String description) {
        return buildCategory(categoryId, categoryName, description, null);
    }
}