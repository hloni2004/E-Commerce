package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;
import za.ac.cput.service.ProductService;
import za.ac.cput.service.CategoryService;
import za.ac.cput.factory.ProductFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product saved = productService.save(product);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProductWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("categoryId") String categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        try {
            // Find category
            Category category = categoryService.read(categoryId);
            if (category == null) {
                return ResponseEntity.badRequest().body("Category not found");
            }

            // Create product with image data
            String productId = UUID.randomUUID().toString();
            byte[] imageData = null;
            String imageType = null;

            if (image != null && !image.isEmpty()) {
                imageData = image.getBytes();
                imageType = image.getContentType();
            }

            Product product = ProductFactory.createProduct(
                    productId,
                    name,
                    description,
                    price,
                    stockQuantity,
                    null, // imageUrl not needed when using LOB
                    category
            );

            // Set image data using builder pattern manually
            Product productWithImage = new Product.Builder()
                    .setProductId(product.getProductId())
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setPrice(product.getPrice())
                    .setStockQuantity(product.getStockQuantity())
                    .setImageUrl(product.getImageUrl())
                    .setImageData(imageData)
                    .setImageType(imageType)
                    .setCategory(product.getCategory())
                    .build();

            Product saved = productService.save(productWithImage);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProductImage(
            @PathVariable String id,
            @RequestParam("image") MultipartFile image) {
        
        try {
            Optional<Product> productOpt = productService.findById(id);
            if (!productOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Product existingProduct = productOpt.get();
            byte[] imageData = image.getBytes();
            String imageType = image.getContentType();

            // Update product with new image
            Product updatedProduct = new Product.Builder()
                    .setProductId(existingProduct.getProductId())
                    .setName(existingProduct.getName())
                    .setDescription(existingProduct.getDescription())
                    .setPrice(existingProduct.getPrice())
                    .setStockQuantity(existingProduct.getStockQuantity())
                    .setImageUrl(existingProduct.getImageUrl())
                    .setImageData(imageData)
                    .setImageType(imageType)
                    .setCategory(existingProduct.getCategory())
                    .build();

            Product saved = productService.save(updatedProduct);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update image: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable String id) {
        Optional<Product> productOpt = productService.findById(id);
        
        if (!productOpt.isPresent() || productOpt.get().getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOpt.get();
        HttpHeaders headers = new HttpHeaders();
        
        if (product.getImageType() != null) {
            headers.setContentType(MediaType.parseMediaType(product.getImageType()));
        } else {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }

        return new ResponseEntity<>(product.getImageData(), headers, HttpStatus.OK);
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
