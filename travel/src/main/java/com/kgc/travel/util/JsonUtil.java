package com.kgc.travel.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper(){
        return mapper;
    }
}
