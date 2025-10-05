package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;
import za.ac.cput.repository.CartRepository;
import za.ac.cput.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    @Autowired
    public CartServiceImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cart create(Cart cart) {
        return repository.save(cart);
    }

    @Override
    public Cart read(String cartId) {
        return repository.findById(cartId).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        if (repository.existsById(cart.getCartId())) {
            return repository.save(cart);
        }
        return null;
    }

    @Override
    public void delete(String cartId) {
        repository.deleteById(cartId);
    }

    @Override
    public List<Cart> getAll() {
        return repository.findAll();
    }

    @Override
    public Cart getCartByUser(User user) {
        return repository.findByUser(user).orElse(null);
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return repository.findByUser_UserId(userId).orElse(null);
    }

    @Override
    public boolean existsCartForUser(User user) {
        return repository.existsByUser(user);
    }
}