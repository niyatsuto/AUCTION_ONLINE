package com.auction.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auction.server.dao.UserDao;
import com.auction.server.dao.UserDaoInMemory;
import com.auction.server.exception.AuthException;
import com.auction.server.model.User;
import com.auction.server.security.PasswordHasher;
import com.auction.server.service.AuthService;
import com.auction.common.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest{
    private UserDao userDao;
    private AuthService authService;
    
    @BeforeEach
    
    void setUp(){
        userDao = new UserDaoInMemory();
        authService = new AuthService(userDao);
    }
    
    @Test
    
    void register_validBidder_savesAndReturnsUser(){
        User user = authService.register("linh","matkhau123", "linh@uet.vn", Role.BIDDER);
        
        assertNotNull(user.getId());
        assertEquals("linh", user.getUsername());
        assertEquals("linh@uet.vn", user.getEmail());
        assertEquals(Role.BIDDER, user.getRole());
        assertNotEquals("matkhau123",user.getPasswordHash());
        assertTrue(PasswordHasher.matches("matkhau123", user.getPasswordHash()));
        assertSame(user, userDao.findByUsername("linh"));
        
    }
    
    @Test
    void register_usernameTaken_throwsUsernameTaken(){
        authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);
        AuthException e = assertThrows(AuthException.class, 
            () ->{ authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);});
        assertEquals("USERNAME_TAKEN", e.getCode());
        assertEquals(1, userDao.findAll().size());
    }
    
    @Test
    void register_adminRole_throwsRoleNotAllowed(){
        AuthException e = assertThrows(AuthException.class,
        () -> authService.register("hacker", "matkhau123", "h@uet.vn", Role.ADMIN));
        assertEquals("ROLE_NOT_ALLOWED",e.getCode());
        assertFalse(userDao.existsByUsername("hacker"));
    }
    
    @Test
    void register_shortPassword_throwsPasswordTooShort(){
         AuthException e = assertThrows(AuthException.class,
        () -> authService.register("linh", "12345", "linh@uet.vn", Role.BIDDER));
         assertEquals("PASSWORD_TOO_SHORT",e.getCode());
         assertFalse(userDao.existsByUsername("linh"));
    }
    
    @Test
    void login_correctPassword_returnsUser(){
        User registered = authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);
        User loggedIn = authService.login("linh", "matkhau123");
        
        assertSame(registered, loggedIn);
    }
    
    @Test
    void login_wrongPassword_throwsInvalidCredentials() {
        authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);
        AuthException e =assertThrows(AuthException.class, 
            () -> {authService.login("linh", "saimatkhau");});
            
        assertEquals("INVALID_CREDENTIALS", e.getCode());
    }
    
    @Test
    void login_unknownUsername_throwsInvalidCredentials(){
        authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);
        AuthException e =assertThrows(AuthException.class, 
            () -> {authService.login("khongcoai", "matkhau123");});
        assertEquals("INVALID_CREDENTIALS", e.getCode());
    }
    
    @Test
    void login_lockedAccount_throwsAccountLocked(){
        User user = authService.register("linh", "matkhau123", "linh@uet.vn", Role.BIDDER);
        user.setActive(false);
        userDao.save(user);
        AuthException e =assertThrows(AuthException.class, 
            () -> {authService.login("linh", "matkhau123");});
        assertEquals("ACCOUNT_LOCKED", e.getCode());
    }
}