package com.io.east.district.utils;

import com.google.gson.Gson;

import java.util.Map;

public class JsonUtils {
    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
