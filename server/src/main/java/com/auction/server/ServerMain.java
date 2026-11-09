package com.auction.server;

import com.auction.common.ServerConfig;
import io.javalin.Javalin;

public class ServerMain {
  public static void main(String[] args) {
    Javalin app = Javalin.create();

    app.get("/api/ping", ctx -> ctx.result("ok"));

    app.start(ServerConfig.REST_PORT);
  }
}