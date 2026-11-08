package com.auction.server.model;

import com.auction.common.Role;

public final class UserFactory {
    
    private UserFactory() {
    }
    
    public static User create(Role role, String id, String username, String passwordHash, String email){
        if(role == Role.BIDDER){
            return new Bidder(id, username, passwordHash, email);
        }
        else if(role == Role.SELLER){
            return new Seller(id, username, passwordHash, email);
        }
        else if(role == Role.ADMIN){
            return new Admin(id, username, passwordHash, email);
        }
        else{
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + role);
        }
    }
}
