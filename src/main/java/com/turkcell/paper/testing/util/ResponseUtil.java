package com.turkcell.paper.testing.util;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {

    }

    public static Map<String, Object> getObjectMap(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("time", LocalDateTime.now());
        return body;
    }
}
