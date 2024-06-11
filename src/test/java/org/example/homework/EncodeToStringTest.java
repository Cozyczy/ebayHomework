package org.example.homework;

import org.example.homework.entity.UserEntity;
import org.example.homework.util.Base64Util;

import java.util.Base64;

public class EncodeToStringTest {
    public static void main(String[] args) throws Exception {
        String input = "{\"userId\":123456,\"accountName\":\"user\",\"role\":\"user\"}"; // 假设这是需要传递的角色信息
        String encodedRoleInfo = encodeToString(input);
        // 将编码后的角色信息作为URL的一部分
        String encodeUrl = "http://localhost:8080/user/getUserRole?input=" + encodedRoleInfo;
        // 输出URL
        System.out.println(encodeUrl);

        String[] urlParts = encodeUrl.split("input=");
        if (urlParts.length > 1) {
            String encodedRoleInfoFromUrl = urlParts[1];
            UserEntity userEntity = Base64Util.decodeUserRole(encodedRoleInfoFromUrl);
            System.out.println(userEntity.getUserId());
        }
    }

    // 将字符串编码为Base64
    public static String encodeToString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
