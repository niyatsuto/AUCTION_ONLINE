package com.auction.client.model.network;

import com.auction.common.ServerConfig;
import com.auction.common.dto.CreateItemRequest;
import com.auction.common.dto.ItemDTO;
import com.auction.common.dto.LoginRequest;
import com.auction.common.dto.RegisterRequest;
import com.auction.common.dto.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ApiClient {
  private final HttpClient http = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(5))
      .build();
  private final String baseUrl = "http://" + ServerConfig.HOST + ":" + ServerConfig.REST_PORT;
  private final Gson gson = new Gson();

  public String ping() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl + "/api/ping"))
        .GET()
        .build();
    HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

  public UserDTO login(String username, String password) throws ApiException {
    return post("/api/login", new LoginRequest(username, password), UserDTO.class);
  }

  public UserDTO register(String username, String password, String displayName) throws ApiException {
    return post("/api/register", new RegisterRequest(username, password, displayName), UserDTO.class);
  }


  public List<ItemDTO> getItems() throws ApiException {
    Type listType =  new TypeToken<List<ItemDTO>>() {}.getType();
    return get("/api/items", listType);
  }

  public ItemDTO getItem(int id) throws ApiException {
    return get("/api/items/" + id, ItemDTO.class);
  }

  public ItemDTO createItem(CreateItemRequest request) throws ApiException {
    return post("/api/items", request, ItemDTO.class);
  }

  // ===== Hàm dùng chung =====
  private <T> T get(String path, Type responseType) throws ApiException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl + path))
        .timeout(Duration.ofSeconds(10))
        .GET()
        .build();
    return send(request, responseType);
  }

  private <T> T post(String path, Object body, Type responseType) throws ApiException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl + path))
        .timeout(Duration.ofSeconds(10))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
        .build();
    return send(request, responseType);
  }

  // Gửi yêu cầu, kiểm tra mã trạng thái, đổi JSON thành đối tượng kiểu responseType
  private <T> T send(HttpRequest request, Type responseType) throws ApiException {
    HttpResponse<String> response;
    try {
      response = http.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      throw new ApiException("Không kết nối được tới server", 0);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ApiException("Yêu cầu bị gián đoạn", 0);
    }

    int status = response.statusCode();
    if (status >= 200 && status < 300) {
      try {
        T result = gson.fromJson(response.body(), responseType);
        if (result == null) {
          throw new ApiException("Server trả về dữ liệu rỗng", status);
        }
        return result;
      } catch (JsonSyntaxException e) {
        throw new ApiException("Dữ liệu server trả về không hợp lệ", status);
      }
    }
    throw new ApiException(readError(response.body(), status), status);
  }

  // Đọc câu lỗi từ {"error":"..."}
  private String readError(String body, int status) {
    try {
      JsonObject obj = JsonParser.parseString(body).getAsJsonObject();
      if (obj.has("error")) {
        return obj.get("error").getAsString();
      }
    } catch (Exception ignored) {
      // body không phải JSON, dùng câu mặc định bên dưới
    }
    return "Lỗi từ server (mã " + status + ")";
  }
}