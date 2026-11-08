package com.auction.server.model;

import com.auction.common.Role;

public abstract class User extends Entity{
    private final String username;
    private final String passwordHash;
    private final String email;
    private boolean active;  // false = bị admin khoá tài khoản, true= tài khoản vẫn hoạt động
    
    protected User(String id, String username, String passwordHash, String email){
        super(id);
        this.username= username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.active = true;  // Tài khoản mới luôn hoạt động
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public abstract Role getRole();
    
    public boolean canBid(){
        return false;
    }
    
    public boolean canSell(){
        return false;
    }
    
    public boolean canManageUsers(){
        return false;
    }
    
    public void printInfo(){
        System.out.println("[" + this.getRole() + "] #" +this.getId() + " " + this.username + " <" + email + ">");
    }
}
