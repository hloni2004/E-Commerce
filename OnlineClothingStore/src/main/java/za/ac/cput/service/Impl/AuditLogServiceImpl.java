package za.ac.cput.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.AuditLog;
import za.ac.cput.domain.User;
import za.ac.cput.repository.AuditLogRepository;
import za.ac.cput.service.AuditLogService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;

    @Autowired
    public AuditLogServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuditLog create(AuditLog auditLog) {
        return repository.save(auditLog);
    }

    @Override
    public AuditLog read(String logId) {
        return repository.findById(logId).orElse(null);
    }

    @Override
    public AuditLog update(AuditLog auditLog) {
        if (repository.existsById(auditLog.getLogId())) {
            return repository.save(auditLog);
        }
        return null;
    }

    @Override
    public void delete(String logId) {
        repository.deleteById(logId);
    }

    @Override
    public List<AuditLog> getAll() {
        return repository.findAll();
    }

    @Override
    public List<AuditLog> getLogsByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<AuditLog> getLogsByAction(String action) {
        return repository.findByAction(action);
    }

    @Override
    public List<AuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTimestampBetween(startDate, endDate);
    }

    @Override
    public List<AuditLog> getLogsByUserAndAction(User user, String action) {
        return repository.findByUserAndAction(user, action);
    }

    @Override
    public List<AuditLog> getLogsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByUserAndTimestampBetween(user, startDate, endDate);
    }

    @Override
    public List<AuditLog> getRecentLogs() {
        return repository.findTop10ByOrderByTimestampDesc();
    }
}