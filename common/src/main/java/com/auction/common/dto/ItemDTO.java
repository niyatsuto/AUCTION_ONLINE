package com.auction.common.dto;
// san pham
public record ItemDTO(int id, String name, String description, double currentPrice,
                      String sellerName, String leaderName, long endTime, String status) {}