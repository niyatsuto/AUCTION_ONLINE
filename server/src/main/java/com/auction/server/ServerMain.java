package com.auction.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.auction.server.controller.RegisterHandler;
import com.auction.server.dao.UserDao;
import com.auction.server.dao.UserDaoInMemory;
import com.auction.server.model.service.AuthService;
import com.sun.net.httpserver.HttpServer;

public class ServerMain {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDaoInMemory();
            AuthService authService = new AuthService(userDao);

            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/api/register", new RegisterHandler(authService));

            server.start();
            
        } catch (IOException e){
            System.out.println("Error while starting the server");
        }

    }
}