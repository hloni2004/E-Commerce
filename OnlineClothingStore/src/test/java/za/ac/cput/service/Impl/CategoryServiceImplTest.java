package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.service.CategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    private static Category testCategory;

    @BeforeAll
    static void setUp() {
        testCategory = CategoryFactory.buildCategory(
                "CAT001",
                "Men's Clothing",
                "Clothing for men including shirts, pants, and jackets"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        Category created = categoryService.create(testCategory);
        assertNotNull(created);
        assertEquals(testCategory.getCategoryId(), created.getCategoryId());
        assertEquals("Men's Clothing", created.getCategoryName());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        Category read = categoryService.read(testCategory.getCategoryId());
        assertNotNull(read);
        assertEquals(testCategory.getCategoryId(), read.getCategoryId());
        assertEquals("Men's Clothing", read.getCategoryName());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        Category existing = categoryService.read(testCategory.getCategoryId());
        Category updated = new Category.Builder()
                .setCategoryId(existing.getCategoryId())
                .setCategoryName("Men's Fashion")
                .setDescription("Updated description for men's fashion")
                .build();

        Category result = categoryService.update(updated);
        assertNotNull(result);
        assertEquals("Men's Fashion", result.getCategoryName());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testGetCategoryByName() {
        Category category = categoryService.getCategoryByName("Men's Fashion");
        assertNotNull(category);
        assertEquals(testCategory.getCategoryId(), category.getCategoryId());
        System.out.println("Category by name: " + category);
    }

    @Test
    @Order(5)
    void testSearchCategoriesByName() {
        List<Category> categories = categoryService.searchCategoriesByName("Men");
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        System.out.println("Search results: " + categories.size());
    }

    @Test
    @Order(6)
    void testExistsByCategoryName() {
        boolean exists = categoryService.existsByCategoryName("Men's Fashion");
        assertTrue(exists);
        System.out.println("Category exists: " + exists);
    }

    @Test
    @Order(7)
    void testGetAll() {
        List<Category> categories = categoryService.getAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        System.out.println("Total categories: " + categories.size());
    }

    @Test
    @Order(8)
    void testGetAllCategoriesOrderedByName() {
        List<Category> categories = categoryService.getAllCategoriesOrderedByName();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        System.out.println("Ordered categories: " + categories.size());
    }

    @Test
    @Order(9)
    void testDelete() {
        categoryService.delete(testCategory.getCategoryId());
        Category deleted = categoryService.read(testCategory.getCategoryId());
        assertNull(deleted);
        System.out.println("Category deleted successfully");
    }
}