package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;
import za.ac.cput.service.CartService;
import za.ac.cput.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        try {
            // Get the user
            User user = userService.findById(cart.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate cart ID and create cart
            String cartId = UUID.randomUUID().toString();
            Cart newCart = CartFactory.buildCart(cartId, user);
            Cart created = cartService.create(newCart);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> read(@PathVariable String id) {
        Cart cart = cartService.read(id);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> update(@PathVariable String id, @RequestBody Cart cart) {
        Cart updated = cartService.update(cart);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        List<Cart> carts = cartService.getAll();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable String userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/exists/user/{userId}")
    public ResponseEntity<Boolean> existsCartForUserId(@PathVariable String userId) {
        Cart cart = cartService.getCartByUserId(userId);
        boolean exists = (cart != null);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
