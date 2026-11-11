package com.auction.client.model;

import com.auction.common.dto.UserDTO;

public class Session {

  private static final Session INSTANCE = new Session();

  private volatile UserDTO currentUser;
  private volatile int selectedItemId = -1;   // -1 nghĩa là chưa chọn sản phẩm nào

  private Session() {
  }

  public static Session getInstance() {
    return INSTANCE;
  }

  public UserDTO getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserDTO user) {
    this.currentUser = user;
  }

  public boolean isLoggedIn() {
    return currentUser != null;
  }

  public int getSelectedItemId() {
    return selectedItemId;
  }

  public void setSelectedItemId(int itemId) {
    this.selectedItemId = itemId;
  }

  public void logout() {
    currentUser = null;
    selectedItemId = -1;
  }
}