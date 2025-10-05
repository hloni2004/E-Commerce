package za.ac.cput.factory;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import za.ac.cput.util.Helper;

public class CartItemFactory {

    public static CartItem buildCartItem(String cartItemId, Cart cart,
                                         Product product, int quantity) {

        // Validate cartItemId
        if (!Helper.isNotEmpty(cartItemId)) {
            throw new IllegalArgumentException("Cart Item ID cannot be null or empty");
        }

        // Validate cart
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }

        // Validate product
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Validate quantity
        if (!Helper.isPositive(quantity)) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Check if quantity exceeds available stock
        if (quantity > product.getStockQuantity()) {
            throw new IllegalArgumentException("Quantity exceeds available stock");
        }

        return new CartItem.Builder()
                .setCartItemId(cartItemId)
                .setCart(cart)
                .setProduct(product)
                .setQuantity(quantity)
                .build();
    }
}