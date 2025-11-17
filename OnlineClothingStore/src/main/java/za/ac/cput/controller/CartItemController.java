package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CartItemFactory;
import za.ac.cput.service.CartItemService;
import za.ac.cput.service.CartService;
import za.ac.cput.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = "*")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartItemController(CartItemService cartItemService, CartService cartService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CartItem cartItem) {
        try {
            // Get cart
            Cart cart = cartService.read(cartItem.getCart().getCartId());
            if (cart == null) {
                return ResponseEntity.badRequest().body("Cart not found");
            }

            // Get product
            Product product = productService.findById(cartItem.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if item already exists in cart
            CartItem existingItem = cartItemService.getItemByCartIdAndProductId(
                    cart.getCartId(), 
                    product.getProductId()
            );

            if (existingItem != null) {
                // Update quantity if item exists
                int newQuantity = existingItem.getQuantity() + cartItem.getQuantity();
                if (newQuantity > product.getStockQuantity()) {
                    return ResponseEntity.badRequest().body("Insufficient stock");
                }
                
                CartItem updatedItem = new CartItem.Builder()
                        .setCartItemId(existingItem.getCartItemId())
                        .setCart(cart)
                        .setProduct(product)
                        .setQuantity(newQuantity)
                        .build();
                
                CartItem updated = cartItemService.update(updatedItem);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                // Create new cart item
                String cartItemId = UUID.randomUUID().toString();
                CartItem newCartItem = CartItemFactory.buildCartItem(
                        cartItemId,
                        cart,
                        product,
                        cartItem.getQuantity()
                );
                
                CartItem created = cartItemService.create(newCartItem);
                return new ResponseEntity<>(created, HttpStatus.CREATED);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding item to cart: " + e.getMessage());
        }
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
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            CartItem cartItem = cartItemService.read(id);
            if (cartItem == null) {
                return ResponseEntity.notFound().build();
            }

            // Update quantity if provided
            if (updates.containsKey("quantity")) {
                int newQuantity = (int) updates.get("quantity");
                
                // Validate quantity
                if (newQuantity < 1) {
                    return ResponseEntity.badRequest().body("Quantity must be at least 1");
                }
                
                if (newQuantity > cartItem.getProduct().getStockQuantity()) {
                    return ResponseEntity.badRequest().body("Insufficient stock");
                }
                
                CartItem updatedItem = new CartItem.Builder()
                        .setCartItemId(cartItem.getCartItemId())
                        .setCart(cartItem.getCart())
                        .setProduct(cartItem.getProduct())
                        .setQuantity(newQuantity)
                        .build();
                
                CartItem saved = cartItemService.update(updatedItem);
                return ResponseEntity.ok(saved);
            }
            
            return ResponseEntity.badRequest().body("No updates provided");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating cart item: " + e.getMessage());
        }
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
