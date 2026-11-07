package com.auction.client;

import com.auction.client.util.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
//  @Override
//  public void start(Stage stage) throws Exception {
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ping.fxml"));
//    stage.setScene(new Scene(loader.load()));
//    stage.setTitle("Đấu giá - Mốc 0");
//    stage.show();
//  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Đấu giá");
    SceneManager.init(stage);
    SceneManager.switchTo("ping");   // khi Minh có login.fxml thì đổi thành "login"
  }

  public static void main(String[] args) {
    launch(args);
  }
}