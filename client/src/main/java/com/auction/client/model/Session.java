package com.auction.client.model;

import com.auction.common.dto.UserDTO;

public class Session {

  private static final Session INSTANCE = new Session();

  private volatile UserDTO currentUser;

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

  public void logout() {
    currentUser = null;
  }
}