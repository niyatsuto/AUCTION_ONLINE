package com.auction.server.model;

import com.auction.common.Role;
import com.auction.server.model.entity.Admin;
import com.auction.server.model.entity.Bidder;
import com.auction.server.model.entity.Seller;
import com.auction.server.model.entity.User;

public final class UserFactory {
    
    private UserFactory() {
    }
    
    public static User create(Role role, String id, String username, String passwordHash, String email){
        switch (role){
            case BIDDER:
                return new Bidder(id, username, passwordHash, email);
            case SELLER:
                return new Seller(id, username, passwordHash, email);
            case ADMIN:
                return new Admin(id, username, passwordHash, email);
            default:
                throw new IllegalArgumentException("Invalid role " + role);
        }
    }
}
