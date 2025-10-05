package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    // Find cart by user
    Optional<Cart> findByUser(User user);

    // Find cart by user ID
    Optional<Cart> findByUser_UserId(String userId);

    // Check if cart exists for a user
    boolean existsByUser(User user);
}