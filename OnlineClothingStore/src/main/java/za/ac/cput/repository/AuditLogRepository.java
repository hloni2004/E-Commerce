package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {

    // Find all audit logs for a specific user
    List<AuditLog> findByUser(User user);

    // Find audit logs by action type
    List<AuditLog> findByAction(String action);

    // Find audit logs within a date range
    List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find audit logs by user and action
    List<AuditLog> findByUserAndAction(User user, String action);

    // Find audit logs by user within a date range
    List<AuditLog> findByUserAndTimestampBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    // Find recent audit logs ordered by timestamp descending
    List<AuditLog> findTop10ByOrderByTimestampDesc();
}