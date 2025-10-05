package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.CartItemRepository;
import za.ac.cput.service.CartItemService;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository repository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return repository.save(cartItem);
    }

    @Override
    public CartItem read(String cartItemId) {
        return repository.findById(cartItemId).orElse(null);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        if (repository.existsById(cartItem.getCartItemId())) {
            return repository.save(cartItem);
        }
        return null;
    }

    @Override
    public void delete(String cartItemId) {
        repository.deleteById(cartItemId);
    }

    @Override
    public List<CartItem> getAll() {
        return repository.findAll();
    }

    @Override
    public List<CartItem> getItemsByCart(Cart cart) {
        return repository.findByCart(cart);
    }

    @Override
    public List<CartItem> getItemsByCartId(String cartId) {
        return repository.findByCart_CartId(cartId);
    }

    @Override
    public CartItem getItemByCartAndProduct(Cart cart, Product product) {
        return repository.findByCartAndProduct(cart, product).orElse(null);
    }

    @Override
    public CartItem getItemByCartIdAndProductId(String cartId, String productId) {
        return repository.findByCart_CartIdAndProduct_ProductId(cartId, productId).orElse(null);
    }

    @Override
    public void deleteAllItemsByCart(Cart cart) {
        repository.deleteByCart(cart);
    }

    @Override
    public long countItemsByCart(Cart cart) {
        return repository.countByCart(cart);
    }
}