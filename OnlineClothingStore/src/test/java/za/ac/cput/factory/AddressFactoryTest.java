package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AddressFactoryTest {

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User.Builder()
                .setUserId("U001")
                .setUsername("testuser")
                .setEmail("test@example.com")
                .setPassword("Test@1234")
                .setRole("CUSTOMER")
                .setCreatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testBuildAddress_Success() {
        Address address = AddressFactory.buildAddress(
                "A001",
                "123 Main Street",
                "Cape Town",
                "Western Cape",
                "8001",
                "South Africa",
                testUser
        );

        assertNotNull(address);
        assertEquals("A001", address.getAddressId());
        assertEquals("123 Main Street", address.getStreet());
        assertEquals("Cape Town", address.getCity());
        assertEquals("Western Cape", address.getProvince());
        assertEquals("8001", address.getPostalCode());
        assertEquals("South Africa", address.getCountry());
        assertEquals(testUser, address.getUser());
    }

    @Test
    void testBuildAddress_NullAddressId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    null,
                    "123 Main Street",
                    "Cape Town",
                    "Western Cape",
                    "8001",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("Address ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_EmptyAddressId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "",
                    "123 Main Street",
                    "Cape Town",
                    "Western Cape",
                    "8001",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("Address ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_NullStreet_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    null,
                    "Cape Town",
                    "Western Cape",
                    "8001",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("Street cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_EmptyCity_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    "123 Main Street",
                    "   ",
                    "Western Cape",
                    "8001",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("City cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_NullProvince_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    "123 Main Street",
                    "Cape Town",
                    null,
                    "8001",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("Province cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_EmptyPostalCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    "123 Main Street",
                    "Cape Town",
                    "Western Cape",
                    "",
                    "South Africa",
                    testUser
            );
        });
        assertEquals("Postal code cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_NullCountry_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    "123 Main Street",
                    "Cape Town",
                    "Western Cape",
                    "8001",
                    null,
                    testUser
            );
        });
        assertEquals("Country cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAddress_NullUser_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AddressFactory.buildAddress(
                    "A001",
                    "123 Main Street",
                    "Cape Town",
                    "Western Cape",
                    "8001",
                    "South Africa",
                    null
            );
        });
        assertEquals("User cannot be null", exception.getMessage());
    }
}