package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Category;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return repository.save(category);
    }

    @Override
    public Category read(String categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public Category update(Category category) {
        if (repository.existsById(category.getCategoryId())) {
            return repository.save(category);
        }
        return null;
    }

    @Override
    public void delete(String categoryId) {
        repository.deleteById(categoryId);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return repository.findByCategoryName(categoryName).orElse(null);
    }

    @Override
    public List<Category> searchCategoriesByName(String keyword) {
        return repository.findByCategoryNameContainingIgnoreCase(keyword);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        return repository.existsByCategoryName(categoryName);
    }

    @Override
    public List<Category> getAllCategoriesOrderedByName() {
        return repository.findAllByOrderByCategoryNameAsc();
    }
}