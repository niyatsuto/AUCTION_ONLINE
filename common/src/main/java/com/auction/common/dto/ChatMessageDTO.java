package com.auction.common.dto;
//mot tin nhan chat
public record ChatMessageDTO(int itemId, String sender, String content, long time) {}