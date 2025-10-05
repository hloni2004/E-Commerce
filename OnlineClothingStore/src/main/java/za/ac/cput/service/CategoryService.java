package za.ac.cput.service;

import za.ac.cput.domain.Category;

import java.util.List;

public interface CategoryService extends IService {

    Category create(Category category);

    Category read(String categoryId);

    Category update(Category category);

    void delete(String categoryId);

    List<Category> getAll();

    Category getCategoryByName(String categoryName);

    List<Category> searchCategoriesByName(String keyword);

    boolean existsByCategoryName(String categoryName);

    List<Category> getAllCategoriesOrderedByName();
}