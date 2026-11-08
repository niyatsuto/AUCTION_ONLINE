package com.auction.server.dao;

import com.auction.server.model.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserDaoInMemory implements UserDao{
    private final Map<String, User> userByName = new LinkedHashMap<>();
    private long lastId =0; // Id lớn nhất đã cấp;
    
    @Override 
    public synchronized String nextId(){
        lastId = lastId +1;
        return "U"+lastId;
    }
    
    @Override 
    public synchronized void save(User user){
        userByName.put(user.getUsername().toLowerCase(), user);
    }
    
    @Override 
    public synchronized User findById(String id){
        for (User user: userByName.values()){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    
    @Override
    public synchronized User findByUsername(String username){
        if(userByName.containsKey(username.toLowerCase())){
            return userByName.get(username.toLowerCase());
        }
        return null;
    }
    
    @Override 
    public synchronized boolean existsByUsername(String username){
        return userByName.containsKey(username.toLowerCase());
    }
    @Override 
    public synchronized List<User> findAll(){
        return new ArrayList<>(userByName.values());
    }
}
