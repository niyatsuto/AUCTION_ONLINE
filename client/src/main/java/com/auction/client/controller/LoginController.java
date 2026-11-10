package com.auction.client.controller;

import com.auction.client.model.Session;
import com.auction.client.model.network.ApiClient;
import com.auction.client.model.network.ApiException;
import com.auction.client.util.SceneManager;
import com.auction.common.dto.UserDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/** Controller màn đăng nhập: kiểm tra ô trống, gọi server ở luồng riêng, hiện lỗi nếu sai. */
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    private final ApiClient api = new ApiClient();

    /** Được gọi khi bấm nút Login hoặc nhấn Enter (defaultButton). */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password");
            return;
        }

        errorLabel.setText("");
        loginButton.setDisable(true);

        Thread thread = new Thread(() -> {
            try {
                UserDTO user = api.login(username, password);
                Platform.runLater(() -> {
                    Session.getInstance().setCurrentUser(user);
                    SceneManager.switchTo("home");
                });
            } catch (ApiException e) {
                showError(e.getMessage());
            } catch (RuntimeException e) {
                showError("Unexpected error: " + e.getMessage());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /** Được gọi khi bấm liên kết sang màn đăng ký. */
    @FXML
    private void goToRegister() {
        SceneManager.switchTo("register");
    }

    /** Hiện lỗi lên nhãn (phải qua Platform.runLater vì đang ở luồng nền) và mở lại nút. */
    private void showError(String message) {
        Platform.runLater(() -> {
            loginButton.setDisable(false);
            errorLabel.setText(message);
        });
    }
}
