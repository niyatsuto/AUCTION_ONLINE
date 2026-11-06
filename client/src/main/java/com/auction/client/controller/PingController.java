package com.auction.client.controller;

import com.auction.client.model.network.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PingController {

  @FXML private Label resultLabel;

  private final ApiClient api = new ApiClient();

  @FXML
  private void handlePing() {
    resultLabel.setText("Đang gọi...");
    new Thread(() -> {
      String text;
      try {
        text = api.ping();
      } catch (Exception e) {
        text = "Lỗi: " + e.getMessage();
      }
      String result = text;
      Platform.runLater(() -> resultLabel.setText(result));
    }).start();
  }
}