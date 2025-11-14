package za.ac.cput.service.Impl;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;
import za.ac.cput.factory.AuditLogFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.AuditLogService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuditLogServiceImplTest {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private UserServiceImpl userService;

    private static User testUser;
    private static AuditLog testAuditLog;
    private static LocalDateTime testTimestamp;

    @BeforeAll
    static void setUp() {
        testUser = UserFactory.createUser(
                "USR003",
                "audittest",
                "audit@example.com",
                "Password123!",
                "+27821234569",
                "ADMIN",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null,
                null
        );

        testTimestamp = LocalDateTime.now();

        testAuditLog = AuditLogFactory.buildAuditLog(
                "LOG001",
                testUser,
                "USER_LOGIN",
                testTimestamp,
                "User logged in successfully"
        );
    }

    @Test
    @Order(1)
    void testCreate() {
        // First save the user to the database
        User savedUser = userService.save(testUser);
        assertNotNull(savedUser);
        
        // Update testAuditLog with the saved user
        testAuditLog = AuditLogFactory.buildAuditLog(
                testAuditLog.getLogId(),
                savedUser,
                testAuditLog.getAction(),
                testAuditLog.getTimestamp(),
                testAuditLog.getDetails()
        );
        
        AuditLog created = auditLogService.create(testAuditLog);
        assertNotNull(created);
        assertEquals(testAuditLog.getLogId(), created.getLogId());
        assertEquals("USER_LOGIN", created.getAction());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void testRead() {
        AuditLog read = auditLogService.read(testAuditLog.getLogId());
        assertNotNull(read);
        assertEquals(testAuditLog.getLogId(), read.getLogId());
        assertEquals("USER_LOGIN", read.getAction());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void testUpdate() {
        AuditLog existing = auditLogService.read(testAuditLog.getLogId());
        AuditLog updated = new AuditLog.Builder()
                .setLogId(existing.getLogId())
                .setUser(existing.getUser())
                .setAction("USER_LOGOUT")
                .setTimestamp(existing.getTimestamp())
                .setDetails("User logged out successfully")
                .build();

        AuditLog result = auditLogService.update(updated);
        assertNotNull(result);
        assertEquals("USER_LOGOUT", result.getAction());
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(4)
    void testGetLogsByAction() {
        List<AuditLog> logs = auditLogService.getLogsByAction("USER_LOGOUT");
        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        System.out.println("Logs for action USER_LOGOUT: " + logs.size());
    }

    @Test
    @Order(5)
    void testGetLogsByDateRange() {
        LocalDateTime startDate = testTimestamp.minusHours(1);
        LocalDateTime endDate = testTimestamp.plusHours(1);

        List<AuditLog> logs = auditLogService.getLogsByDateRange(startDate, endDate);
        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        System.out.println("Logs in date range: " + logs.size());
    }

    @Test
    @Order(6)
    void testGetRecentLogs() {
        List<AuditLog> logs = auditLogService.getRecentLogs();
        assertNotNull(logs);
        System.out.println("Recent logs: " + logs.size());
    }

    @Test
    @Order(7)
    void testGetAll() {
        List<AuditLog> logs = auditLogService.getAll();
        assertNotNull(logs);
        assertFalse(logs.isEmpty());
        System.out.println("Total logs: " + logs.size());
    }

    @Test
    @Order(8)
    void testDelete() {
        auditLogService.delete(testAuditLog.getLogId());
        AuditLog deleted = auditLogService.read(testAuditLog.getLogId());
        assertNull(deleted);
        System.out.println("Audit log deleted successfully");
    }
}