package com.auction.server.model.entity;

import com.auction.common.Role;

public class Seller extends User{
    public Seller(String id, String username, String passwordHash, String email){
        super(id, username, passwordHash, email);
    }
    
    @Override
    public Role getRole(){
        return Role.SELLER;
    }
    
    @Override 
    public boolean canSell(){
        return true;
    }
    
}
