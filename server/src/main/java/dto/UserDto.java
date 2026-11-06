package dto;

public class UserDto {
    private String username;
    private String id;
    private String email;
    private String role;

    public UserDto() {}

    public UserDto(String username, String id, String email, String role) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}