package za.ac.cput.service;

import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Optional<Product> findById(String productId);
    List<Product> findAll();
    void deleteById(String productId);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(Category category);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByStockQuantityGreaterThan(int quantity);
    List<Product> findByStockQuantityLessThan(int quantity);
    List<Product> findByCategoryCategoryId(String categoryId);
    boolean existsByName(String name);
}
