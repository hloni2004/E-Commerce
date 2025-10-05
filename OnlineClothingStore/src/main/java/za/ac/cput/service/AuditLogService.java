package za.ac.cput.service;

import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService extends IService {

    AuditLog create(AuditLog auditLog);

    AuditLog read(String logId);

    AuditLog update(AuditLog auditLog);

    void delete(String logId);

    List<AuditLog> getAll();

    List<AuditLog> getLogsByUser(User user);

    List<AuditLog> getLogsByAction(String action);

    List<AuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<AuditLog> getLogsByUserAndAction(User user, String action);

    List<AuditLog> getLogsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate);

    List<AuditLog> getRecentLogs();
}