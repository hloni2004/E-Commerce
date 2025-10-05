package za.ac.cput.factory;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.util.List;

public class CartFactory {

    public static Cart buildCart(String cartId, User user, List<CartItem> items) {

        // Validate cartId
        if (!Helper.isNotEmpty(cartId)) {
            throw new IllegalArgumentException("Cart ID cannot be null or empty");
        }

        // Validate user
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Items can be null or empty (empty cart is valid)

        return new Cart.Builder()
                .setCartId(cartId)
                .setUser(user)
                .setItems(items)
                .build();
    }

    // Overloaded method without items (for creating empty cart)
    public static Cart buildCart(String cartId, User user) {
        return buildCart(cartId, user, null);
    }
}