package com.auction.client.model.network;

import com.auction.common.ServerConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
  private final HttpClient http = HttpClient.newHttpClient();
  private final String baseUrl = "http://" + ServerConfig.HOST + ":" + ServerConfig.REST_PORT;

  public String ping() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl + "/api/ping"))
        .GET()
        .build();
    HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}