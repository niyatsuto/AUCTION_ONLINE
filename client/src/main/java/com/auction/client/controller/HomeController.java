package com.auction.client.controller;

import com.auction.client.model.Session;
import com.auction.client.util.SceneManager;
import com.auction.common.dto.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/** Controller màn Home tạm: chỉ báo đã đăng nhập; màn Home thật làm ở Mốc 2. */
public class HomeController {
    @FXML private Label welcomeLabel;

    /** Được gọi tự động sau khi FXML nạp xong. */
    @FXML
    private void initialize() {
        UserDTO user = Session.getInstance().getCurrentUser();
        welcomeLabel.setText(user == null ? "Not logged in" : "Logged in: " + user);
    }

    /** Được gọi khi bấm nút Logout. */
    @FXML
    private void handleLogout() {
        Session.getInstance().logout();
        SceneManager.switchTo("login");
    }
}