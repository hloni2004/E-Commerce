package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(Category category);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByStockQuantityGreaterThan(int quantity);
    List<Product> findByStockQuantityLessThan(int quantity);
    List<Product> findByCategory_CategoryId(String categoryId);
    boolean existsByName(String name);
}
