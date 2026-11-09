package com.auction.server.model.entity;

import com.auction.common.Role;

public class Admin extends User {
    public Admin(String id, String username, String passwordHash, String email) {
        super(id, username, passwordHash, email);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public boolean canManageUsers() {
        return true;
    }
}
