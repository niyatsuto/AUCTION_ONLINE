package com.auction.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

  private static Stage primaryStage;

  private SceneManager() {
  }

  public static void init(Stage stage) {
    primaryStage = stage;
  }

  public static void switchTo(String fxmlName) {
    String path = "/com/auction/client/view/" + fxmlName + ".fxml";
    URL url = SceneManager.class.getResource(path);
    if (url == null) {
      throw new IllegalArgumentException("Không tìm thấy file FXML: " + path);
    }
    try {
      Parent root = new FXMLLoader(url).load();
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (IOException e) {
      throw new RuntimeException("Không mở được màn hình: " + fxmlName, e);
    }
  }
}