package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.User;
import za.ac.cput.domain.dto.LoginRequest;
import za.ac.cput.domain.dto.LoginResponse;
import za.ac.cput.domain.dto.RegistrationRequest;
import za.ac.cput.domain.dto.RegistrationResponse;
import za.ac.cput.factory.AddressFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.AddressService;
import za.ac.cput.service.UserService;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        try {
            // Validate username
            if (!Helper.isNotEmpty(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Username is required"));
            }

            // Validate email
            if (!Helper.isValidEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Invalid email format"));
            }

            // Validate password
            if (!Helper.isValidPassword(request.getPassword())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, 
                            "Password must be at least 8 characters with uppercase, lowercase, number, and special character"));
            }

            // Validate phone number (optional but if provided must be valid)
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
                if (!Helper.isValidPhone(request.getPhoneNumber())) {
                    return ResponseEntity.badRequest()
                            .body(new RegistrationResponse(false, "Invalid phone number format (use South African format)"));
                }
            }

            // Validate address fields
            if (!Helper.isNotEmpty(request.getStreet())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Street address is required"));
            }
            if (!Helper.isNotEmpty(request.getCity())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "City is required"));
            }
            if (!Helper.isNotEmpty(request.getProvince())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Province is required"));
            }
            if (!Helper.isNotEmpty(request.getPostalCode())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Postal code is required"));
            }
            if (!Helper.isNotEmpty(request.getCountry())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Country is required"));
            }

            // Check if username already exists
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Username already exists"));
            }

            // Check if email already exists
            if (userService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(new RegistrationResponse(false, "Email already registered"));
            }

            // Create new user with CUSTOMER role by default
            String userId = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now();

            User newUser = UserFactory.createUser(
                    userId,
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(), // Note: In production, hash this password
                    request.getPhoneNumber(),
                    "CUSTOMER", // Default role
                    now,
                    now,
                    null,
                    null,
                    null
            );

            // Save user to database first
            User savedUser = userService.save(newUser);

            // Create and save address for the user
            String addressId = UUID.randomUUID().toString();
            Address address = AddressFactory.buildAddress(
                    addressId,
                    request.getStreet(),
                    request.getCity(),
                    request.getProvince(),
                    request.getPostalCode(),
                    request.getCountry(),
                    savedUser
            );

            addressService.create(address);

            // Return success response
            RegistrationResponse response = new RegistrationResponse(
                    true,
                    "Registration successful",
                    savedUser.getUserId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.getRole()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegistrationResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Validate input
            if (!Helper.isNotEmpty(request.getUsername()) || !Helper.isNotEmpty(request.getPassword())) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse(false, "Username and password are required"));
            }

            // Find user by username
            Optional<User> userOptional = userService.findByUsername(request.getUsername());
            
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(false, "Invalid username or password"));
            }

            User user = userOptional.get();

            // Verify password (Note: In production, use BCrypt or similar for password hashing)
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(false, "Invalid username or password"));
            }

            // Return success response with user details
            LoginResponse response = new LoginResponse(
                    true,
                    "Login successful",
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    user.getPhoneNumber()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(false, "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User saved = userService.save(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> findByRole(@PathVariable String role) {
        List<User> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
