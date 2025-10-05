package za.ac.cput.service;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;

import java.util.List;

public interface CartItemService extends IService {

    CartItem create(CartItem cartItem);

    CartItem read(String cartItemId);

    CartItem update(CartItem cartItem);

    void delete(String cartItemId);

    List<CartItem> getAll();

    List<CartItem> getItemsByCart(Cart cart);

    List<CartItem> getItemsByCartId(String cartId);

    CartItem getItemByCartAndProduct(Cart cart, Product product);

    CartItem getItemByCartIdAndProductId(String cartId, String productId);

    void deleteAllItemsByCart(Cart cart);

    long countItemsByCart(Cart cart);
}