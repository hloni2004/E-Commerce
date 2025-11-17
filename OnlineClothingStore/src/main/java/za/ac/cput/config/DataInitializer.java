package za.ac.cput.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.ProductFactory;
import za.ac.cput.service.CategoryService;
import za.ac.cput.service.ProductService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(2) // Run after AdminInitializer
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) {
        try {
            initializeCategories();
            // Sample products removed - use admin dashboard to add products
            System.out.println("✓ Categories initialized successfully");
        } catch (Exception e) {
            System.err.println("✗ Failed to initialize categories: " + e.getMessage());
        }
    }

    private void initializeCategories() {
        // Check if categories already exist
        if (!categoryService.getAll().isEmpty()) {
            System.out.println("✓ Categories already exist");
            return;
        }

        // Create categories
        String[][] categories = {
                {"Men's Clothing", "Stylish clothing for men"},
                {"Women's Clothing", "Fashionable clothing for women"},
                {"Accessories", "Fashion accessories and jewelry"},
                {"Footwear", "Shoes and sneakers for all occasions"},
                {"Sports & Outdoor", "Active wear and outdoor clothing"}
        };

        for (String[] cat : categories) {
            Category category = CategoryFactory.buildCategory(
                    UUID.randomUUID().toString(),
                    cat[0],
                    cat[1]
            );
            categoryService.create(category);
        }

        System.out.println("✓ " + categories.length + " categories created");
    }

    private void initializeSampleProducts() {
        // Check if products already exist
        if (!productService.findAll().isEmpty()) {
            System.out.println("✓ Products already exist");
            return;
        }

        // Get categories
        Category mensCategory = categoryService.getCategoryByName("Men's Clothing");
        Category womensCategory = categoryService.getCategoryByName("Women's Clothing");
        Category accessoriesCategory = categoryService.getCategoryByName("Accessories");
        Category footwearCategory = categoryService.getCategoryByName("Footwear");
        Category sportsCategory = categoryService.getCategoryByName("Sports & Outdoor");

        // Sample products data
        Object[][] products = {
                // Men's Clothing
                {"Classic Denim Jacket", "Timeless denim jacket with a modern fit", 899.99, 50, mensCategory, new Color(70, 130, 180)},
                {"Cotton Polo Shirt", "Comfortable cotton polo perfect for casual wear", 299.99, 100, mensCategory, new Color(0, 100, 0)},
                {"Slim Fit Chinos", "Modern slim fit chino pants", 599.99, 75, mensCategory, new Color(139, 69, 19)},
                
                // Women's Clothing
                {"Floral Summer Dress", "Light and breezy summer dress with floral print", 749.99, 60, womensCategory, new Color(255, 182, 193)},
                {"Elegant Blouse", "Sophisticated blouse for office or evening wear", 499.99, 80, womensCategory, new Color(255, 255, 255)},
                {"High-Waist Jeans", "Trendy high-waist jeans with stretch comfort", 699.99, 90, womensCategory, new Color(25, 25, 112)},
                
                // Accessories
                {"Leather Belt", "Genuine leather belt with silver buckle", 249.99, 150, accessoriesCategory, new Color(101, 67, 33)},
                {"Designer Sunglasses", "UV protection sunglasses with style", 349.99, 120, accessoriesCategory, new Color(0, 0, 0)},
                {"Leather Wallet", "Premium leather wallet with multiple card slots", 399.99, 100, accessoriesCategory, new Color(139, 69, 19)},
                
                // Footwear
                {"Running Sneakers", "Lightweight running shoes with cushioned sole", 1299.99, 80, footwearCategory, new Color(255, 255, 255)},
                {"Casual Loafers", "Comfortable leather loafers for everyday wear", 899.99, 60, footwearCategory, new Color(101, 67, 33)},
                {"High-Top Sneakers", "Stylish high-top sneakers in classic design", 1099.99, 70, footwearCategory, new Color(0, 0, 0)},
                
                // Sports & Outdoor
                {"Performance T-Shirt", "Moisture-wicking athletic t-shirt", 349.99, 150, sportsCategory, new Color(0, 0, 255)},
                {"Running Shorts", "Lightweight shorts with built-in compression", 449.99, 120, sportsCategory, new Color(0, 0, 0)},
                {"Windbreaker Jacket", "Water-resistant windbreaker for outdoor activities", 799.99, 80, sportsCategory, new Color(255, 140, 0)}
        };

        int count = 0;
        for (Object[] productData : products) {
            try {
                String name = (String) productData[0];
                String description = (String) productData[1];
                double price = (double) productData[2];
                int stock = (int) productData[3];
                Category category = (Category) productData[4];
                Color color = (Color) productData[5];

                if (category == null) continue;

                // Generate simple placeholder image
                byte[] imageData = generatePlaceholderImage(name, color);

                Product product = new Product.Builder()
                        .setProductId(UUID.randomUUID().toString())
                        .setName(name)
                        .setDescription(description)
                        .setPrice(price)
                        .setStockQuantity(stock)
                        .setImageData(imageData)
                        .setImageType("image/jpeg")
                        .setCategory(category)
                        .build();

                productService.save(product);
                count++;
            } catch (Exception e) {
                System.err.println("Failed to create product: " + productData[0]);
            }
        }

        System.out.println("✓ " + count + " sample products created with images");
    }

    private byte[] generatePlaceholderImage(String productName, Color bgColor) {
        try {
            int width = 400;
            int height = 400;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Enable antialiasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Draw background
            g2d.setColor(bgColor);
            g2d.fillRect(0, 0, width, height);

            // Draw gradient overlay
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 200),
                    width, height, new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 100)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);

            // Draw product name
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g2d.getFontMetrics();
            
            // Split text into multiple lines if too long
            String[] words = productName.split(" ");
            int lineHeight = fm.getHeight();
            int y = height / 2 - (words.length * lineHeight) / 2;
            
            for (String word : words) {
                int x = (width - fm.stringWidth(word)) / 2;
                g2d.drawString(word, x, y);
                y += lineHeight;
            }

            // Draw border
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(10, 10, width - 20, height - 20);

            g2d.dispose();

            // Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Failed to generate placeholder image for: " + productName);
            return new byte[0];
        }
    }
}
