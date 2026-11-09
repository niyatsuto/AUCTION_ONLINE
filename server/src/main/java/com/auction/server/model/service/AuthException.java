package com.auction.server.model.service;

public class AuthException extends RuntimeException{
    private final String code;  // mã lỗi cho chương trình đọc, ví dụ "USERNAME_TAKEN"
    
    public AuthException(String code, String message){
        super(message);
        this.code = code;
    }
    
    public String getCode(){
        return this.code;
    }
    
}