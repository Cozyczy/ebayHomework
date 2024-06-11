package org.example.homework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.homework.entity.UserEntity;

import java.util.Base64;

public class Base64Util {
    // 将字符串编码为Base64
    public static String encodeToString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    // 将Base64编码的字符串解码回UserEntity对象
    public static UserEntity decodeUserRole(String input) throws Exception {
        String decodedString = new String(Base64.getDecoder().decode(input));
        ObjectMapper mapper = new ObjectMapper();
        UserEntity userEntity = mapper.readValue(decodedString, UserEntity.class);
        return userEntity;
    }
}
