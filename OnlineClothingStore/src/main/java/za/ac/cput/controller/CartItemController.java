package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.CartItemService;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItem> create(@RequestBody CartItem cartItem) {
        CartItem created = cartItemService.create(cartItem);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> read(@PathVariable String id) {
        CartItem cartItem = cartItemService.read(id);
        if (cartItem != null) {
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> update(@PathVariable String id, @RequestBody CartItem cartItem) {
        CartItem updated = cartItemService.update(cartItem);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cartItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAll() {
        List<CartItem> cartItems = cartItemService.getAll();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable String cartId) {
        List<CartItem> cartItems = cartItemService.getItemsByCartId(cartId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CartItem>> getCartItemsByProductId(@PathVariable String productId) {
        // This endpoint is removed as the service doesn't support finding by product ID alone
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<CartItem> getCartItemByCartAndProduct(@PathVariable String cartId, @PathVariable String productId) {
        CartItem cartItem = cartItemService.getItemByCartIdAndProductId(cartId, productId);
        if (cartItem != null) {
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<Void> deleteByCartId(@PathVariable String cartId) {
        // Note: This requires creating a Cart object or using the service differently
        List<CartItem> items = cartItemService.getItemsByCartId(cartId);
        for (CartItem item : items) {
            cartItemService.delete(item.getCartItemId());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
