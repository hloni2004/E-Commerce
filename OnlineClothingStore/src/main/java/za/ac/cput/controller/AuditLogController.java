package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.AuditLog;
import za.ac.cput.service.AuditLogService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<AuditLog> create(@RequestBody AuditLog auditLog) {
        AuditLog created = auditLogService.create(auditLog);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> read(@PathVariable String id) {
        AuditLog auditLog = auditLogService.read(id);
        if (auditLog != null) {
            return new ResponseEntity<>(auditLog, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAll() {
        List<AuditLog> auditLogs = auditLogService.getAll();
        return new ResponseEntity<>(auditLogs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        auditLogService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUserId(@PathVariable String userId) {
        // Note: Service expects User object, this endpoint may need UserService to fetch user first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
        List<AuditLog> auditLogs = auditLogService.getLogsByAction(action);
        return new ResponseEntity<>(auditLogs, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<AuditLog>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<AuditLog> auditLogs = auditLogService.getLogsByDateRange(start, end);
        return new ResponseEntity<>(auditLogs, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUserAndAction(@PathVariable String userId, @PathVariable String action) {
        // Note: Service expects User object, this endpoint may need UserService to fetch user first
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<AuditLog>> getRecentAuditLogs() {
        List<AuditLog> auditLogs = auditLogService.getRecentLogs();
        return new ResponseEntity<>(auditLogs, HttpStatus.OK);
    }
}
