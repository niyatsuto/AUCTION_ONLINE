package com.auction.client.controller;

import com.auction.client.model.network.ApiClient;
import com.auction.client.model.network.ApiException;
import com.auction.client.util.SceneManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/** Controller màn đăng ký: kiểm tra dữ liệu nhập, gọi server ở luồng riêng, báo kết quả. */
public class RegisterController {
    private static final String ERROR_STYLE = "-fx-text-fill: #c62828;";
    private static final String SUCCESS_STYLE = "-fx-text-fill: #2e7d32;";

    @FXML private TextField usernameField;
    @FXML private TextField displayNameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private Button registerButton;
    @FXML private Label messageLabel;

    private final ApiClient api = new ApiClient();

    /** Được gọi khi bấm nút Register. */
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String displayName = displayNameField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmField.getText();

        if (username.isEmpty() || displayName.isEmpty() || password.isEmpty()
                || confirm.isEmpty()) {
            showMessage("Please fill in all fields", ERROR_STYLE, false);
            return;
        }
        if (!password.equals(confirm)) {
            showMessage("Passwords do not match", ERROR_STYLE, false);
            return;
        }

        messageLabel.setText("");
        registerButton.setDisable(true);

        Thread thread = new Thread(() -> {
            try {
                api.register(username, password, displayName);
                Platform.runLater(() -> {
                    showMessage("Registered successfully. Going to login...",
                            SUCCESS_STYLE, true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(done -> SceneManager.switchTo("login"));
                    pause.play();
                });
            } catch (ApiException e) {
                Platform.runLater(() -> showMessage(e.getMessage(), ERROR_STYLE, false));
            } catch (RuntimeException e) {
                String message = "Unexpected error: " + e.getMessage();
                Platform.runLater(() -> showMessage(message, ERROR_STYLE, false));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /** Được gọi khi bấm liên kết quay lại màn đăng nhập. */
    @FXML
    private void goToLogin() {
        SceneManager.switchTo("login");
    }

    /** Hiện một dòng thông báo; nếu chưa thành công thì mở lại nút Register. */
    private void showMessage(String message, String style, boolean success) {
        messageLabel.setStyle(style);
        messageLabel.setText(message);
        registerButton.setDisable(success);
    }
}
