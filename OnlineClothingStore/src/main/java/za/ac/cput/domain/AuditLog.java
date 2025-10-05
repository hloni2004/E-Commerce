package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @Column(name = "log_id")
    private String logId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action")
    private String action;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "details", length = 1000)
    private String details;

    protected AuditLog() {}

    private AuditLog(Builder builder) {
        this.logId = builder.logId;
        this.user = builder.user;
        this.action = builder.action;
        this.timestamp = builder.timestamp;
        this.details = builder.details;
    }

    public String getLogId() { return logId; }
    public User getUser() { return user; }
    public String getAction() { return action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return "AuditLog{" +
                "logId='" + logId + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {
        private String logId;
        private User user;
        private String action;
        private LocalDateTime timestamp;
        private String details;

        public Builder setLogId(String logId) { this.logId = logId; return this; }
        public Builder setUser(User user) { this.user = user; return this; }
        public Builder setAction(String action) { this.action = action; return this; }
        public Builder setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public Builder setDetails(String details) { this.details = details; return this; }

        public AuditLog build() { return new AuditLog(this); }
    }
}
