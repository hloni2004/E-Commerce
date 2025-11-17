package za.ac.cput.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Order(1) // Run first
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        // Check if admin already exists
        if (userService.existsByUsername("admin")) {
            System.out.println("✓ Admin user already exists");
            return;
        }

        try {
            // Create default admin user
            String adminId = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now();

            User admin = UserFactory.createUser(
                    adminId,
                    "admin",
                    "admin@stylehub.com",
                    "Admin@123", // Default admin password
                    "+27123456789",
                    "ADMIN",
                    now,
                    now,
                    null,
                    null,
                    null
            );

            userService.save(admin);
            System.out.println("✓ Admin user created successfully");
            System.out.println("  Username: admin");
            System.out.println("  Password: Admin@123");
            System.out.println("  Email: admin@stylehub.com");
        } catch (Exception e) {
            System.err.println("✗ Failed to create admin user: " + e.getMessage());
        }
    }
}
