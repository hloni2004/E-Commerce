package za.ac.cput.domain.dto;

public class RegistrationResponse {
    private boolean success;
    private String message;
    private String userId;
    private String username;
    private String email;
    private String role;

    public RegistrationResponse() {
    }

    public RegistrationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RegistrationResponse(boolean success, String message, String userId, String username, String email, String role) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
