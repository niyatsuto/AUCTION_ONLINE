package com.auction.common.dto;
import com.google.gson.JsonElement;
public record SocketMessage(String type, JsonElement payload) {}