package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    // Find all items in a specific cart
    List<CartItem> findByCart(Cart cart);

    // Find all items by cart ID
    List<CartItem> findByCart_CartId(String cartId);

    // Find a specific product in a cart
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // Find cart item by cart ID and product ID
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(String cartId, String productId);

    // Delete all items in a cart
    void deleteByCart(Cart cart);

    // Count items in a cart
    long countByCart(Cart cart);
}