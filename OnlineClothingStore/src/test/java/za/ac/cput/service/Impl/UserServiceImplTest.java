package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U1", "testuser", "test@example.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void save() {
        User saved = userService.save(user);
        assertNotNull(saved);
        assertEquals(user.getUserId(), saved.getUserId());
        System.out.println("✓ Saved user to DB: " + saved);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        Optional<User> found = userService.findById("U1");
        assertTrue(found.isPresent());
        assertEquals(user.getUsername(), found.get().getUsername());
        System.out.println("Found user: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findAll() {
        List<User> all = userService.findAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All users: " + all.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findByUsername() {
        Optional<User> found = userService.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals(user.getUsername(), found.get().getUsername());
        System.out.println("Found by username: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void findByEmail() {
        Optional<User> found = userService.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals(user.getEmail(), found.get().getEmail());
        System.out.println("Found by email: " + found.get());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void findByRole() {
        List<User> result = userService.findByRole("CUSTOMER");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Users with role CUSTOMER: " + result.size());
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void existsByUsername() {
        boolean exists = userService.existsByUsername("testuser");
        assertTrue(exists);
        System.out.println("User exists by username: " + exists);
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    void existsByEmail() {
        boolean exists = userService.existsByEmail("test@example.com");
        assertTrue(exists);
        System.out.println("User exists by email: " + exists);
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    void deleteById() {
        userService.deleteById("U1");
        Optional<User> deleted = userService.findById("U1");
        assertFalse(deleted.isPresent());
        System.out.println("User deleted successfully");
    }
}