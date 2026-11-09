package com.auction.server.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.auction.server.dto.ErrorResponseDto;
import com.auction.server.dto.RegisterRequestDto;
import com.auction.server.dto.UserDto;
import com.auction.server.model.entity.User;
import com.auction.server.model.service.AuthService;
import com.auction.server.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RegisterHandler implements HttpHandler {
    private final AuthService authService;

    public RegisterHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        if (!"POST".equals(exchange.getRequestMethod())){
            sendJson(exchange, 405, new ErrorResponseDto("METHOD_NOT_ALLOWED", "Only POST is supported"));
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        RegisterRequestDto request = JsonUtil.fromJson(body, RegisterRequestDto.class);

        User user = authService.register(request.getUsername(), request.getPassword(), request.getEmail(), request.getRole());
        sendJson(exchange, 201, toDto(user));

    }

    private UserDto toDto(User user){
        return new UserDto(user.getUsername(), user.getId(), user.getEmail(), user.getRole());
    }

    private void sendJson(HttpExchange exchange, int status, Object obj) throws IOException{
        byte[] bytes = JsonUtil.toJson(obj).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        
        try (OutputStream os = exchange.getResponseBody();){
            os.write(bytes);
        }
    }

}