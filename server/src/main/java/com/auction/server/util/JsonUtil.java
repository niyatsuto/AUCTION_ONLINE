package com.auction.server.util;

import com.google.gson.Gson;

public final class JsonUtil {
    private static final Gson GSON = new Gson();
    
    private JsonUtil() {}

    public static <T> T fromJson(String jsonString, Class<T> objectType){
        return GSON.fromJson(jsonString, objectType);
    }

    public static String toJson(Object object){
        return GSON.toJson(object);
    }
}