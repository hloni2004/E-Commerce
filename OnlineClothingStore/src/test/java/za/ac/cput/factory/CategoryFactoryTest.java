package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFactoryTest {

    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProducts = new ArrayList<>();
    }

    @Test
    void testBuildCategory_WithAllParameters_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT001",
                "Men's Clothing",
                "Clothing items for men",
                testProducts
        );

        assertNotNull(category);
        assertEquals("CAT001", category.getCategoryId());
        assertEquals("Men's Clothing", category.getCategoryName());
        assertEquals("Clothing items for men", category.getDescription());
        assertEquals(testProducts, category.getProducts());
    }

    @Test
    void testBuildCategory_WithoutDescriptionAndProducts_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT002",
                "Women's Clothing"
        );

        assertNotNull(category);
        assertEquals("CAT002", category.getCategoryId());
        assertEquals("Women's Clothing", category.getCategoryName());
        assertNull(category.getDescription());
        assertNull(category.getProducts());
    }

    @Test
    void testBuildCategory_WithDescriptionWithoutProducts_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT003",
                "Accessories",
                "Fashion accessories and jewelry"
        );

        assertNotNull(category);
        assertEquals("CAT003", category.getCategoryId());
        assertEquals("Accessories", category.getCategoryName());
        assertEquals("Fashion accessories and jewelry", category.getDescription());
        assertNull(category.getProducts());
    }

    @Test
    void testBuildCategory_NullDescription_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT004",
                "Shoes",
                null,
                testProducts
        );

        assertNotNull(category);
        assertEquals("CAT004", category.getCategoryId());
        assertEquals("Shoes", category.getCategoryName());
        assertNull(category.getDescription());
        assertEquals(testProducts, category.getProducts());
    }

    @Test
    void testBuildCategory_EmptyDescription_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT005",
                "Kids Clothing",
                "",
                null
        );

        assertNotNull(category);
        assertEquals("CAT005", category.getCategoryId());
        assertEquals("Kids Clothing", category.getCategoryName());
        assertEquals("", category.getDescription());
        assertNull(category.getProducts());
    }

    @Test
    void testBuildCategory_NullProducts_Success() {
        Category category = CategoryFactory.buildCategory(
                "CAT006",
                "Sports Wear",
                "Athletic and sports clothing",
                null
        );

        assertNotNull(category);
        assertNull(category.getProducts());
    }

    @Test
    void testBuildCategory_EmptyProductsList_Success() {
        List<Product> emptyList = new ArrayList<>();

        Category category = CategoryFactory.buildCategory(
                "CAT007",
                "Formal Wear",
                "Formal clothing and suits",
                emptyList
        );

        assertNotNull(category);
        assertTrue(category.getProducts().isEmpty());
    }

    @Test
    void testBuildCategory_NullCategoryId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    null,
                    "Casual Wear",
                    "Casual clothing",
                    testProducts
            );
        });
        assertEquals("Category ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_EmptyCategoryId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "",
                    "Casual Wear",
                    "Casual clothing",
                    testProducts
            );
        });
        assertEquals("Category ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_WhitespaceCategoryId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "   ",
                    "Casual Wear",
                    "Casual clothing",
                    testProducts
            );
        });
        assertEquals("Category ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_NullCategoryName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "CAT001",
                    null,
                    "Some description",
                    testProducts
            );
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_EmptyCategoryName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "CAT001",
                    "",
                    "Some description",
                    testProducts
            );
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_WhitespaceCategoryName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "CAT001",
                    "   ",
                    "Some description",
                    testProducts
            );
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildCategory_OverloadedMethod_NullCategoryName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CategoryFactory.buildCategory(
                    "CAT001",
                    null
            );
        });
        assertEquals("Category name cannot be null or empty", exception.getMessage());
    }
}