package za.ac.cput.service;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;

import java.util.List;

public interface CartService extends IService {

    Cart create(Cart cart);

    Cart read(String cartId);

    Cart update(Cart cart);

    void delete(String cartId);

    List<Cart> getAll();

    Cart getCartByUser(User user);

    Cart getCartByUserId(String userId);

    boolean existsCartForUser(User user);
}