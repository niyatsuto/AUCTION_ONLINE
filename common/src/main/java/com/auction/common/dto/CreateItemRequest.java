package com.auction.common.dto;
// tao phieu ban san pham
public record CreateItemRequest(String name, String description, double startPrice, long endTime, int sellerId) {}