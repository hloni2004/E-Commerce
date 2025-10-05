package za.ac.cput.factory;

import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDateTime;

public class AuditLogFactory {

    public static AuditLog buildAuditLog(String logId, User user, String action,
                                         LocalDateTime timestamp, String details) {

        // Validate logId
        if (!Helper.isNotEmpty(logId)) {
            throw new IllegalArgumentException("Log ID cannot be null or empty");
        }

        // Validate user
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Validate action
        if (!Helper.isNotEmpty(action)) {
            throw new IllegalArgumentException("Action cannot be null or empty");
        }

        // Validate timestamp
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        // Validate details (can be null/empty, but check length if provided)
        if (details != null && details.length() > 1000) {
            throw new IllegalArgumentException("Details cannot exceed 1000 characters");
        }

        return new AuditLog.Builder()
                .setLogId(logId)
                .setUser(user)
                .setAction(action)
                .setTimestamp(timestamp)
                .setDetails(details)
                .build();
    }
}