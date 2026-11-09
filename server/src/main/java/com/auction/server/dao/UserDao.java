package com.auction.server.dao;

import java.util.List;

import com.auction.server.model.entity.User;

public interface UserDao{
    
    // Cấp một id mới, chưa ai dùng
    String nextId();
    
    // Lưu người dùng; nếu tên đã có thì ghi đè 
    void save(User user);
    
    // Tìm theo id;
    User findById(String id);
    
    // Tìm theo tên, không phân biệt hoa thường;
    User findByUsername(String username);
    
    // Tên này đã có người dùng chưa? (không phân biệt hoa thường)
    boolean existsByUsername(String username);
    
    // Toàn bộ người dùng, theo thứ tự đăng ký
    List<User> findAll();
}
