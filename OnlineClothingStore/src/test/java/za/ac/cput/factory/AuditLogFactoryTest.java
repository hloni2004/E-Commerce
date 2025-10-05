package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuditLogFactoryTest {

    private User testUser;
    private LocalDateTime testTimestamp;

    @BeforeEach
    void setUp() {
        testUser = new User.Builder()
                .setUserId("U001")
                .setUsername("adminuser")
                .setEmail("admin@example.com")
                .setPassword("Admin@1234")
                .setRole("ADMIN")
                .setCreatedAt(LocalDateTime.now())
                .build();

        testTimestamp = LocalDateTime.now();
    }

    @Test
    void testBuildAuditLog_Success() {
        AuditLog auditLog = AuditLogFactory.buildAuditLog(
                "L001",
                testUser,
                "USER_LOGIN",
                testTimestamp,
                "User logged in successfully"
        );

        assertNotNull(auditLog);
        assertEquals("L001", auditLog.getLogId());
        assertEquals(testUser, auditLog.getUser());
        assertEquals("USER_LOGIN", auditLog.getAction());
        assertEquals(testTimestamp, auditLog.getTimestamp());
        assertEquals("User logged in successfully", auditLog.getDetails());
    }

    @Test
    void testBuildAuditLog_NullDetails_Success() {
        AuditLog auditLog = AuditLogFactory.buildAuditLog(
                "L002",
                testUser,
                "USER_LOGOUT",
                testTimestamp,
                null
        );

        assertNotNull(auditLog);
        assertNull(auditLog.getDetails());
    }

    @Test
    void testBuildAuditLog_EmptyDetails_Success() {
        AuditLog auditLog = AuditLogFactory.buildAuditLog(
                "L003",
                testUser,
                "PASSWORD_CHANGE",
                testTimestamp,
                ""
        );

        assertNotNull(auditLog);
        assertEquals("", auditLog.getDetails());
    }

    @Test
    void testBuildAuditLog_NullLogId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    null,
                    testUser,
                    "USER_LOGIN",
                    testTimestamp,
                    "Details"
            );
        });
        assertEquals("Log ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_EmptyLogId_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "   ",
                    testUser,
                    "USER_LOGIN",
                    testTimestamp,
                    "Details"
            );
        });
        assertEquals("Log ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_NullUser_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "L001",
                    null,
                    "USER_LOGIN",
                    testTimestamp,
                    "Details"
            );
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_NullAction_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "L001",
                    testUser,
                    null,
                    testTimestamp,
                    "Details"
            );
        });
        assertEquals("Action cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_EmptyAction_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "L001",
                    testUser,
                    "",
                    testTimestamp,
                    "Details"
            );
        });
        assertEquals("Action cannot be null or empty", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_NullTimestamp_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "L001",
                    testUser,
                    "USER_LOGIN",
                    null,
                    "Details"
            );
        });
        assertEquals("Timestamp cannot be null", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_DetailsExceed1000Characters_ThrowsException() {
        String longDetails = "A".repeat(1001);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AuditLogFactory.buildAuditLog(
                    "L001",
                    testUser,
                    "USER_LOGIN",
                    testTimestamp,
                    longDetails
            );
        });
        assertEquals("Details cannot exceed 1000 characters", exception.getMessage());
    }

    @Test
    void testBuildAuditLog_DetailsExactly1000Characters_Success() {
        String exactDetails = "B".repeat(1000);

        AuditLog auditLog = AuditLogFactory.buildAuditLog(
                "L004",
                testUser,
                "DATA_EXPORT",
                testTimestamp,
                exactDetails
        );

        assertNotNull(auditLog);
        assertEquals(1000, auditLog.getDetails().length());
    }
}