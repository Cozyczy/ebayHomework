package org.example.homework;

import org.example.homework.controller.UserController;
import org.example.homework.entity.UserEntity;
import org.example.homework.util.Base64Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;


import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    //解析url获取用户的role
    @Test
    public void userControllerTest() throws Exception{
        //更换userId、role测试，改这里
        String input = "{\"userId\":123456,\"accountName\":\"user\",\"role\":\"user\"}"; // 假设这是需要传递的角色信息
        String encodedRoleInfo = encodeToString(input);
        // 将编码后的角色信息作为URL的一部分
        String encodeUrl = "http://localhost:8080/user/getUserRole?input=" + encodedRoleInfo;
        // 输出URL
        //System.out.println(encodeUrl);

        String[] urlParts = encodeUrl.split("input=");
        if (urlParts.length > 1) {
            String encodedRoleInfoFromUrl = urlParts[1];
            ResponseEntity<String> userRole = userController.getUserRole(encodedRoleInfoFromUrl);
            System.out.println(userRole.getBody());
        }

    }

    //1、添加新的User，已经该user的resource
    //2、已经存在的user，添加新的resource
    @Test
    public void addUserAccess(){
        //更换userId、role测试，改这里
        String input = "{\"userId\":123459,\"accountName\":\"user\",\"role\":\"admin\"}"; // 假设这是需要传递的角色信息
        //更换resource测试，改这里
        String resource = "resource C";
        String encodedRoleInfo = encodeToString(input);
        // 将编码后的角色信息作为URL的一部分
        String encodeUrl = "http://localhost:8080/user/getUserRole?resource="+resource+"&input=" + encodedRoleInfo;
        // 输出URL
        System.out.println(encodeUrl);

        String[] urlParts = encodeUrl.split("input=");
        if (urlParts.length > 1) {
            String encodedRoleInfoFromUrl = urlParts[1];
            ResponseEntity<String> stringResponseEntity = userController.addUserAccess(encodedRoleInfoFromUrl, resource);
            System.out.println(stringResponseEntity.getStatusCode());
            // UserEntity userEntity = Base64Util.decodeUserRole(encodedRoleInfoFromUrl);
            // userController.getUserRole(encodedRoleInfoFromUrl);
        }
    }

    //查询某user是否有resource权限
    @Test
    public void getUserAccess(){
        //更换userId测试，改这里
        String input = "{\"userId\":123457,\"accountName\":\"user\",\"role\":\"admin\"}"; // 假设这是需要传递的角色信息
        //更换resource测试，改这里
        String resource = "resource C";
        String encodedRoleInfo = encodeToString(input);
        // 将编码后的角色信息作为URL的一部分
        String encodeUrl = "http://localhost:8080/user/resource?resource="+resource+"&input=" + encodedRoleInfo;
        // 输出URL
        System.out.println(encodeUrl);

        String[] urlParts = encodeUrl.split("input=");
        if (urlParts.length > 1) {
            String encodedRoleInfoFromUrl = urlParts[1];
            ResponseEntity<String> stringResponseEntity = userController.getUserAccess(encodedRoleInfoFromUrl, resource);
            System.out.println(stringResponseEntity.getStatusCode());
            // UserEntity userEntity = Base64Util.decodeUserRole(encodedRoleInfoFromUrl);
            // userController.getUserRole(encodedRoleInfoFromUrl);
        }
    }

    // 将字符串编码为Base64
    public static String encodeToString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
}
