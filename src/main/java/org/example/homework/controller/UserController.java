package org.example.homework.controller;

import org.example.homework.entity.UserEndpoint;
import org.example.homework.entity.UserEntity;
import org.example.homework.service.UserService;
import org.example.homework.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/getUserRole")
    public ResponseEntity<String> getUserRole(@RequestParam String input) {
        try {
            UserEntity userEntity = Base64Util.decodeUserRole(input);
            return ResponseEntity.ok(userEntity.getRole());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("get User Role error: " + e.getMessage());
        }
    }

    @PostMapping("/admin/addUser")
    public ResponseEntity<String> addUserAccess(@RequestParam String input,@RequestParam String resource) {
        try {
            UserEntity userEntity = Base64Util.decodeUserRole(input);
            if (!"admin".equals(userEntity.getRole())) {
                return ResponseEntity.status(403).body("Only admins can add users.");
            }
            userService.saveAccess(userEntity,resource);
            return ResponseEntity.ok("User added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("add User error: " + e.getMessage());
        }
    }


    @GetMapping("/user/{resource}")
    public ResponseEntity<String> getUserAccess(@RequestParam String input,@PathVariable String resource) {
        try {
            UserEntity userEntity = Base64Util.decodeUserRole(input);
            List<UserEndpoint> userEndpointList = userService.getAllAccess();
            for (UserEndpoint userEndpoint : userEndpointList) {
                if (userEndpoint.getUserId().equals(userEntity.getUserId()) && userEndpoint.getEndpoints().contains(resource)) {
                    return ResponseEntity.ok("Use have permission, return resource successful: " + resource);
                }
            }
            return ResponseEntity.status(403).body("Use does not have permission, return resource failed: " + resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Add resource had an error: " + e.getMessage());
        }
    }

    @GetMapping("/user/getUser")
    public ResponseEntity<String> getUser(@RequestParam String input) {
        try {
            UserEntity userEntity = Base64Util.decodeUserRole(input);
            return ResponseEntity.ok(userEntity.getRole());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("get User Role error: " + e.getMessage());
        }
    }
}
