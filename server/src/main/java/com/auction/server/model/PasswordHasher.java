package com.auction.server.model;

import java.nio.charset.StandardCharsets;      // bảng mã UTF-8 để đổi chuỗi ra byte
import java.security.MessageDigest;            // công cụ băm SHA-256
import java.security.NoSuchAlgorithmException; // ngoại lệ checked khi máy không có SHA-256
import java.security.SecureRandom;             // bộ sinh muối ngẫu nhiên an toàn
import java.util.Base64;                       // đổi byte sang chuỗi in được

public final class PasswordHasher {
    private static final SecureRandom RANDOM = new SecureRandom();
    
    private PasswordHasher() {
    }
     // Dùng khi ĐĂNG KÝ. Trả về "muối:mãbăm" để lưu vào User.
    public static String hash(String password){
        byte[] saltBytes = new byte[16];
        RANDOM.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        
        return salt + ":" + sha256(salt + password);
    }
    // Dùng khi ĐĂNG NHẬP. Mật khẩu vừa gõ có khớp chuỗi đã lưu không?
    public static boolean matches(String password, String stored){
        String[] parts = stored.split(":");
        if(parts.length!=2){
            return false;
        }
        String salt = parts[0];
        String expected = parts[1];
        String actual = sha256(salt + password);
        return expected.equals(actual);    
    }
    
    // Băm một chuỗi bằng SHA-256
    private static String sha256(String text){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        }
        catch(NoSuchAlgorithmException e){throw new IllegalStateException("Máy không hỗ trợ SHA-256", e);}
    }
    
}
