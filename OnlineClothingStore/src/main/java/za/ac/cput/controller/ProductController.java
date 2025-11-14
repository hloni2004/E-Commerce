package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Product;
import za.ac.cput.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product saved = productService.save(product);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Optional<Product> product = productService.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable String name) {
        List<Product> products = productService.findByNameContainingIgnoreCase(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> findByCategoryId(@PathVariable String categoryId) {
        List<Product> products = productService.findByCategoryCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> findByPriceRange(@RequestParam double min, @RequestParam double max) {
        List<Product> products = productService.findByPriceBetween(min, max);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/stock/low")
    public ResponseEntity<List<Product>> findLowStock(@RequestParam(defaultValue = "10") int threshold) {
        List<Product> products = productService.findByStockQuantityLessThan(threshold);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/stock/available")
    public ResponseEntity<List<Product>> findAvailableStock(@RequestParam(defaultValue = "0") int threshold) {
        List<Product> products = productService.findByStockQuantityGreaterThan(threshold);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
