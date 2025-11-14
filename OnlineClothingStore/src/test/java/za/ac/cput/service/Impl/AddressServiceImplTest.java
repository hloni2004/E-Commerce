package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceImplTest {

    @Autowired
    private AddressServiceImpl service;

    @Autowired
    private UserServiceImpl userService;

    private static Address address;
    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("U123", "john", "john@email.com", "password", 
                "1234567890", "CUSTOMER", LocalDateTime.now(), LocalDateTime.now(), 
                null, null, null);
        address = AddressFactory.buildAddress("123", "123 Main St", "Springfield", "IL", "62701", "USA", user);
    }

    @Test
    @Order(1)
    void create() {
        User savedUser = userService.save(user);
        Address created = service.create(address);
        assertNotNull(created);
        assertEquals(address.getAddressId(), created.getAddressId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void read() {
        Address read = service.read(address.getAddressId());
        assertNotNull(read);
        assertEquals(address.getAddressId(), read.getAddressId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void update() {
        Address updated = new Address.Builder()
                .setAddressId(address.getAddressId())
                .setUser(address.getUser())
                .setStreet("456 Updated St")
                .setCity(address.getCity())
                .setProvince(address.getProvince())
                .setPostalCode(address.getPostalCode())
                .setCountry(address.getCountry())
                .build();
        Address result = service.update(updated);
        assertNotNull(result);
        assertEquals("456 Updated St", result.getStreet());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Address> addresses = service.getAll();
        assertNotNull(addresses);
        assertTrue(addresses.size() > 0);
        System.out.println("All addresses: " + addresses.size());
    }

    @Test
    @Order(5)
    void getAddressesByUser() {
        List<Address> addresses = service.getAddressesByUser(user);
        assertNotNull(addresses);
        System.out.println("Addresses by user: " + addresses.size());
    }

    @Test
    @Order(6)
    void getAddressesByUserId() {
        List<Address> addresses = service.getAddressesByUserId(user.getUserId());
        assertNotNull(addresses);
        System.out.println("Addresses by user ID: " + addresses.size());
    }

    @Test
    @Order(7)
    void getAddressesByCity() {
        List<Address> addresses = service.getAddressesByCity("Springfield");
        assertNotNull(addresses);
        System.out.println("Addresses by city: " + addresses.size());
    }

    @Test
    @Order(8)
    void getAddressesByProvince() {
        List<Address> addresses = service.getAddressesByProvince("IL");
        assertNotNull(addresses);
        System.out.println("Addresses by province: " + addresses.size());
    }

    @Test
    @Order(9)
    void getAddressesByCountry() {
        List<Address> addresses = service.getAddressesByCountry("USA");
        assertNotNull(addresses);
        System.out.println("Addresses by country: " + addresses.size());
    }

    @Test
    @Order(10)
    void countAddressesByUser() {
        long count = service.countAddressesByUser(user);
        assertTrue(count >= 0);
        System.out.println("Address count by user: " + count);
    }

    @Test
    @Order(11)
    void delete() {
        service.delete(address.getAddressId());
        Address deleted = service.read(address.getAddressId());
        assertNull(deleted);
        System.out.println("Address deleted successfully");
    }
}