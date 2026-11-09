package com.auction.server.dto;

import com.auction.common.Role;

public class UserDto {
    private String username;
    private String id;
    private String email;
    private Role role;

    public UserDto() {}

    public UserDto(String username, String id, String email, Role role) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}