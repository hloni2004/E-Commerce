package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    // Find category by name
    Optional<Category> findByCategoryName(String categoryName);

    // Find categories by name containing (case-insensitive search)
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);

    // Check if category exists by name
    boolean existsByCategoryName(String categoryName);

    // Find all categories ordered by name
    List<Category> findAllByOrderByCategoryNameAsc();
}