package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ProductFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    private static Product product;
    private static Category category;

    @BeforeAll
    static void setUp() {
        category = CategoryFactory.buildCategory("C1", "Electronics", "Electronic items");
        product = ProductFactory.createProduct("P1", "Laptop", "High-end laptop", 
                1500.00, 10, "http://example.com/laptop.jpg", category);
    }

    @Test
    @Order(1)
    void save() {
        categoryService.create(category);
        Product saved = productService.save(product);
        assertNotNull(saved);
        assertEquals(product.getProductId(), saved.getProductId());
        System.out.println("Saved product: " + saved);
    }

    @Test
    @Order(2)
    void findById() {
        Optional<Product> found = productService.findById("P1");
        assertTrue(found.isPresent());
        assertEquals(product.getName(), found.get().getName());
        System.out.println("Found product: " + found.get());
    }

    @Test
    @Order(3)
    void findAll() {
        List<Product> all = productService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All products: " + all.size());
    }

    @Test
    @Order(4)
    void findByNameContainingIgnoreCase() {
        List<Product> result = productService.findByNameContainingIgnoreCase("Laptop");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Found by name: " + result.size());
    }

    @Test
    @Order(5)
    void findByCategory() {
        List<Product> result = productService.findByCategory(category);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Found by category: " + result.size());
    }

    @Test
    @Order(6)
    void findByPriceBetween() {
        List<Product> result = productService.findByPriceBetween(1000.0, 2000.0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Found by price range: " + result.size());
    }

    @Test
    @Order(7)
    void findByStockQuantityGreaterThan() {
        List<Product> result = productService.findByStockQuantityGreaterThan(5);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Stock > 5: " + result.size());
    }

    @Test
    @Order(8)
    void findByStockQuantityLessThan() {
        List<Product> result = productService.findByStockQuantityLessThan(20);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Stock < 20: " + result.size());
    }

    @Test
    @Order(9)
    void findByCategoryCategoryId() {
        List<Product> result = productService.findByCategoryCategoryId("C1");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Found by category ID: " + result.size());
    }

    @Test
    @Order(10)
    void existsByName() {
        boolean exists = productService.existsByName("Laptop");
        assertTrue(exists);
        System.out.println("Product exists: " + exists);
    }

    @Test
    @Order(11)
    void deleteById() {
        productService.deleteById("P1");
        Optional<Product> deleted = productService.findById("P1");
        assertFalse(deleted.isPresent());
        System.out.println("Product deleted successfully");
    }
}