package com.auction.server.model.service;

import com.auction.common.Role;
import com.auction.server.dao.UserDao;
import com.auction.server.model.PasswordHasher;
import com.auction.server.model.UserFactory;
import com.auction.server.model.entity.User;

public class AuthService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final UserDao userDao;

    public AuthService(UserDao userDao){
        this.userDao = userDao;
    }
    
    public synchronized User register(String username, String password, String email, Role role){
        // Kiểm tra dữ liệu đăng ký   
        if(username == null || username.isBlank()){
            throw new AuthException("INVALID_USERNAME", "Tên đăng nhập không được để trống");
        }
        
        if(password == null || password.length() < MIN_PASSWORD_LENGTH){
            throw new AuthException("PASSWORD_TOO_SHORT", "Mật khẩu phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
        }
        
        if(role != Role.BIDDER && role != Role.SELLER){
            throw new AuthException("ROLE_NOT_ALLOWED", "Chỉ được đăng ký làm Bidder hoặc Seller");
        }
        
        //kiểm tra trùng tên
        String name = username.trim();
        if(userDao.existsByUsername(name)){
            throw new AuthException("USERNAME_TAKEN", "Tên đăng nhập đã tồn tại");
        }
        
        //băm mật khẩu, tạo đúng loại User, lưu
        String passwordHash = PasswordHasher.hash(password);
        User user = UserFactory.create(role, userDao.nextId(), name, passwordHash, email);
        userDao.save(user);
        return user;
    }
}