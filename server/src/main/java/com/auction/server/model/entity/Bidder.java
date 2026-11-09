package com.auction.server.model.entity;

import com.auction.common.Role;

public class Bidder extends User{
    public Bidder(String id, String username, String passwordHash, String email){
        super(id, username, passwordHash, email);
    }
    
    @Override 
    public Role getRole(){
        return Role.BIDDER;
    }
    
    @Override 
    public boolean canBid(){
        return true;
    }
}
