package com.auction.server.model.entity;

public abstract class Entity{
    private final String id;
    
    protected Entity(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
}
